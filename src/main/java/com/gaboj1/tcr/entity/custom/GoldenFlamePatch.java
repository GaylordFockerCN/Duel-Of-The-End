package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.entity.ai.DOTECombatBehaviors;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

public class GoldenFlamePatch extends HumanoidMobPatch<GoldenFlame> {
    public GoldenFlamePatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public AttackResult tryHarm(Entity target, EpicFightDamageSource epicFightDamageSource, float amount) {
        //击中补刀
        if(epicFightDamageSource.getAnimation() == WOMAnimations.TORMENT_BERSERK_AUTO_2 && getOriginal().getHealth() < getOriginal().getMaxHealth() / 2){
            this.reserveAnimation(WOMAnimations.SOLAR_AUTO_4_POLVORA);
        }
        AttackResult attackResult = super.tryHarm(target, epicFightDamageSource, amount);
        //击中吸血+火焰附加
        if(attackResult.resultType.equals(AttackResult.ResultType.SUCCESS)){
            this.getOriginal().setHealth(this.getOriginal().getHealth() + 58);
            target.setRemainingFireTicks(100);
            System.out.println("1111");
        }
        return attackResult;
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        //免疫远程
        if(damageSource.isIndirect()){
            this.playAnimationSynchronized(Animations.BIPED_STEP_BACKWARD, 0.0F);
            return AttackResult.missed(0);
        }
//        //免疫硬直
        if(damageSource instanceof EpicFightDamageSource source){
            source.setStunType(StunType.NONE);
        }
        return super.tryHurt(damageSource, amount);
    }

    @Override
    public void initAnimator(Animator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.WITHER_SKELETON_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.WITHER_SKELETON_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.WITHER_SKELETON_CHASE);
        animator.addLivingAnimation(LivingMotions.FALL, Animations.BIPED_FALL);
        animator.addLivingAnimation(LivingMotions.MOUNT, Animations.BIPED_MOUNT);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_COMMON_NEUTRALIZED);
    }
    @Override
    protected void setWeaponMotions() {
        this.weaponLivingMotions = Maps.newHashMap();
        this.weaponLivingMotions.put(CapabilityItem.WeaponCategories.GREATSWORD, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, Set.of(Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_TWOHAND), Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_TWOHAND))));
        this.weaponAttackMotions = Maps.newHashMap();
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.GREATSWORD, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, DOTECombatBehaviors.GOLDEN_FLAME));
    }
    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

}
