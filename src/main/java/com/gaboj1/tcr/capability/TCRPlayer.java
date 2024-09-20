package com.gaboj1.tcr.capability;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 * 懒得换成DataKey了呜呜将就一下吧
 */
public class TCRPlayer {
    private CompoundTag data = new CompoundTag();
    private UUID fakePlayerUuid;
    private BlockPos bedPointBeforeEnter = BlockPos.ZERO;
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
    private ItemStack sword = ItemStack.EMPTY;
    public boolean getBoolean(String key){
        return data.getBoolean(key);
    }
    public double getDouble(String key){
        return data.getDouble(key);
    }
    public String getString(String key){
        return data.getString(key);
    }
    public void putBoolean(String key, boolean value){
        data.putBoolean(key, value);
    }
    public void putDouble(String key, double v){
        data.putDouble(key, v);
    }
    public void putString(String k, String v){
        data.putString(k, v);
    }
    public void setData(Consumer<CompoundTag> consumer) {
        consumer.accept(data);
    }

    public CompoundTag getData() {
        return data;
    }

    public UUID getFakePlayerUuid() {
        return fakePlayerUuid;
    }

    public void hasFakePlayerUuid(Consumer<UUID> consumer){
        if(fakePlayerUuid != null){
            consumer.accept(fakePlayerUuid);
        } else {
            TheCasketOfReveriesMod.LOGGER.error("fakePlayerUuid is null!");
        }
    }

    public void setFakePlayerUuid(UUID fakePlayerUuid) {
        this.fakePlayerUuid = fakePlayerUuid;
    }

    public void setBedPointBeforeEnter(BlockPos bedPointBeforeEnter) {
        this.bedPointBeforeEnter = bedPointBeforeEnter;
    }

    public BlockPos getBedPointBeforeEnter() {
        return bedPointBeforeEnter;
    }

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
        tag.put("customDataManager", data);

        tag.putUUID("fakePlayer", Objects.requireNonNullElseGet(fakePlayerUuid, UUID::randomUUID));

        tag.putInt("bedPointBeforeEnterX", bedPointBeforeEnter.getX());
        tag.putInt("bedPointBeforeEnterY", bedPointBeforeEnter.getY());
        tag.putInt("bedPointBeforeEnterZ", bedPointBeforeEnter.getZ());

        tag.putBoolean("isFlying", isFlying);
        tag.putBoolean("protectNextFall", protectNextFall);
        tag.putBoolean("hasEntity", hasSwordEntity);
        tag.putInt("hasSwordScreenEntity", swordScreenEntityCount);
        tag.putInt("rainCutterTimer", rainCutterTimer);
        tag.putBoolean("rainCutterCoolDown", isScreenCutterCoolDown);
        tag.putInt("yakshaMaskTimer", yakshaMaskTimer);
        tag.putInt("anticipationTick", anticipationTick);
        tag.putInt("flyingTick", flyingTick);

        tag.put("sword", Objects.requireNonNullElseGet(sword.serializeNBT(), CompoundTag::new));
    }

    public void loadNBTData(CompoundTag tag){
        data = tag.getCompound("customDataManager");

        fakePlayerUuid = tag.getUUID("fakePlayer");
        bedPointBeforeEnter = new BlockPos(tag.getInt("bedPointBeforeEnterX"), tag.getInt("bedPointBeforeEnterY"), tag.getInt("bedPointBeforeEnterZ"));

        isFlying = tag.getBoolean("isFlying");
        protectNextFall = tag.getBoolean("protectNextFall");
        hasSwordEntity = tag.getBoolean("hasEntity");
        rainCutterTimer = tag.getInt("rainCutterTimer");
        isScreenCutterCoolDown = tag.getBoolean("rainCutterCoolDown");
        yakshaMaskTimer = tag.getInt("yakshaMaskTimer");
        anticipationTick = tag.getInt("anticipationTick");
        flyingTick = tag.getInt("flyingTick");
        sword = ItemStack.of(tag.getCompound("sword"));
    }

    public void copyFrom(TCRPlayer old){
        data = old.data;

        fakePlayerUuid = old.fakePlayerUuid;
        bedPointBeforeEnter = old.bedPointBeforeEnter;

        isFlying = old.isFlying;
        protectNextFall = old.protectNextFall;
        hasSwordEntity = old.hasSwordEntity;
        rainCutterTimer = old.rainCutterTimer;
        isScreenCutterCoolDown = old.isScreenCutterCoolDown;
        yakshaMaskTimer = old.yakshaMaskTimer;
        anticipationTick = old.anticipationTick;
        flyingTick = old.flyingTick;
        sword = old.sword;
    }

}
