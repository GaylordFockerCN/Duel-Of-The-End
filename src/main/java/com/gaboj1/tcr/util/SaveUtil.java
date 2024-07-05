package com.gaboj1.tcr.util;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagVisitor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class SaveUtil {

    private static boolean isOld;//用于判断服务端是否更新过数据

    public static int worldLevel = 0;
    private static List<Dialog> dialogList = new ArrayList<>();

    private static HashSet<Dialog> dialogSet = new HashSet<>();

    @Nullable
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

    public static void addDialog(Component name, Component content){
        Dialog dialog = new Dialog(name, content);
        if(!dialogSet.contains(dialog)){
            dialogList.add(dialog);
            dialogSet.add(dialog);
        }
    }

    public static List<Dialog> getDialogList() {
        return dialogList;
    }

    public static CompoundTag getDialogListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        for(int i = 0; i < dialogList.size(); i++){
            dialogListNbt.put("dialog"+i, dialogList.get(i).toNbt());
        }
        return dialogListNbt;
    }

    public static void setDialogListFromNbt(CompoundTag serverData, int size){
        for(int i = 0; i < size; i++){
            dialogList.set(i, Dialog.fromNbt(serverData.getCompound("dialog"+i)));
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

        public int choice = 0;//0:no sure 1:boss 2:villager
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

    }

    public static class Biome1Data extends BiomeData{

        public Biome1Data(){
        }

        public Biome1Data(int choice, boolean isBossDefeated, boolean isBossTalked, boolean isBossFought, boolean isElderDefeated, boolean isElderTalked, boolean isElderFought) {
            super(choice, isBossDefeated, isBossTalked, isBossFought, isElderDefeated, isElderTalked);
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
            return isBossFought && !isBossDie && isElderDie;
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

    public static final String FILE_NAME = "config/"+TheCasketOfReveriesMod.MOD_ID+"/save.dat";

    public static void save(){

        try {
            NbtIo.write(toNbt(), new File(FILE_NAME));
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't save save serverData", e);
        }

    }

    public static void read(){

        try {
            File save = new File(FILE_NAME);
            if(save.exists()){
                fromNbt(Objects.requireNonNull(NbtIo.read(save)));
            } else {
                save.createNewFile();
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
        serverData.putInt("dialogLength", dialogList.size());
        serverData.put("dialogList", getDialogListNbt());
        serverData.putInt("firstChoiceBiome", firstChoiceBiome);
        serverData.put("biome1", biome1.toNbt());
        serverData.put("biome2", biome2.toNbt());
        serverData.put("biome3", biome3.toNbt());
        serverData.put("biome4", biome4.toNbt());
        return new CompoundTag();
    }

    /**
     * 把服务端发来的nbt转成客户端的SaveUtil调用
     * @param serverData 从服务端发来的nbt
     */
    public static void fromNbt(CompoundTag serverData){
        worldLevel = serverData.getInt("worldLevel");
        setDialogListFromNbt(serverData, serverData.getInt("dialogLength"));
        firstChoiceBiome = serverData.getInt("firstChoiceBiome");
        biome1.fromNbt(serverData.getCompound("biome1"));
        biome2.fromNbt(serverData.getCompound("biome2"));
        biome3.fromNbt(serverData.getCompound("biome3"));
        biome4.fromNbt(serverData.getCompound("biome4"));
    }

}
