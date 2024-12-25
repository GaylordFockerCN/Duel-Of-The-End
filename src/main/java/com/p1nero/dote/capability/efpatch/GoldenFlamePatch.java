package com.p1nero.dote.capability.efpatch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.ai.ef.GoldenFlameCombatBehaviors;
import com.p1nero.dote.entity.custom.GoldenFlame;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

public class GoldenFlamePatch extends DOTEBossPatch<GoldenFlame> {

    private boolean onTormentAuto4Hit;

    public boolean isOnTormentAuto4Hit() {
        return onTormentAuto4Hit;
    }

    public void resetOnTormentAuto4Hit() {
        onTormentAuto4Hit = false;
    }

    public GoldenFlamePatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void updateEntityState() {
        super.updateEntityState();
        //尝试修复反神无伤
        if (this.getOriginal().getAntiFormTimer() > 0) {
            state.setState(EntityState.ATTACK_RESULT, (damage) -> AttackResult.ResultType.SUCCESS);
        }

    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = 1.2F;
        return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
    }

    @Override
    public AttackResult attack(EpicFightDamageSource damageSource, Entity target, InteractionHand hand) {
        AttackResult result = super.attack(damageSource, target, hand);
        if (result.resultType.dealtDamage()) {
            this.getOriginal().setHealth((float) (this.getOriginal().getHealth() + Math.max(this.getOriginal().getMaxHealth() * 0.005, result.damage * (DOTEArchiveManager.getWorldLevel() + 1.1 - this.getOriginal().getHealthRatio()))));
            target.setSecondsOnFire(10);
            if (damageSource.getAnimation().equals(WOMAnimations.TORMENT_AUTO_4)) {
                onTormentAuto4Hit = true;
            }
        }
        return result;
    }

    @Nullable
    @Override
    public EpicFightDamageSource getEpicFightDamageSource() {
        if (epicFightDamageSource != null && stunTypeModify != null) {
            epicFightDamageSource.setStunType(stunTypeModify);
        }
        return epicFightDamageSource;
    }

    @Override
    public float getModifiedBaseDamage(float baseDamage) {
        if (damageModify != 0) {
            return damageModify * baseDamage;
        }
        return super.getModifiedBaseDamage(baseDamage);
    }

    @Override
    public void initAnimator(Animator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.WITHER_SKELETON_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.WITHER_SKELETON_IDLE);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.WITHER_SKELETON_IDLE);
        animator.addLivingAnimation(LivingMotions.FALL, Animations.BIPED_FALL);
        animator.addLivingAnimation(LivingMotions.MOUNT, Animations.BIPED_MOUNT);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_COMMON_NEUTRALIZED);
    }

    @Override
    protected void setWeaponMotions() {
        this.weaponLivingMotions = Maps.newHashMap();
        this.weaponLivingMotions.put(CapabilityItem.WeaponCategories.GREATSWORD,
                ImmutableMap.of(CapabilityItem.Styles.TWO_HAND,
                        Set.of(Pair.of(LivingMotions.IDLE, Animations.WITHER_SKELETON_IDLE),
                                Pair.of(LivingMotions.WALK, Animations.WITHER_SKELETON_WALK),
                                Pair.of(LivingMotions.RUN, Animations.WITHER_SKELETON_WALK),
                                Pair.of(LivingMotions.CHASE, Animations.WITHER_SKELETON_WALK))));
        this.weaponLivingMotions.put(CapabilityItem.WeaponCategories.FIST,
                ImmutableMap.of(CapabilityItem.Styles.ONE_HAND,
                        Set.of(Pair.of(LivingMotions.IDLE, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                Pair.of(LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                Pair.of(LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                Pair.of(LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN))));
        this.weaponAttackMotions = Maps.newHashMap();
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.GREATSWORD, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, GoldenFlameCombatBehaviors.GOLDEN_FLAME_GREAT_SWORD));
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.FIST, ImmutableMap.of(CapabilityItem.Styles.ONE_HAND, GoldenFlameCombatBehaviors.GOLDEN_FLAME_FIST));
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

    /**
     * 免疫硬直
     */
    @Override
    public boolean applyStun(StunType stunType, float stunTime) {
        return false;
    }

    /**
     * 免疫硬直
     */
    @Override
    public void playAnimationSynchronized(StaticAnimation animation, float convertTimeModifier) {
        if(animation instanceof HitAnimation){
            return;
        }
        super.playAnimationSynchronized(animation, convertTimeModifier);
    }

    /**
     * 免疫硬直
     */
    @Override
    public void playAnimationSynchronized(StaticAnimation animation, float convertTimeModifier, AnimationPacketProvider packetProvider) {
        if(animation instanceof HitAnimation){
            return;
        }
        super.playAnimationSynchronized(animation, convertTimeModifier, packetProvider);
    }
}
