package com.p1nero.dote.archive;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.LevelableEntity;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.packet.SyncSaveUtilPacket;
import com.p1nero.dote.network.packet.clientbound.BroadcastMessagePacket;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.io.*;
import java.util.*;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class DOTEArchiveManager {

    private static boolean alreadyInit = false;

    /**
     * 以服务端数据为准，如果已经被服务端同步过了，则不能读取客户端的数据，用于{@link DOTEArchiveManager#read(String)}
     */
    public static void setAlreadyInit() {
        DOTEArchiveManager.alreadyInit = true;
    }

    public static boolean isAlreadyInit() {
        return alreadyInit;
    }

    private static int worldLevel = 0;
    private static boolean noPlotMode = false;

    public static void setNoPlotMode() {
        noPlotMode = true;
    }

    public static boolean isNoPlotMode() {
        return noPlotMode;
    }

    public static int getWorldLevel(){
        return worldLevel;
    }
    public static String getWorldLevelName(){
        return switch (worldLevel){
            case 0 -> "N";
            case 1 -> "Ⅰ";
            case 2 -> "Ⅱ";
            case 3 -> "Ⅲ";
            case 4 -> "Ⅳ";
            default -> throw new IllegalStateException("Unexpected worldLevel value: " + worldLevel);
        };
    }

    public static final List<Task> DIALOG_LIST = new ArrayList<>();
    public static final HashSet<Task> DIALOG_SET = new HashSet<>();//优化用的，但是不知道能优化多少（
    public static final TaskSet TASK_SET = new TaskSet();
    public static void addTask(Task task){
        TASK_SET.add(task);
    }
    public static void finishTask(Task task){
        TASK_SET.remove(task);
    }

    public static int firstChoiceBiome = 0;//0 means null

    /**
     * 把对话添加到列表里
     */
    public static void addDialog(Component name, Component content){
        if(name == null || content == null){
            return;
        }
        Task task = new Task(name, content);
        if(!DIALOG_SET.contains(task)){
            DIALOG_LIST.add(task);
            DIALOG_SET.add(task);
        }
    }

    public static List<Task> getDialogList() {
        return DIALOG_LIST;
    }

    public static CompoundTag getDialogListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        for(int i = 0; i < DIALOG_LIST.size(); i++){
            dialogListNbt.put("dialog"+i, DIALOG_LIST.get(i).toNbt());
        }
        return dialogListNbt;
    }

    public static void setDialogListFromNbt(CompoundTag DialogListTag, int size){
        DIALOG_LIST.clear();
        for(int i = 0; i < size; i++){
            Task task = Task.fromNbt(DialogListTag.getCompound("dialog"+i));
            DIALOG_LIST.add(task);
            DIALOG_SET.add(task);
        }
    }

    public static CompoundTag getTaskListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        List<Task> tasks = TASK_SET.stream().toList();
        for(int i = 0; i < tasks.size(); i++){
            dialogListNbt.put("task" + i, tasks.get(i).toNbt());
        }
        return dialogListNbt;
    }

    public static void setTaskListFromNbt(CompoundTag taskListTag, int size){
        TASK_SET.clear();
        for(int i = 0; i < size; i++){
            TASK_SET.add(Task.fromNbt(taskListTag.getCompound("task"+i)), false);
        }
    }

    public static class BiomeProgressData {

        public BiomeProgressData(){

        }

        public BiomeProgressData(int choice, boolean isBossDie, boolean isBossTalked, boolean isBossFought, boolean isElderDie, boolean isElderTalked) {
            this.choice = choice;
            this.isBossDie = isBossDie;
            this.isBossTalked = isBossTalked;
            this.isBossFought = isBossFought;
            this.isElderDie = isElderDie;
            this.isElderTalked = isElderTalked;
        }

        public static int BOSS = 1;
        public static int VILLAGER = 2;

        public int choice = 0;//0:no sure 1:boss 2:villager
        protected int biome = 0;
        public boolean isBossDie = false;//boss是否死亡
        public boolean isBossTalked = false;//好像没用
        public boolean isBossFought = false;//是否和boss战斗过
        public boolean isElderDie = false;
        public boolean isElderTalked = false;

        public CompoundTag toNbt() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("choice", choice);
            tag.putBoolean("isBossDie", isBossDie);
            tag.putBoolean("isBossTalked", isBossTalked);
            tag.putBoolean("isBossFought", isBossFought);
            tag.putBoolean("isElderDie", isElderDie);
            tag.putBoolean("isElderTalked", isElderTalked);
            return tag;
        }

        public void fromNbt(CompoundTag serverData){
            choice = serverData.getInt("choice");
            isBossDie = serverData.getBoolean("isBossDie");
            isBossTalked = serverData.getBoolean("isBossTalked");
            isBossFought = serverData.getBoolean("isBossFought");
            isElderDie = serverData.getBoolean("isElderDie");
            isElderTalked = serverData.getBoolean("isElderTalked");

        }

        /**
         * 群系事件完成，升级世界等级，而且只能进行一次
         */
        public void finish(int choice, ServerLevel level){
            if(level.dimension() != DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
                return;
            }
            if(this.choice == 0){
                this.choice = choice;
            } else {
                return;//防止重复提升
            }
            if(firstChoiceBiome == 0){
                firstChoiceBiome = biome;
            }
            worldLevel++;
            for(Entity entity : level.getAllEntities()){
                if(entity instanceof LevelableEntity levelableEntity){
                    levelableEntity.levelUp(worldLevel);
                }
            }

            for(ServerPlayer player : level.getPlayers((serverPlayer -> true))){

            }
            //全局广播
            Component message = DuelOfTheEndMod.getInfo("level_up", getWorldLevelName());
            PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new BroadcastMessagePacket(message, false));
            PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncSaveUtilPacket(DOTEArchiveManager.toNbt()));

        }

        public boolean isFinished(){
            return choice != 0;
        }

    }

    public static Task buildTask(String task){
        return new Task(Component.translatable("task." + DuelOfTheEndMod.MOD_ID + "." + task), Component.translatable("task_content." + DuelOfTheEndMod.MOD_ID + "." + task));
    }

    public static Task buildTask(String task, Object... objects){
        return new Task(Component.translatable("task." + DuelOfTheEndMod.MOD_ID + "." + task), Component.translatable("task_content." + DuelOfTheEndMod.MOD_ID + "." + task, objects));
    }


    public static File getFile(String worldName){
        return new File("config/"+ DuelOfTheEndMod.MOD_ID+"/"+ worldName +".nbt");
    }

    public static void save(String worldName){

        try {
            CompoundTag saveData = toNbt();
            File file = getFile(worldName);
            DuelOfTheEndMod.LOGGER.info("saving data to {} :\n"+saveData, file.getAbsolutePath());
            if(!file.exists()){
                file.createNewFile();
            }
            NbtIo.write(saveData, file);
            DuelOfTheEndMod.LOGGER.info("over.");
        } catch (Exception e) {
            DuelOfTheEndMod.LOGGER.error("Can't save serverData", e);
        }

    }

    public static void read(String worldName){
        if(alreadyInit){
            return;
        }
        try {
            File save = getFile(worldName);
            CompoundTag saveData = NbtIo.read(save);
            if(saveData == null){
                DuelOfTheEndMod.LOGGER.info("save data not found. created new save data: {}" + save.createNewFile(), save.getAbsolutePath());
            } else {
                DuelOfTheEndMod.LOGGER.info("reading data {} :\n"+saveData, save.getAbsolutePath());
                fromNbt(saveData);
                DuelOfTheEndMod.LOGGER.info("over.");
            }
        } catch (Exception e) {
            DuelOfTheEndMod.LOGGER.error("Can't read save serverData", e);
        }
    }

    public static boolean deleteCache(String fileName){
        fromNbt(new CompoundTag());
        return getFile(fileName).delete();
    }

    /**
     * 清空数据
     */
    public static void clear(){
        fromNbt(new CompoundTag());
        alreadyInit = false;
    }

    /**
     * 把服务端的所有数据转成NBT方便发给客户端
     * @return 所有数据狠狠塞进NBT里
     */
    public static CompoundTag toNbt(){
        CompoundTag serverData = new CompoundTag();
        serverData.putInt("worldLevel", worldLevel);
        serverData.putBoolean("noPlotMode", noPlotMode);
        serverData.putInt("dialogLength", DIALOG_LIST.size());
        serverData.put("dialogList", getDialogListNbt());
        serverData.putInt("taskLength", TASK_SET.size());
        serverData.put("taskList", getTaskListNbt());
        serverData.putInt("firstChoiceBiome", firstChoiceBiome);
        return serverData;
    }

    /**
     * 把服务端发来的nbt转成客户端的SaveUtil调用
     * @param serverData 从服务端发来的nbt
     */
    public static void fromNbt(CompoundTag serverData){
        DuelOfTheEndMod.LOGGER.info("reading from: \n" + serverData);
        alreadyInit = true;
        worldLevel = serverData.getInt("worldLevel");
        noPlotMode = serverData.getBoolean("noPlotMode");
        setDialogListFromNbt(serverData.getCompound("dialogList"), serverData.getInt("dialogLength"));
        setTaskListFromNbt(serverData.getCompound("taskList"), serverData.getInt("taskLength"));
        firstChoiceBiome = serverData.getInt("firstChoiceBiome");
    }

}
