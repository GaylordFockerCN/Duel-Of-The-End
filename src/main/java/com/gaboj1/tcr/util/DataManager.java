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
    public static BoolData isWhite =  new BoolData("is_white",true);
    public static BoolData boss1Defeated =  new BoolData("boss1_defeated",false);

    //给予初始值
    public static void init(Player player){
        if(player == null)
            return;
        System.out.println("inited");
        isWhite.init(player);
        boss1Defeated.init(player);
    }

    public static class Data {

        protected String key;
        protected boolean isLocked = false;//增加一个锁

        public Data(String key){
            this.key = key;
        }

        public void init(Player player){
            isLocked = player.getPersistentData().getBoolean(key+"isLocked");

        }

        public boolean isLocked() {
            return isLocked;
        }

        public void lock(Player player) {
            player.getPersistentData().putBoolean(key+"isLocked",true);
            isLocked = true;
        }

        public void unLock(Player player) {
            player.getPersistentData().putBoolean(key+"isLocked",false);
            isLocked = false;
        }

//        public void putData(Player player, int value){
//            if(!isLocked)
//                player.getPersistentData().putInt(key, value);
//        }
//        public void putData(Player player, String value){
//            if(!isLocked)
//                player.getPersistentData().putString(key, value);
//        }
//        public void putData(Player player, boolean value){
//            if(!isLocked)
//                player.getPersistentData().putBoolean(key, value);
//        }
//
//        public boolean getBool(Player player){
//            return player.getPersistentData().getBoolean(key);
//        }
//        public int getInt(Player player){
//            return player.getPersistentData().getInt(key);
//        }
//        public String getString(Player player){
//            return player.getPersistentData().getString(key);
//        }

    }

    public static class StringData extends Data {

        protected boolean isLocked = false;//增加一个锁
        protected String defaultString = "";

        public StringData(String key, String defaultString){
            super(key);
            this.defaultString = defaultString;
        }

        @Override
        public void init(Player player) {
            putString(player,defaultString);
        }

        public void putString(Player player, String value){
            if(!isLocked)
                player.getPersistentData().putString(key, value);
        }

        public String getString(Player player){
           return player.getPersistentData().getString(key);
        }

    }
    public static class IntData extends Data {

        private int defaultInt = 0;

        public IntData(String key, int defaultInt) {
            super(key);
            this.defaultInt = defaultInt;
        }

        public void init(Player player){
            isLocked = player.getPersistentData().getBoolean(key+"isLocked");
            putInt(player,defaultInt);
        }

        public void putInt(Player player, int value){
            if(!isLocked)
                player.getPersistentData().putInt(key, value);
        }

        public int getInt(Player player){
            return player.getPersistentData().getInt(key);
        }

    }
    public static class BoolData extends Data {

        boolean defaultBool = false;
        public BoolData(String key, boolean defaultBool) {
            super(key);
            this.defaultBool = defaultBool;
        }

        public void init(Player player){
            isLocked = player.getPersistentData().getBoolean(key+"isLocked");
            putBool(player,defaultBool);
        }

        public void putBool(Player player, boolean value){
            if(!isLocked)
                player.getPersistentData().putBoolean(key, value);
        }

        public boolean getBool(Player player){
            System.out.println(key+"local"+player.isLocalPlayer()+player.getPersistentData().getBoolean(key));
            return player.getPersistentData().getBoolean(key);
        }

    }

}
