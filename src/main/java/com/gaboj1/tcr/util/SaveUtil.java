package com.gaboj1.tcr.util;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * 保存游戏进度，这玩意儿应该所有人统一。
 */
public class SaveUtil {

    @Nullable
    public static Biome firstChoiceBiome;//0 means null

    public static BiomeData biome1,biome2,biome3,biome4;

    public static class BiomeData{

        public BiomeData(int choice, boolean isBossDefeated, boolean isBossTalked, boolean isBossFought, boolean isElderDefeated, boolean isElderTalked, boolean isElderFought) {
            this.choice = choice;
            this.isBossDefeated = isBossDefeated;
            this.isBossTalked = isBossTalked;
            this.isBossFought = isBossFought;
            this.isElderDefeated = isElderDefeated;
            this.isElderTalked = isElderTalked;
            this.isElderFought = isElderFought;
        }

        int choice;//0:no sure 1:boss 2:villager
        boolean isBossDefeated;
        boolean isBossTalked;
        boolean isBossFought;
        boolean isElderDefeated;
        boolean isElderTalked;
        boolean isElderFought;

    }

    public static String fileName = "config/"+TheCasketOfReveriesMod.MOD_ID+"/save.dat";

    public static void save(){

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
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
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            firstChoiceBiome = (Biome) ois.readObject();
            biome1 = ((BiomeData) ois.readObject());
            biome2 = ((BiomeData) ois.readObject());
            biome3 = ((BiomeData) ois.readObject());
            biome4 = ((BiomeData) ois.readObject());
            ois.close();
            fis.close();
        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("Can't read save data", e);
        }
    }

}
