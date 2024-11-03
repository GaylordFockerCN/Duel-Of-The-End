package com.gaboj1.tcr.util;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.SyncSaveUtilPacket;
import com.gaboj1.tcr.network.packet.clientbound.BroadcastMessagePacket;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class SaveUtil {

    private static boolean alreadyInit = false;

    /**
     * 以服务端数据为准，如果已经被服务端同步过了，则不能读取客户端的数据，用于{@link SaveUtil#read(String)}
     */
    public static void setAlreadyInit() {
        SaveUtil.alreadyInit = true;
    }

    public static boolean isAlreadyInit() {
        return alreadyInit;
    }

    private static int worldLevel = 0;
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

    /**
     * 获取随世界等级提升后的怪物数值（即乘以倍率）
     */
    public static double getMobMultiplier(double mobAttr){
        return mobAttr * (SaveUtil.worldLevel == 0 ? 1 : SaveUtil.worldLevel* TCRConfig.MOB_MULTIPLIER_WHEN_WORLD_LEVEL_UP.get());
    }

    public static final List<Dialog> DIALOG_LIST = new ArrayList<>();
    public static final HashSet<Dialog> DIALOG_SET = new HashSet<>();//优化用的，但是不知道能优化多少（
    public static final HashSet<Dialog> TASK_SET = new HashSet<>(){

        @Override
        public boolean add(Dialog dialog) {
            if(super.add(dialog)){
                PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new SyncSaveUtilPacket(SaveUtil.toNbt()));
                return true;
            }
            return false;
        }

        /**
         * 同步数据，并广播任务完成
         */
        @Override
        public boolean remove(Object o) {
            if(super.remove(o)){
                Component message = TheCasketOfReveriesMod.getInfo("task_finish0").append(((Dialog)o).name.copy().withStyle(ChatFormatting.RED)).append(TheCasketOfReveriesMod.getInfo("task_finish1"));
                PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new BroadcastMessagePacket(message, false));
                PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new SyncSaveUtilPacket(SaveUtil.toNbt()));
                return true;
            }
            return false;
        }
    };
    public static int firstChoiceBiome = 0;//0 means null

    public static Biome1ProgressData biome1 = new Biome1ProgressData();
    public static Biome2ProgressData biome2 = new Biome2ProgressData();
    public static Biome3ProgressData biome3 = new Biome3ProgressData();
    public static Biome4ProgressData biome4 = new Biome4ProgressData();

    public record Dialog (Component name, Component content) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dialog dialog = (Dialog) o;
            return Objects.equals(name.getString(), dialog.name.getString()) && Objects.equals(content.getString(), dialog.content.getString());
        }

        @Override
        public int hashCode() {
            if(name == null || content == null){
                return 0;
            }
            return Objects.hash(name.getString(), content.getString());
        }

        @NotNull
        public CompoundTag toNbt(){
            CompoundTag dialog = new CompoundTag();
            dialog.putString("message", Component.Serializer.toJson(name));
            dialog.putString("content", Component.Serializer.toJson(content));
            return dialog;
        }

        public static Dialog fromNbt(CompoundTag dialog){
            System.out.println(Component.Serializer.fromJson(dialog.getString("message")) + ", " +  Component.Serializer.fromJson(dialog.getString("content")));
            return new Dialog(Component.Serializer.fromJson(dialog.getString("message")), Component.Serializer.fromJson(dialog.getString("content")));
        }

    }

    /**
     * 把对话添加到列表里
     */
    public static void addDialog(Component name, Component content){
        if(name == null || content == null){
            return;
        }
        Dialog dialog = new Dialog(name, content);
        if(!DIALOG_SET.contains(dialog)){
            DIALOG_LIST.add(dialog);
            DIALOG_SET.add(dialog);
        }
    }

    public static List<Dialog> getDialogList() {
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
            Dialog dialog = Dialog.fromNbt(DialogListTag.getCompound("dialog"+i));
            DIALOG_LIST.add(dialog);
            DIALOG_SET.add(dialog);
        }
    }

    public static CompoundTag getTaskListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        List<Dialog> tasks = TASK_SET.stream().toList();
        for(int i = 0; i < tasks.size(); i++){
            dialogListNbt.put("task" + i, tasks.get(i).toNbt());
        }
        return dialogListNbt;
    }

    public static void setTaskListFromNbt(CompoundTag taskListTag, int size){
        for(int i = 0; i < size; i++){
            TASK_SET.add(Dialog.fromNbt(taskListTag.getCompound("task"+i)));
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
            if(level.dimension() != TCRDimension.P_SKY_ISLAND_LEVEL_KEY){
                return;
            }
            if(this.choice == 0){
                this.choice = choice;
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
                if(biome1.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_1", player);
                }
                if(biome2.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_2", player);
                }
                if(biome3.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_3", player);
                }
                if(biome4.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_4", player);
                }
            }

            //全局广播
            Component message = TheCasketOfReveriesMod.getInfo("level_up", getWorldLevelName());
            PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new BroadcastMessagePacket(message, false));

        }

        public boolean isFinished(){
            return choice != 0;
        }

    }

    public static Dialog buildTask(String task){
        return new Dialog(Component.translatable( "task."+TheCasketOfReveriesMod.MOD_ID + "." + task), Component.translatable( "task_content."+TheCasketOfReveriesMod.MOD_ID + "." + task));
    }

    public static class Biome1ProgressData extends BiomeProgressData {
        public boolean smithTalked = false;//是否接取任务
        public boolean monsterSummoned = false;//是否已召唤怪
        public boolean killed = false;//是否杀死
        public boolean heal = false;//是否治愈
        public boolean isBranchFinish = false;//是否结局，和铁匠二次对话才是结局
        public static final Dialog TASK_FIND_ELDER1 = buildTask("find_elder1");
        public static final Dialog TASK_KILL_ELDER = buildTask("kill_elder1");
        public static final Dialog TASK_KILL_BOSS = buildTask("kill_boss1");
        public static final Dialog TASK_BACK_TO_BOSS = buildTask("back_boss1");//回去领赏
        public static final Dialog TASK_BACK_TO_ELDER = buildTask("back_elder1");
        public static final Dialog TASK_BLUE_MUSHROOM = buildTask("blue_mushroom");
        //支线是否结束
        public boolean isUnKnowMonsterDie(){
            return smithTalked && (killed || heal);
        }
        public Biome1ProgressData(){
            biome = 1;
        }

        /**
         * 判断boss是否能受伤，如果选择boss阵营则boss无法被打，杀死长老也视为boss阵营。
         */
        public boolean canAttackBoss(){
            return choice != 1 && !bossTaskReceived();
        }

        /**
         * 没接树魔任务不能打长老，打过boss且选择不处决则视为选择接了刺杀长老任务
         */
        public boolean canAttackElder(){
            return choice != 2 && bossTaskReceived();
        }

        public boolean bossTaskReceived(){
            return isBossFought && !isBossDie;
        }

        public boolean canGetBossReward(){
            return bossTaskReceived() && isElderDie;
        }

        /**
         * 战斗过且boss没死说明是接任务了。注意要做出选择后再标记战斗过，以免中断
         */
        public boolean killElderTaskGet(){
            return TASK_SET.contains(TASK_KILL_ELDER);
        }

        public boolean canGetElderReward(){
            return isBossDie;
        }

        @Override
        public CompoundTag toNbt() {
            CompoundTag tag = new CompoundTag();

            tag.putInt("choice", choice);
            tag.putBoolean("isBossDie", isBossDie);
            tag.putBoolean("isBossTalked", isBossTalked);
            tag.putBoolean("isBossFought", isBossFought);
            tag.putBoolean("isElderDie", isElderDie);
            tag.putBoolean("isElderTalked", isElderTalked);

            tag.putBoolean("smithTalked", smithTalked);
            tag.putBoolean("killed", killed);
            tag.putBoolean("heal", heal);
            tag.putBoolean("isBranchFinish", isBranchFinish);
            tag.putBoolean("monsterSummoned", monsterSummoned);
            return tag;
        }

        @Override
        public void fromNbt(CompoundTag serverData) {
            super.fromNbt(serverData);
            smithTalked = serverData.getBoolean("smithTalked");
            heal = serverData.getBoolean("heal");
            killed = serverData.getBoolean("killed");
            isBranchFinish= serverData.getBoolean("isBranchFinish");
            monsterSummoned = serverData.getBoolean("monsterSummoned");
        }


    }

    public static class Biome2ProgressData extends BiomeProgressData {
        public boolean miaoYinTalked1;//是否初次和乐师对话完
        public boolean shangRenTalked;//是否完成和商人的对话
        public boolean afterTrial;//是否通过了试炼
        public boolean trialTalked1;//是否初次和试炼主对话过
        public boolean talkToMaster;//是否选择告诉试炼主人盲人乐师的事
        public boolean wandererTalked;//是否完成和商人的对话
        public boolean miaoYinTalked2;//是否二次和乐师对话完、
        public boolean chooseEnd2;//是否选到了结局2
        public boolean trialTalked2;//是否与试炼主人二次对话
        public boolean isBranchEnd;//支线是否全部完结

        public boolean chooseEnd3(){
            return miaoYinTalked2 && !chooseEnd2;
        }

        @Override
        public CompoundTag toNbt() {
            CompoundTag tag = new CompoundTag();

            tag.putInt("choice", choice);
            tag.putBoolean("isBossDie", isBossDie);
            tag.putBoolean("isBossTalked", isBossTalked);
            tag.putBoolean("isBossFought", isBossFought);
            tag.putBoolean("isElderDie", isElderDie);
            tag.putBoolean("isElderTalked", isElderTalked);

            tag.putBoolean("miaoYinTalked1", miaoYinTalked1);
            tag.putBoolean("shangRenTalked", shangRenTalked);
            tag.putBoolean("afterTrial", afterTrial);
            tag.putBoolean("trialTalked1", trialTalked1);
            tag.putBoolean("talkToMaster", talkToMaster);
            tag.putBoolean("wandererTalked", wandererTalked);
            tag.putBoolean("miaoYinTalked2", miaoYinTalked2);
            tag.putBoolean("chooseEnd2", chooseEnd2);
            tag.putBoolean("trialTalked2", trialTalked2);
            tag.putBoolean("isBranchEnd", isBranchEnd);
            return tag;
        }

        @Override
        public void fromNbt(CompoundTag serverData) {
            super.fromNbt(serverData);
            miaoYinTalked1 = serverData.getBoolean("miaoYinTalked1");
            shangRenTalked = serverData.getBoolean("shangRenTalked");
            afterTrial = serverData.getBoolean("afterTrial");
            trialTalked1 = serverData.getBoolean("trialTalked1");
            talkToMaster = serverData.getBoolean("talkToMaster");
            wandererTalked = serverData.getBoolean("wandererTalked");
            miaoYinTalked2 = serverData.getBoolean("miaoYinTalked2");
            chooseEnd2 = serverData.getBoolean("chooseEnd2");
            trialTalked2= serverData.getBoolean("trialTalked2");
            isBranchEnd = serverData.getBoolean("isBranchEnd");
        }

    }

    public static class Biome3ProgressData extends BiomeProgressData {

    }

    public static class Biome4ProgressData extends BiomeProgressData {

    }

    public static File getFile(String worldName){
        return new File("config/"+TheCasketOfReveriesMod.MOD_ID+"/"+ worldName +".nbt");
    }

    public static void save(String worldName){

        try {
            CompoundTag saveData = toNbt();
            File file = getFile(worldName);
            TheCasketOfReveriesMod.LOGGER.info("saving data to {} :\n"+saveData, file.getAbsolutePath());
            if(!file.exists()){
                file.createNewFile();
            }
            NbtIo.write(saveData, file);
            TheCasketOfReveriesMod.LOGGER.info("over.");
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't save serverData", e);
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
                TheCasketOfReveriesMod.LOGGER.info("save data not found. created new save data: {}" + save.createNewFile(), save.getAbsolutePath());
            } else {
                TheCasketOfReveriesMod.LOGGER.info("reading data {} :\n"+saveData, save.getAbsolutePath());
                fromNbt(saveData);
                TheCasketOfReveriesMod.LOGGER.info("over.");
            }
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't read save serverData", e);
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
        serverData.putInt("dialogLength", DIALOG_LIST.size());
        serverData.put("dialogList", getDialogListNbt());
        serverData.putInt("taskLength", TASK_SET.size());
        serverData.put("taskList", getTaskListNbt());
        serverData.putInt("firstChoiceBiome", firstChoiceBiome);
        serverData.put("biome1", biome1.toNbt());
        serverData.put("biome2", biome2.toNbt());
        serverData.put("biome3", biome3.toNbt());
        serverData.put("biome4", biome4.toNbt());
        return serverData;
    }

    /**
     * 把服务端发来的nbt转成客户端的SaveUtil调用
     * @param serverData 从服务端发来的nbt
     */
    public static void fromNbt(CompoundTag serverData){
        TheCasketOfReveriesMod.LOGGER.info("reading from: \n" + serverData);
        alreadyInit = true;
        worldLevel = serverData.getInt("worldLevel");
        setDialogListFromNbt(serverData.getCompound("dialogList"), serverData.getInt("dialogLength"));
        setTaskListFromNbt(serverData.getCompound("taskList"), serverData.getInt("taskLength"));
        firstChoiceBiome = serverData.getInt("firstChoiceBiome");
        biome1.fromNbt(serverData.getCompound("biome1"));
        biome2.fromNbt(serverData.getCompound("biome2"));
        biome3.fromNbt(serverData.getCompound("biome3"));
        biome4.fromNbt(serverData.getCompound("biome4"));
    }

}
