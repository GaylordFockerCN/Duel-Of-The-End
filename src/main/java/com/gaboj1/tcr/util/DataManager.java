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

import java.util.ArrayList;
import java.util.List;

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

    public static void putData(Player player, String key, double value) {
        getTCRPlayer(player).putDouble(key, value);
    }

    public static void putData(Player player, String key, String value) {
        getTCRPlayer(player).putString(key, value);
    }

    public static void putData(Player player, String key, boolean value) {
        getTCRPlayer(player).putBoolean(key, value);
    }

    public static boolean getBool(Player player, String key) {
        return getTCRPlayer(player).getBoolean(key);
    }

    public static double getDouble(Player player, String key) {
        return getTCRPlayer(player).getDouble(key);
    }

    public static String getString(Player player, String key) {
        return getTCRPlayer(player).getString(key);
    }

    public static TCRPlayer getTCRPlayer(Player player) {
        return player.getCapability(TCRCapabilityProvider.TCR_PLAYER).orElseThrow(() -> new IllegalStateException("Player " + player.getName().getContents() + " has no TCR Player Capability!"));
    }

    public static List<BoolData> portalPointUnlockData = new ArrayList<>();
    public static BoolData villager1Unlock = new BoolData("villager1Unlock", false);
    public static BoolData villager2Unlock = new BoolData("villager2Unlock", false);
    public static BoolData villager3Unlock = new BoolData("villager3Unlock", false);
    public static BoolData villager4Unlock = new BoolData("villager4Unlock", false);
    public static BoolData boss1Unlock = new BoolData("boss1Unlock", false);
    public static BoolData boss2Unlock = new BoolData("boss2Unlock", false);
    public static BoolData boss3Unlock = new BoolData("boss3Unlock", false);
    public static BoolData boss4Unlock = new BoolData("boss4Unlock", false);
    public static BoolData finalUnlock = new BoolData("finalUnlock", false);
    static {
        portalPointUnlockData.add(villager1Unlock);
        portalPointUnlockData.add(villager2Unlock);
        portalPointUnlockData.add(villager3Unlock);
        portalPointUnlockData.add(villager4Unlock);
        portalPointUnlockData.add(boss1Unlock);
        portalPointUnlockData.add(boss2Unlock);
        portalPointUnlockData.add(boss3Unlock);
        portalPointUnlockData.add(boss4Unlock);
        portalPointUnlockData.add(finalUnlock);

    }

    //工匠送的火枪
    public static BoolData gunGot =  new BoolData("gun_got",false);
    public static BoolData ammoGot =  new BoolData("ammo_got",false);
    //侍者送的饮料
    public static BoolData drinkGot =  new BoolData("drink_got",false);
    //长老送的
    public static BoolData elderLoot1Got =  new BoolData("elder_loot1_got",false);
    public static BoolData elderLoot2Got =  new BoolData("elder_loot2_got",false);
    public static BoolData boss1LootGot =  new BoolData("boss1_loot_got",false);
    public static BoolData isSecondEnter =  new BoolData("isSecondEnter",false);
    public static BoolData isMiaoYinGifted =  new BoolData("isMiaoYinGifted",false);
    public static BoolData stolenMiaoYin =  new BoolData("stolenMiaoYin",false);
    public static BoolData wanMingPearlGot =  new BoolData("wanMingPearlGot",false);
    public static BoolData miaoYinMoney1 =  new BoolData("miaoYinMoney1",false);
    public static BoolData getFastModLoot =  new BoolData("getFastModLoot",false);

    public abstract static class Data<T> {

        protected String key;
        protected boolean isLocked = false;//增加一个锁，用于初始化数据用
        protected int id;
        public Data(String key){
            this.key = key;
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

        public abstract T get(Player player);
        public abstract void put(Player player, T data);

    }

    public static class StringData extends Data<String> {

        protected boolean isLocked = false;//增加一个锁
        protected String defaultString = "";

        public StringData(String key, String defaultString){
            super(key);
            this.defaultString = defaultString;
        }

        @Override
        public void init(Player player) {
            put(player, defaultString);
        }

        @Override
        public void put(Player player, String value) {
            if(!isLocked(player)){
                getTCRPlayer(player).putString(key, value);
                if(player instanceof ServerPlayer serverPlayer){
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentStringDataSyncPacket(key, isLocked, value),serverPlayer);
                }
            }
        }

        @Override
        public String get(Player player){
            return getTCRPlayer(player).getString(key);
        }

        public String get(CompoundTag playerData){
            return playerData.getString(key);
        }

    }
    public static class DoubleData extends Data<Double> {

        private double defaultValue = 0;

        public DoubleData(String key, double defaultValue) {
            super(key);
            this.defaultValue = defaultValue;
        }

        public void init(Player player){
            isLocked = getTCRPlayer(player).getBoolean(key+"isLocked");
            put(player, defaultValue);
        }

        @Override
        public void put(Player player, Double value) {
            if(!isLocked(player)){
                getTCRPlayer(player).putDouble(key, value);
                if(player instanceof ServerPlayer serverPlayer){
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentDoubleDataSyncPacket(key, isLocked, value),serverPlayer);
                }
            }
        }

        @Override
        public Double get(Player player){
            return getTCRPlayer(player).getDouble(key);
        }

        public double get(CompoundTag playerData){
            return playerData.getDouble(key);
        }

    }
    public static class BoolData extends Data<Boolean> {

        boolean defaultBool;
        public BoolData(String key, boolean defaultBool) {
            super(key);
            this.defaultBool = defaultBool;
        }

        public void init(Player player){
            isLocked = getTCRPlayer(player).getBoolean(key+"isLocked");
            put(player,defaultBool);
        }

        @Override
        public void put(Player player, Boolean value){
            if(isLocked(player))
                return;

            getTCRPlayer(player).putBoolean(key, value);
            if(player instanceof ServerPlayer serverPlayer){
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentBoolDataSyncPacket(key, isLocked, value),serverPlayer);
            }
        }

        @Override
        public Boolean get(Player player){
            return getTCRPlayer(player).getBoolean(key);
        }

        public boolean get(CompoundTag playerData){
            return playerData.getBoolean(key);
        }

    }

}
