package com.gaboj1.tcr.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.function.Consumer;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 * 懒得换成DataKey了呜呜将就一下吧
 */
public class DOTEPlayer {
    private boolean canEnterPBiome;

    public boolean canEnterPBiome() {
        return canEnterPBiome;
    }

    public void setCanEnterPBiome(boolean canEnterPBiome) {
        this.canEnterPBiome = canEnterPBiome;
    }

    private CompoundTag data = new CompoundTag();
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

    public void saveNBTData(CompoundTag tag){
        tag.put("customDataManager", data);
        tag.putBoolean("canEnterBiome", canEnterPBiome);
    }

    public void loadNBTData(CompoundTag tag){
        data = tag.getCompound("customDataManager");
        canEnterPBiome = tag.getBoolean("canEnterBiome");
    }

    public void copyFrom(DOTEPlayer old){
        data = old.data;
        canEnterPBiome = old.canEnterPBiome;
    }

}
