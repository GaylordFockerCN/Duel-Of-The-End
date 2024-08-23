package com.gaboj1.tcr.util;

import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.PersistentBoolDataSyncPacket;
import com.gaboj1.tcr.network.packet.clientbound.PersistentIntDataSyncPacket;
import com.gaboj1.tcr.network.packet.clientbound.PersistentStringDataSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * 用于单一玩家数据，而不是全体数据，SaveUtil用于全体数据{@link SaveUtil}
 *
 * 玩家的PersistentData管理
 * 本质上只是管理key。由于NBT标签不存在null，所以我加入了一个lock变量，如果还没lock就表示不确定性。比如isWhite，在玩家做出选择之前你不能确定是哪个阵营。
 * 虽然使用的时候麻烦了一点，但是多了个lock相当于把bool拆成四个量来用。
 * @author LZY
 */
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

    //工匠送的火枪
    public static BoolData gunGot =  new BoolData("gun_got",false,4);
    public static BoolData ammoGot =  new BoolData("ammo_got",false,5);
    //侍者送的饮料
    public static BoolData drinkGot =  new BoolData("drink_got",false,6);
    //长老送的
    public static BoolData elderLoot1Got =  new BoolData("elder_loot1_got",false,7);
    public static BoolData elderLoot2Got =  new BoolData("elder_loot2_got",false,8);
    public static BoolData boss1LootGot =  new BoolData("boss1_loot_got",false,9);
    public static BoolData isFirstEnter =  new BoolData("isFirstEnter",false,10);

    //给予初始值
    public static void init(Player player){
        if(player == null) {
            //TODO
        }
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

        public boolean isLocked(Player player) {
            return player.getPersistentData().getBoolean(key+"isLocked");
        }

        public boolean isLocked(CompoundTag playerData) {
            return playerData.getBoolean(key+"isLocked");
        }

        public void lock(Player player) {
            player.getPersistentData().putBoolean(key+"isLocked",true);
            isLocked = true;
        }

        public void unLock(Player player) {
            player.getPersistentData().putBoolean(key+"isLocked",false);
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            isLocked = false;
        }

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
            if(!isLocked(player)){
                player.getPersistentData().putString(key, value);
                if(player instanceof ServerPlayer serverPlayer){
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentStringDataSyncPacket(key, isLocked,value),serverPlayer);
                }
            }
        }

        public String getString(Player player){
           return player.getPersistentData().getString(key);
        }

        public String getString(CompoundTag playerData){
            return playerData.getString(key);
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
            if(!isLocked(player)){
                player.getPersistentData().putInt(key, value);
                if(player instanceof ServerPlayer serverPlayer){
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentIntDataSyncPacket(key, isLocked,value),serverPlayer);
                }
            }
        }

        public int getInt(Player player){
            return player.getPersistentData().getInt(key);
        }

        public int getInt(CompoundTag playerData){
            return playerData.getInt(key);
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
            if(isLocked(player))
                return;

            player.getPersistentData().putBoolean(key, value);
            if(player instanceof ServerPlayer serverPlayer){
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentBoolDataSyncPacket(key, isLocked,value),serverPlayer);
            }
        }

        public void putBool(CompoundTag playerData ,boolean bool){
            if(isLocked(playerData))
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
//            System.out.println(key+"local"+player.isLocalPlayer()+player.getPersistentData().getList("bool",maxNum).getCompound(x).getBoolean(key));//操你妈傻逼为啥这行输出不了也不给我报个错 后面发现原来是因为ServerPlayer不能在客户端用。。
//
//            System.out.println(key+player.getPersistentData().getBoolean(key));
//            System.out.println("isLocked"+isLocked);
//            ListTag bool = tag.getList("bool", maxNum);
//            return bool.getCompound(x).getBoolean(key);

//            System.out.println("isLocked"+isLocked);
            return player.getPersistentData().getBoolean(key);
        }

        public boolean getBool(CompoundTag playerData){
//            System.out.println(playerData);
            return playerData.getBoolean(key);
        }

    }

}
