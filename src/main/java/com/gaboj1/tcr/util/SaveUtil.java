package com.gaboj1.tcr.util;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class SaveUtil {

    public static int worldLevel = 0;

    /**
     * 获取随世界等级提升后的武器数值（即乘以倍率）
     */
    public static double getWeaponMultiplier(double weaponAttr){
        return weaponAttr * (SaveUtil.worldLevel == 0 ? 1 : SaveUtil.worldLevel* TCRConfig.WEAPON_MULTIPLIER_WHEN_WORLD_LEVEL_UP.get());
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

        /**
         * 移除任务说明任务完成了
         */
        @Override
        public boolean remove(Object o) {
            //TODO 全局广播任务完成
            if(o instanceof Dialog dialog){
//                PacketRelay.sendToServer(TCRPacketHandler.INSTANCE,);
            }
            return super.remove(o);
        }
    };
    public static int firstChoiceBiome = 0;//0 means null

    public static Biome1Data biome1 = new Biome1Data();
    public static Biome2Data biome2 = new Biome2Data();
    public static Biome3Data biome3 = new Biome3Data();
    public static Biome4Data biome4 = new Biome4Data();

    public record Dialog (Component name, Component content) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dialog dialog = (Dialog) o;
            return Objects.equals(name.toString(), dialog.name.toString()) && Objects.equals(content.toString(), dialog.content.toString());
        }

        @Override
        public int hashCode() {
            if(name == null || content == null){
                return 0;
            }
            return Objects.hash(name.toString(), content.toString());
        }

        @NotNull
        public CompoundTag toNbt(){
            CompoundTag dialog = new CompoundTag();
            dialog.putString("name", Component.Serializer.toJson(name));
            dialog.putString("content", Component.Serializer.toJson(content));
            return dialog;
        }

        public static Dialog fromNbt(CompoundTag dialog){
            return new Dialog(Component.Serializer.fromJson(dialog.getString("name")), Component.Serializer.fromJson(dialog.getString("content")));
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

    public static CompoundTag getTaskListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        List<Dialog> tasks = TASK_SET.stream().toList();
        for(int i = 0; i < tasks.size(); i++){
            dialogListNbt.put("task"+i, tasks.get(i).toNbt());
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

    public static void setTaskListFromNbt(CompoundTag taskListTag, int size){
        for(int i = 0; i < size; i++){
            TASK_SET.add(Dialog.fromNbt(taskListTag.getCompound("task"+i)));
        }
    }

    public static class BiomeData {

        public BiomeData(){

        }

        public BiomeData(int choice, boolean isBossDie, boolean isBossTalked, boolean isBossFought, boolean isElderDie, boolean isElderTalked) {
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

        public void finish(int choice){
            this.choice = choice;
            if(firstChoiceBiome == 0){
                firstChoiceBiome = biome;
            }
            worldLevel++;
        }

    }

    public static Dialog buildTask(String task){
        return new Dialog(Component.translatable( "task."+TheCasketOfReveriesMod.MOD_ID+task), Component.translatable( "task_content."+TheCasketOfReveriesMod.MOD_ID+task));
    }

    public static class Biome1Data extends BiomeData{

        public static Dialog taskKillElder = buildTask("kill_elder1");
        public static Dialog taskKillBoss = buildTask("kill_boss1");
        public static Dialog taskBackToBoss = buildTask("back_boss1");//回去领赏
        public static Dialog taskBackToElder = buildTask("back_elder1");
        public Biome1Data(){
            biome = 1;
        }

        /**
         * 判断boss是否能受伤，如果选择boss阵营则boss无法被打，杀死长老也视为boss阵营。
         */
        public boolean canAttackBoss(){
//            return isBossTalked && !isBossDie;
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
            return TASK_SET.contains(taskKillElder);
        }

        public boolean canGetElderReward(){
            return isBossDie;
        }


    }

    public static class Biome2Data extends BiomeData{

    }

    public static class Biome3Data extends BiomeData{

    }

    public static class Biome4Data extends BiomeData{

    }

    public static final String FILE_NAME = "config/"+TheCasketOfReveriesMod.MOD_ID+"/"+ TCRBiomeProvider.worldName +"/save.nbt";

    public static void save(){

        try {
            CompoundTag saveData = toNbt();
            TheCasketOfReveriesMod.LOGGER.info("saving data:\n"+saveData);
            NbtIo.write(saveData, new File(FILE_NAME));
            TheCasketOfReveriesMod.LOGGER.info("over.");
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't save save serverData", e);
        }

    }

    public static void read(){

        try {
            File save = new File(FILE_NAME);
            CompoundTag saveData = NbtIo.read(save);
            if(saveData == null){
                TheCasketOfReveriesMod.LOGGER.info("save data not found. created new save data:\n"+save.createNewFile());
            } else {
                TheCasketOfReveriesMod.LOGGER.info("reading data:\n"+saveData);
                fromNbt(saveData);
                TheCasketOfReveriesMod.LOGGER.info("over.");
            }
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't read save serverData", e);
        }
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
