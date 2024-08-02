package com.gaboj1.tcr.entity.ai.behavior.boss;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;

import java.util.Random;

public class SummonLightningTask extends TCRVillagerRetaliateTask {
    @Override
    protected int getAttackInterval() {
        return 80;
    }

    @Override
    protected boolean checkRange(TCRVillager tcrVillager, LivingEntity target) {
        return tcrVillager.distanceTo(target) <= 5;
    }

    @Override
    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level) {
        Random random = new Random();
        EntityType.LIGHTNING_BOLT.spawn(level, target.getOnPos().offset(random.nextInt(-2, 2), 0, random.nextInt(-2, 2)), MobSpawnType.TRIGGERED);
    }

}
