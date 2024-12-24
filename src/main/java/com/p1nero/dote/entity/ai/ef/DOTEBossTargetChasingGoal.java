package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.entity.custom.DOTEBoss;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class DOTEBossTargetChasingGoal extends MeleeAttackGoal {
    protected final MobPatch<? extends DOTEBoss> mobpatch;
    protected final double attackRadiusSqr;

    public DOTEBossTargetChasingGoal(MobPatch<? extends DOTEBoss> mobpatch, PathfinderMob pathfinderMob, double speedModifier, boolean longMemory) {
        this(mobpatch, pathfinderMob, speedModifier, longMemory, 0.0);
    }

    public DOTEBossTargetChasingGoal(MobPatch<? extends DOTEBoss> mobpatch, PathfinderMob pathfinderMob, double speedModifier, boolean longMemory, double attackRadius) {
        super(pathfinderMob, speedModifier, longMemory);
        this.mobpatch = mobpatch;
        this.attackRadiusSqr = attackRadius * attackRadius;
    }

    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            double d0 = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (this.mobpatch.getOriginal().getStrafingTime() > 0) {
                this.mobpatch.getOriginal().setStrafingTime(this.mobpatch.getOriginal().getStrafingTime() - 1);
                this.mob.getNavigation().stop();
                this.mob.lookAt(target, 30.0F, 30.0F);
                this.mob.getMoveControl().strafe(d0 <= this.attackRadiusSqr && this.mobpatch.getOriginal().getStrafingForward() > 0.0F ? 0.0F : this.mobpatch.getOriginal().getStrafingForward(), this.mobpatch.getOriginal().getStrafingClockwise());
            } else if (d0 <= this.attackRadiusSqr) {
                this.mob.getNavigation().stop();
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            } else {
                super.tick();
            }
        }
    }

    protected void checkAndPerformAttack(@NotNull LivingEntity target, double p_25558_) {
    }
}
