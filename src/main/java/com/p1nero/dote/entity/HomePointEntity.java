package com.p1nero.dote.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public interface HomePointEntity {
    void setHomePos(BlockPos homePos);
    BlockPos getHomePos();
    float getHomeRadius();
    default boolean inHome(){
        LivingEntity livingEntity = (LivingEntity) this;
        return livingEntity.position().distanceTo(getHomePos().getCenter()) < getHomeRadius();
    }
}
