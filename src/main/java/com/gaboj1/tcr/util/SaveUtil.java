package com.gaboj1.tcr.util;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class SaveUtil {

    public static int worldLevel = 0;
    private static List<Dialog> conversationsRecord = new ArrayList<>();

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

    }

    public static void addDialog(Component name, Component content){
        conversationsRecord.add(new Dialog(name, content));
    }

    public static class BiomeData implements Serializable{

        public BiomeData(){

        }

        public BiomeData(int choice, boolean isBossDie, boolean isBossTalked, boolean isBossFought, boolean isElderDie, boolean isElderTalked, boolean isElderFought) {
            this.choice = choice;
            this.isBossDie = isBossDie;
            this.isBossTalked = isBossTalked;
            this.isBossFought = isBossFought;
            this.isElderDie = isElderDie;
            this.isElderTalked = isElderTalked;
            this.isElderFought = isElderFought;
        }

        int choice = 0;//0:no sure 1:boss 2:villager
        boolean isBossDie = false;
        boolean isBossTalked = false;
        boolean isBossFought = false;
        boolean isElderDie = false;
        boolean isElderTalked = false;
        boolean isElderFought = false;

    }

    public static class Biome1Data extends BiomeData{

        public Biome1Data(){
        }

        public Biome1Data(int choice, boolean isBossDefeated, boolean isBossTalked, boolean isBossFought, boolean isElderDefeated, boolean isElderTalked, boolean isElderFought) {
            super(choice, isBossDefeated, isBossTalked, isBossFought, isElderDefeated, isElderTalked, isElderFought);
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
            oos.writeObject(conversationsRecord);
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
            conversationsRecord = (List<Dialog>) ois.readObject();
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
