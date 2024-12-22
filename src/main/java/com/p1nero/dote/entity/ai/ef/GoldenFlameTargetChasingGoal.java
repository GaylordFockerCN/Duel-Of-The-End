package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.entity.custom.GoldenFlame;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class GoldenFlameTargetChasingGoal extends MeleeAttackGoal {
    protected final MobPatch<? extends GoldenFlame> mobpatch;
    protected final double attackRadiusSqr;

    public GoldenFlameTargetChasingGoal(MobPatch<? extends GoldenFlame> mobpatch, PathfinderMob pathfinderMob, double speedModifier, boolean longMemory) {
        this(mobpatch, pathfinderMob, speedModifier, longMemory, 0.0);
    }

    public GoldenFlameTargetChasingGoal(MobPatch<? extends GoldenFlame> mobpatch, PathfinderMob pathfinderMob, double speedModifier, boolean longMemory, double attackRadius) {
        super(pathfinderMob, speedModifier, longMemory);
        this.mobpatch = mobpatch;
        this.attackRadiusSqr = attackRadius * attackRadius;
    }

    public void tick() {

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            if (!this.mobpatch.getEntityState().turningLocked()) {
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (!this.mobpatch.getEntityState().movementLocked()) {
                boolean withDistance = this.attackRadiusSqr > this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (this.mobpatch.getOriginal().getStrafingTime() > 0) {
                    this.mobpatch.getOriginal().setStrafingTime(this.mobpatch.getOriginal().getStrafingTime() - 1);
                    this.mob.getNavigation().stop();
                    this.mob.lookAt(target, 30.0F, 30.0F);
                    this.mob.getMoveControl().strafe(withDistance && this.mobpatch.getOriginal().getStrafingForward() > 0.0F ? 0.0F : this.mobpatch.getOriginal().getStrafingForward(), this.mobpatch.getOriginal().getStrafingClockwise());
                } else if (withDistance) {
                    this.mob.getNavigation().stop();
                } else {
                    super.tick();
                }

            }
        }
    }

    protected void checkAndPerformAttack(@NotNull LivingEntity target, double p_25558_) {
    }
}
