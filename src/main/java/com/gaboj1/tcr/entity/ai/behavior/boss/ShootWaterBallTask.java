package com.gaboj1.tcr.entity.ai.behavior.boss;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.MagicProjectile;
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

    /**
     * 口水也是水（
     */
    @Override
    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level) {
        MagicProjectile projectile = new MagicProjectile(level, tcrVillager);
        double x = target.getX() - tcrVillager.getX();
        double y = target.getY(0.3333333333333333) - projectile.getY();
        double z = target.getZ() - tcrVillager.getZ();
        double $$5 = Math.sqrt(x * x + z * z) * 0.20000000298023224;
        projectile.shoot(x, y + $$5, z, 1.5F, 20.0F);
        level.addFreshEntity(projectile);
    }

}
