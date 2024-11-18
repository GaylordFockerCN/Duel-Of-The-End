package com.p1nero.dote.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class StarChaserPatch extends HumanoidMobPatch<SenbaiDevil> {
    public StarChaserPatch() {
        super(Faction.VILLAGER);
    }

    /**
     * 无法被玩家攻击
     */
    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        if(damageSource.getEntity() instanceof Player){
            playAnimationSynchronized(Animations.BIPED_STEP_BACKWARD, 0.0F);
            return AttackResult.missed(0);
        }
        return super.tryHurt(damageSource, amount);
    }

    @Override
    public void initAnimator(Animator animator) {
        super.commonAggresiveMobAnimatorInit(animator);
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

}
