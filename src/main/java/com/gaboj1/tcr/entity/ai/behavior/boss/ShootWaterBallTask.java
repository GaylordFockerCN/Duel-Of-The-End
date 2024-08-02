package com.gaboj1.tcr.entity.ai.behavior.boss;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class ShootWaterBallTask extends TCRVillagerRetaliateTask {

    @Override
    protected int getAttackInterval() {
        return 50;
    }

    @Override
    protected boolean checkRange(TCRVillager tcrVillager, LivingEntity target) {
        return tcrVillager.distanceTo(target) <= 5;
    }

    @Override
    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level) {
        //TODO 发射水球
    }

}
