package com.p1nero.dote.entity.ai.goal;

import com.p1nero.dote.entity.IWanderableEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class CustomWanderGoal<T extends PathfinderMob & IWanderableEntity> extends MeleeAttackGoal {

    private final T wanderMob;
    @Nullable
    private LivingEntityPatch<?> patch;
    protected final double attackRadiusSqr;

    public CustomWanderGoal(T mob, double speedModifier, boolean longMemory, double attackRadius) {
        super(mob, speedModifier, longMemory);
        this.wanderMob = mob;
        patch = EpicFightCapabilities.getEntityPatch(wanderMob, LivingEntityPatch.class);
        this.attackRadiusSqr = attackRadius * attackRadius;
    }

    @Override
    public boolean canUse() {
        patch = EpicFightCapabilities.getEntityPatch(wanderMob, LivingEntityPatch.class);
        if(patch == null){
            return false;
        }
        return !patch.getEntityState().movementLocked() && (wanderMob.getStrafingTime() > 0 || super.canUse());
    }

    public void tick() {
        System.out.println("using");
        if(patch == null){
            return;
        }
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            if (!this.patch.getEntityState().turningLocked()) {
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (!this.patch.getEntityState().movementLocked()) {
                boolean withDistance = this.attackRadiusSqr > this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (this.wanderMob.getStrafingTime() > 0) {
                    this.wanderMob.setStrafingTime(this.wanderMob.getStrafingTime() - 1);
                    this.mob.getNavigation().stop();
                    this.mob.lookAt(target, 30.0F, 30.0F);
                    this.mob.getMoveControl().strafe(withDistance && this.wanderMob.getStrafingForward() > 0.0F ? 0.0F : this.wanderMob.getStrafingForward(), this.wanderMob.getStrafingClockwise());
                } else if (withDistance) {
                    this.mob.getNavigation().stop();
                } else {
                    super.tick();
                }

            }
        }
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity p_25556_) {
        return attackRadiusSqr;
    }

}
