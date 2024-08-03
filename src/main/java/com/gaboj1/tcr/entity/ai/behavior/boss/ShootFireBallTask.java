package com.gaboj1.tcr.entity.ai.behavior.boss;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;

public class ShootFireBallTask extends TCRVillagerRetaliateTask {

    @Override
    protected int getAttackInterval() {
        return 50;
    }

    @Override
    protected boolean checkRange(TCRVillager tcrVillager, LivingEntity target) {
        return tcrVillager.distanceTo(target) <= 5;
    }

    /**
     * 发射火球
     */
    @Override
    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level) {
        double $$3 = target.getX() - tcrVillager.getX();
        double $$4 = target.getY(0.5) - tcrVillager.getY(0.5);
        double $$5 = target.getZ() - tcrVillager.getZ();
        double $$6 = Math.sqrt(Math.sqrt(tcrVillager.distanceTo(target))) * 0.5;
        SmallFireball $$8 = new SmallFireball(level, tcrVillager, tcrVillager.getRandom().triangle($$3, 2.297 * $$6), $$4, tcrVillager.getRandom().triangle($$5, 2.297 * $$6));
        $$8.setPos($$8.getX(), tcrVillager.getY(0.5) + 0.5, $$8.getZ());
        level.addFreshEntity($$8);
    }

}
