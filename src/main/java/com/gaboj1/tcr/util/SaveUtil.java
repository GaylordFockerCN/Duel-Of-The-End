package com.gaboj1.tcr.util;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class SaveUtil {

    public static int worldLevel = 0;
    private static List<Dialog> dialogList = new ArrayList<>();

    private static HashSet<Dialog> dialogSet = new HashSet<>();

    @Nullable
    public static Biome firstChoiceBiome = null;//0 means null

    public static Biome1Data biome1 = new Biome1Data();
    public static Biome2Data biome2 = new Biome2Data();
    public static Biome3Data biome3 = new Biome3Data();
    public static Biome4Data biome4 = new Biome4Data();

    public record Dialog (Component name, Component content) implements Serializable{
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

    public static class BiomeData implements Serializable{

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
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(worldLevel);
            oos.writeObject(dialogList);
            oos.writeObject(dialogSet);
            oos.writeObject(firstChoiceBiome);
            oos.writeObject(biome1);
            oos.writeObject(biome2);
            oos.writeObject(biome3);
            oos.writeObject(biome4);
            oos.close();
            fos.close();
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't save save data", e);
        }

    }

    public static void read(){

        try {
            File save = new File(FILE_NAME);
            if(!save.exists()){
                if(save.createNewFile()){
                    save();
                }
                return;
            }
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            worldLevel = ois.readInt();
            dialogList = (ArrayList<Dialog>)ois.readObject();
            dialogSet = (HashSet<Dialog>)ois.readObject();
            firstChoiceBiome = (Biome) ois.readObject();
            biome1 = ((Biome1Data) ois.readObject());
            biome2 = ((Biome2Data) ois.readObject());
            biome3 = ((Biome3Data) ois.readObject());
            biome4 = ((Biome4Data) ois.readObject());
            ois.close();
            fis.close();
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't read save data", e);
        }
    }

}
