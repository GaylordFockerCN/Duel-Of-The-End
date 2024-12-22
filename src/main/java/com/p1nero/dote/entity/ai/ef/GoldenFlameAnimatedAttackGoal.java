package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.capability.efpatch.GoldenFlamePatch;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class GoldenFlameAnimatedAttackGoal<T extends MobPatch<?>> extends Goal {
    protected final T mobpatch;
    protected final CombatBehaviors<T> combatBehaviors;

    public GoldenFlameAnimatedAttackGoal(T mobpatch, CombatBehaviors<T> combatBehaviors) {
        this.mobpatch = mobpatch;
        this.combatBehaviors = combatBehaviors;
    }

    public boolean canUse() {
        return this.checkTargetValid();
    }

    public void tick() {
        if (this.mobpatch.getTarget() != null) {
            EntityState state = this.mobpatch.getEntityState();
            this.combatBehaviors.tick();
            if (mobpatch instanceof GoldenFlamePatch goldenFlamePatch) {
                CombatBehaviors.Behavior<T> result;
                if (this.combatBehaviors.hasActivatedMove()) {
                    if (state.canBasicAttack() && goldenFlamePatch.getOriginal().getStrafingTime() <= 0) {
                        result = this.combatBehaviors.tryProceed();
                        if (result != null) {
                            result.execute(this.mobpatch);
                        }
                    }
                } else if (!state.inaction()
                        && !goldenFlamePatch.getOriginal().isCharging()
                        && goldenFlamePatch.getOriginal().getInactionTime() <= 0
                        && goldenFlamePatch.getOriginal().getStrafingTime() <= 0
                        && !goldenFlamePatch.getAnimator().getPlayerFor(null).getAnimation().equals(WOMAnimations.TORMENT_CHARGE)) {
                    result = this.combatBehaviors.selectRandomBehaviorSeries();
                    if (result != null) {
                        result.execute(this.mobpatch);
                    }
                }
            }
        }

    }

    protected boolean checkTargetValid() {
        LivingEntity livingentity = this.mobpatch.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
        }
    }
}
