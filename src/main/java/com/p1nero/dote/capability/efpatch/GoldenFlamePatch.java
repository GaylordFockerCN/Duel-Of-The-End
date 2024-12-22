package com.p1nero.dote.capability.efpatch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.ai.ef.GoldenFlameCombatBehaviors;
import com.p1nero.dote.entity.ai.ef.GoldenFlameTargetChasingGoal;
import com.p1nero.dote.entity.ai.ef.api.*;
import com.p1nero.dote.entity.custom.GoldenFlame;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GoldenFlamePatch extends HumanoidMobPatch<GoldenFlame> implements IModifyStunTypeEntityPatch, IModifyAttackDamageEntityPatch, ITimeEventListEntityPatch, IModifyAttackSpeedEntityPatch {

    @Nullable
    private StunType stunTypeModify;

    private float damageModify = 0;
    private final List<TimeStampedEvent> list = new ArrayList<>();
    private static final EntityDataAccessor<Float> ATTACK_SPEED = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.FLOAT);

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
    public void onConstructed(GoldenFlame entityIn) {
        super.onConstructed(entityIn);
        entityIn.getEntityData().define(ATTACK_SPEED, 1.0F);
    }

    @Override
    public void setAIAsInfantry(boolean holdingRangedWeapon) {
        CombatBehaviors.Builder<HumanoidMobPatch<?>> builder = this.getHoldingItemWeaponMotionBuilder();
        if (builder != null) {
//            this.original.goalSelector.addGoal(0, new GoldenFlameAnimatedAttackGoal<>(this, builder.build(this)));
            this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, builder.build(this)));
            this.original.goalSelector.addGoal(1, new GoldenFlameTargetChasingGoal(this, this.getOriginal(), 1.0, true));
        }
    }

    @Override
    public void updateEntityState() {
        super.updateEntityState();
        if(this.getOriginal().getInactionTime() > 0){
            state.setState(EntityState.INACTION, true);
            state.setState(EntityState.CAN_BASIC_ATTACK, false);
        }
    }

    @Override
    public AttackResult attack(EpicFightDamageSource damageSource, Entity target, InteractionHand hand) {
        AttackResult result = super.attack(damageSource, target, hand);
        if(result.resultType.dealtDamage()){
            this.getOriginal().setHealth((float) (this.getOriginal().getHealth() + Math.max(this.getOriginal().getMaxHealth() * 0.005, result.damage * (DOTEArchiveManager.getWorldLevel() + 1.1 - this.getOriginal().getHealthRatio()))));
            target.setSecondsOnFire(10);
            if(damageSource.getAnimation().equals(WOMAnimations.TORMENT_AUTO_4)){
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
        animator.addLivingAnimation(LivingMotions.WALK, Animations.WITHER_SKELETON_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.WITHER_SKELETON_CHASE);
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
                                Pair.of(LivingMotions.RUN, Animations.WITHER_SKELETON_CHASE),
                                Pair.of(LivingMotions.CHASE, Animations.WITHER_SKELETON_CHASE))));
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

    @Override
    public void setStunType(StunType stunType) {
        stunTypeModify = stunType;
    }

    @Override
    @Nullable
    public StunType getStunType() {
        return stunTypeModify;
    }

    @Override
    public void setNewDamage(float damage) {
        damageModify = damage;
    }

    @Override
    public float getNewDamage() {
        return damageModify;
    }

    @Override
    public List<TimeStampedEvent> getTimeEventList() {
        return list;
    }

    @Override
    public boolean addEvent(TimeStampedEvent event) {
        return list.add(event);
    }

    @Override
    public float getAttackSpeed() {
        return this.original.getEntityData().get(ATTACK_SPEED);
    }

    @Override
    public void setAttackSpeed(float value) {
        this.original.getEntityData().set(ATTACK_SPEED, Math.abs(value));
    }

}
