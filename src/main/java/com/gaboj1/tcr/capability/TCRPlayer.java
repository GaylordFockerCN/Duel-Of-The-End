package com.gaboj1.tcr.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 * 懒得换成DataKey了呜呜将就一下吧
 */
public class TCRPlayer {
    private boolean isFlying;
    private boolean protectNextFall;
    private boolean hasSwordEntity;
    private int swordScreenEntityCount;
    private int rainCutterTimer;
    private boolean isScreenCutterCoolDown;
    private int yakshaMaskTimer;
    private int flyingTick;
    private Set<Integer> swordID;
    private int anticipationTick;
    private ItemStack sword;

    public boolean isFlying() {
        return isFlying;
    }

    public void setHasSwordEntity(boolean hasSwordEntity) {
        this.hasSwordEntity = hasSwordEntity;
    }

    public int getSwordScreenEntityCount() {
        return swordScreenEntityCount;
    }

    public void setSwordScreenEntityCount(int swordScreenEntityCount) {
        if(swordScreenEntityCount < 0){
            return;
        }
        this.swordScreenEntityCount = swordScreenEntityCount;
    }

    public void setSwordID(Set<Integer> swordID) {
        this.swordID = swordID;
    }

    public Set<Integer> getSwordScreensID() {
        if(swordID == null){
            swordID = new HashSet<>();
        }
        return swordID;
    }

    public ItemStack getSword() {
        if(sword == null){
            return ItemStack.EMPTY;
        }
        return sword;
    }

    public void setSword(ItemStack sword) {
        this.sword = sword;
    }

    public void saveNBTData(CompoundTag tag){
        tag.putBoolean("isFlying", isFlying);
        tag.putBoolean("protectNextFall", protectNextFall);
        tag.putBoolean("hasEntity", hasSwordEntity);
        tag.putInt("hasSwordScreenEntity", swordScreenEntityCount);
        tag.putInt("rainCutterTimer", rainCutterTimer);
        tag.putBoolean("rainCutterCoolDown", isScreenCutterCoolDown);
        tag.putInt("yakshaMaskTimer", yakshaMaskTimer);
        tag.putInt("anticipationTick", anticipationTick);
        tag.putInt("flyingTick", flyingTick);
        if(sword != null){
            tag.put("sword", sword.serializeNBT());
        }else {
            tag.put("sword", new CompoundTag());
        }
    }

    public void loadNBTData(CompoundTag tag){
        isFlying = tag.getBoolean("isFlying");
        protectNextFall = tag.getBoolean("protectNextFall");
        hasSwordEntity = tag.getBoolean("hasEntity");
//        swordScreenEntityCount = tag.getInt("hasSwordScreenEntity");
        rainCutterTimer = tag.getInt("rainCutterTimer");
        isScreenCutterCoolDown = tag.getBoolean("rainCutterCoolDown");
        yakshaMaskTimer = tag.getInt("yakshaMaskTimer");
        anticipationTick = tag.getInt("anticipationTick");
        flyingTick = tag.getInt("flyingTick");
        sword = ItemStack.of(tag.getCompound("sword"));
    }

    public void copyFrom(TCRPlayer old){
        isFlying = old.isFlying;
        protectNextFall = old.protectNextFall;
        hasSwordEntity = old.hasSwordEntity;
//        swordScreenEntityCount = old.swordScreenEntityCount;
        rainCutterTimer = old.rainCutterTimer;
        isScreenCutterCoolDown = old.isScreenCutterCoolDown;
        yakshaMaskTimer = old.yakshaMaskTimer;
        anticipationTick = old.anticipationTick;
        flyingTick = old.flyingTick;
        sword = old.sword;
    }

}
