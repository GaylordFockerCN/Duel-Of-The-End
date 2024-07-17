package com.gaboj1.tcr.entity.custom.boss;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

/**
 * 方便统一调难度
 */
public class TCRBoss extends PathfinderMob {
    protected TCRBoss(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }
}
