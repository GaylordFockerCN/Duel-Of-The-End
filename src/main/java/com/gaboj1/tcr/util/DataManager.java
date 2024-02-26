package com.gaboj1.tcr.util;

import net.minecraft.world.entity.player.Player;

public class DataManager {

    public static void putData(Player player, String key, int value){
        player.getPersistentData().putInt(key, value);
    }
    public static void putData(Player player, String key, String value){
        player.getPersistentData().putString(key, value);
    }
    public static void putData(Player player, String key, boolean value){
        player.getPersistentData().putBoolean(key, value);
    }

    public static void getBool(Player player, String key){
        player.getPersistentData().getBoolean(key);
    }
    public static void getInt(Player player, String key){
        player.getPersistentData().getInt(key);
    }
    public static void getString(Player player, String key){
        player.getPersistentData().getString(key);
    }

    //阵营判断
    public static Data isWhite =  new Data("is_white",true);
    public static Data boss1Defeated =  new Data("boss1_defeated",false);

    public static class Data{
        private String key;

        private boolean isLocked = false;//增加一个锁

        //Default 不知道怎么用上..
        private boolean defaultBool = false;
        private int defaultInt = 0;

        public Data(String key){
            this.key = key;
        }

        public Data(String key, boolean defaultBool){
            this.key = key;
            this.defaultBool = defaultBool;
        }

        public Data(String key, int defaultInt){
            this.key = key;
            this.defaultInt = defaultInt;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public void lock() {
            isLocked = true;
        }

        public void unLock() {
            isLocked = false;
        }


        public void putData(Player player, int value){
            if(!isLocked)
                player.getPersistentData().putInt(key, value);
        }
        public void putData(Player player, String value){
            if(!isLocked)
                player.getPersistentData().putString(key, value);
        }
        public void putData(Player player, boolean value){
            if(!isLocked)
                player.getPersistentData().putBoolean(key, value);
        }

        public boolean getBool(Player player){
            boolean re = player.getPersistentData().getBoolean(key);

            return player.getPersistentData().getBoolean(key);
        }
        public int getInt(Player player){
            return player.getPersistentData().getInt(key);
        }
        public String getString(Player player){
           return player.getPersistentData().getString(key);
        }

    }

}
