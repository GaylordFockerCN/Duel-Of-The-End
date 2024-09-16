package com.gaboj1.tcr.entity.ai.behavior.boss;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
        return tcrVillager.distanceTo(target) <= 36;
    }

    @Override
    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level) {
        RandomSource random = tcrVillager.getRandom();
        EntityType.LIGHTNING_BOLT.spawn(level, target.getOnPos().offset(random.nextInt(-2, 2), 0, random.nextInt(-2, 2)), MobSpawnType.TRIGGERED);
    }

}
