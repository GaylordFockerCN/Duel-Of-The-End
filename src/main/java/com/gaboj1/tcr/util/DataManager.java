package com.gaboj1.tcr.util;

import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.capability.TCRPlayer;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.PersistentBoolDataSyncPacket;
import com.gaboj1.tcr.network.packet.clientbound.PersistentDoubleDataSyncPacket;
import com.gaboj1.tcr.network.packet.clientbound.PersistentStringDataSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * 用于单一玩家数据，而不是全体数据，SaveUtil用于全体数据{@link SaveUtil}
 *
 * 别骂了，后来才知道可以用Capability，不过我发现这个就相当于是封装过的Capability哈哈
 *
 * 玩家的PersistentData管理
 * 本质上只是管理key。由于NBT标签不存在null，所以我加入了一个lock变量，如果还没lock就表示不确定性。比如isWhite，在玩家做出选择之前你不能确定是哪个阵营。
 * 虽然使用的时候麻烦了一点，但是多了个lock相当于把bool拆成四个量来用。
 * @author LZY
 */
public class DataManager {

    public static void putData(Player player, String key, double value){
        getTCRPlayer(player).putDouble(key, value);
    }
    public static void putData(Player player, String key, String value){
        getTCRPlayer(player).putString(key, value);
    }
    public static void putData(Player player, String key, boolean value){
        getTCRPlayer(player).putBoolean(key, value);
    }

    public static boolean getBool(Player player, String key){
        return getTCRPlayer(player).getBoolean(key);
    }
    public static double getDouble(Player player, String key){
        return getTCRPlayer(player).getDouble(key);
    }
    public static String getString(Player player, String key){
        return getTCRPlayer(player).getString(key);
    }
    public static TCRPlayer getTCRPlayer(Player player){
        return player.getCapability(TCRCapabilityProvider.TCR_PLAYER).orElse(new TCRPlayer());
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
    public static BoolData isMiaoYinGifted =  new BoolData("isMiaoYinGifted",false,11);
    public static BoolData stolenMiaoYin =  new BoolData("stolenMiaoYin",false,12);
    public static BoolData wanMingPearlGot =  new BoolData("wanMingPearlGot",false,13);
    public static BoolData miaoYinMoney1 =  new BoolData("miaoYinMoney1",false,14);

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
            isLocked = getTCRPlayer(player).getBoolean(key+"isLocked");

        }

        public boolean isLocked(Player player) {
            return getTCRPlayer(player).getBoolean(key+"isLocked");
        }

        public boolean isLocked(CompoundTag playerData) {
            return playerData.getBoolean(key+"isLocked");
        }

        public void lock(Player player) {
            getTCRPlayer(player).putBoolean(key+"isLocked",true);
            isLocked = true;
        }

        public void unLock(Player player) {
            getTCRPlayer(player).putBoolean(key+"isLocked",false);
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
                getTCRPlayer(player).putString(key, value);
                if(player instanceof ServerPlayer serverPlayer){
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentStringDataSyncPacket(key, isLocked,value),serverPlayer);
                }
            }
        }

        public String getString(Player player){
           return getTCRPlayer(player).getString(key);
        }

        public String getString(CompoundTag playerData){
            return playerData.getString(key);
        }

    }
    public static class DoubleData extends Data {

        private double defaultValue = 0;

        public DoubleData(String key, double defaultValue, int id) {
            super(key,id);
            this.defaultValue = defaultValue;
        }

        public void init(Player player){
            isLocked = getTCRPlayer(player).getBoolean(key+"isLocked");
            putInt(player, defaultValue);
        }

        public void putInt(Player player, double value){
            if(!isLocked(player)){
                getTCRPlayer(player).putDouble(key, value);
                if(player instanceof ServerPlayer serverPlayer){
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentDoubleDataSyncPacket(key, isLocked, value),serverPlayer);
                }
            }
        }

        public double getInt(Player player){
            return getTCRPlayer(player).getDouble(key);
        }

        public double getInt(CompoundTag playerData){
            return playerData.getDouble(key);
        }

    }
    public static class BoolData extends Data {

        boolean defaultBool;
        public BoolData(String key, boolean defaultBool,int id) {
            super(key, id);
            this.defaultBool = defaultBool;
        }

        public void init(Player player){
            isLocked = getTCRPlayer(player).getBoolean(key+"isLocked");
            putBool(player,defaultBool);
        }

        public void putBool(Player player, boolean value){
            if(isLocked(player))
                return;

            getTCRPlayer(player).putBoolean(key, value);
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
            return getTCRPlayer(player).getBoolean(key);
        }

        public boolean getBool(CompoundTag playerData){
            return playerData.getBoolean(key);
        }

    }

}
