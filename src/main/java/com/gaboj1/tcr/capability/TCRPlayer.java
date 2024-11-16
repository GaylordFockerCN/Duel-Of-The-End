package com.gaboj1.tcr.capability;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;
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
    private BlockPos lastPortalBlockPos = BlockPos.ZERO;
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
            DuelOfTheEndMod.LOGGER.error("fakePlayerUuid is null!");
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

    public void setLastPortalBlockPos(BlockPos lastPortalBlockPos) {
        this.lastPortalBlockPos = lastPortalBlockPos;
    }

    public BlockPos getLastPortalBlockPos() {
        return lastPortalBlockPos;
    }


    public void saveNBTData(CompoundTag tag){
        tag.put("customDataManager", data);

        tag.putUUID("fakePlayer", Objects.requireNonNullElseGet(fakePlayerUuid, UUID::randomUUID));

        tag.putInt("bedPointBeforeEnterX", bedPointBeforeEnter.getX());
        tag.putInt("bedPointBeforeEnterY", bedPointBeforeEnter.getY());
        tag.putInt("bedPointBeforeEnterZ", bedPointBeforeEnter.getZ());

        tag.putInt("lastPortalBlockPosX", lastPortalBlockPos.getX());
        tag.putInt("lastPortalBlockPosY", lastPortalBlockPos.getY());
        tag.putInt("lastPortalBlockPosZ", lastPortalBlockPos.getZ());
    }

    public void loadNBTData(CompoundTag tag){
        data = tag.getCompound("customDataManager");

        fakePlayerUuid = tag.getUUID("fakePlayer");
        bedPointBeforeEnter = new BlockPos(tag.getInt("bedPointBeforeEnterX"), tag.getInt("bedPointBeforeEnterY"), tag.getInt("bedPointBeforeEnterZ"));
        lastPortalBlockPos = new BlockPos(tag.getInt("lastPortalBlockPosX"), tag.getInt("lastPortalBlockPosY"), tag.getInt("lastPortalBlockPosZ"));

    }

    public void copyFrom(TCRPlayer old){
        data = old.data;
        fakePlayerUuid = old.fakePlayerUuid;
        bedPointBeforeEnter = old.bedPointBeforeEnter;
        lastPortalBlockPos = old.lastPortalBlockPos;

    }

}
