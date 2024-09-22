package com.gaboj1.tcr.entity.ai.goal;

import com.gaboj1.tcr.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * 周围没人就回满血
 */
public class BossRecoverGoal extends Goal {
    private final LivingEntity boss;
    private final int dis;
    public BossRecoverGoal(LivingEntity boss, int dis){
        this.boss = boss;
        this.dis = dis;
    }
    @Override
    public boolean canUse() {
        return EntityUtil.getNearByPlayers(boss, dis).isEmpty();
    }

    @Override
    public void start() {
        boss.setHealth(boss.getMaxHealth());
    }
}
