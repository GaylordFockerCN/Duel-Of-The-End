package com.gaboj1.tcr.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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

    public static boolean getBool(Player player, String key){
        return player.getPersistentData().getBoolean(key);
    }
    public static int getInt(Player player, String key){
        return player.getPersistentData().getInt(key);
    }
    public static String getString(Player player, String key){
        return player.getPersistentData().getString(key);
    }

    //阵营判断
    public static BoolData isWhite =  new BoolData("is_white",true,1);
    public static BoolData boss1Defeated =  new BoolData("boss1_defeated",false,2);
    public static BoolData gunGot =  new BoolData("gun_got",false,3);
    public static BoolData ammoGot =  new BoolData("ammo_got",false,4);
    public static BoolData drinkGot =  new BoolData("drink_got",false,5);

    //给予初始值
    public static void init(Player player){
        if(player == null)
            return;
        isWhite.init(player);
        boss1Defeated.init(player);
    }

    public static class Data {

        protected String key;
        protected boolean isLocked = false;//增加一个锁，用于初始化数据用
        protected int id;
        public Data(String key, int id){
            this.key = key;
            this.id = id;
        }

        public String getKey(){
            return key;
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

        public StringData(String key, String defaultString, int id){
            super(key,id);
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

        public IntData(String key, int defaultInt, int id) {
            super(key,id);
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
        final int maxNum = 10;
        public BoolData(String key, boolean defaultBool,int id) {
            super(key, id);
            this.defaultBool = defaultBool;
        }

        public void init(Player player){
            isLocked = player.getPersistentData().getBoolean(key+"isLocked");
            putBool(player,defaultBool);
        }

        public void putBool(Player player, boolean value){
            if(isLocked)
                return;

//            CompoundTag tag = player.getPersistentData();
//            if(!tag.contains("bool")){
//                ListTag boolTagsList = new ListTag();
//                for (int i = 0; i < maxNum; i++) {
//                    boolTagsList.add(new CompoundTag());
//                }
//                tag.put("bool", boolTagsList);
//            }
//            ListTag bool = tag.getList("bool", maxNum);
//            bool.getCompound(id).putBoolean(key,value);

            player.getPersistentData().putBoolean(key, value);

        }

        public void putBool(CompoundTag playerData ,boolean bool){
            if(isLocked)
                return;
            playerData.putBoolean(key,bool);

        }

        public boolean getBool(Player player){

//            CompoundTag tag = player.getPersistentData();
//            if(!tag.contains("bool")){
//                ListTag boolTagsList = new ListTag();
//                for (int i = 0; i < maxNum; i++) {
//                    boolTagsList.add(new CompoundTag());
//                }
//                tag.put("bool", boolTagsList);
//                return false;
//            }
//
//            System.out.println(key+"local"+player.isLocalPlayer()+player.getPersistentData().getList("bool",maxNum).getCompound(id).getBoolean(key));//操你妈傻逼为啥这行输出不了也不给我报个错 后面发现原来是因为ServerPlayer不能在客户端用。。
//
//            System.out.println(key+player.getPersistentData().getBoolean(key));
//            System.out.println("isLocked"+isLocked);
//            ListTag bool = tag.getList("bool", maxNum);
//            return bool.getCompound(id).getBoolean(key);

//            System.out.println("isLocked"+isLocked);
            return player.getPersistentData().getBoolean(key);
        }

        public boolean getBool(CompoundTag playerData){
//            System.out.println(playerData);
            return playerData.getBoolean(key);
        }

    }

}
