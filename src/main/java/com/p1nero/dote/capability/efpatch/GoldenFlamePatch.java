package com.p1nero.dote.capability.efpatch;

import com.p1nero.dote.entity.ai.ef.api.*;
import com.p1nero.dote.entity.ai.ef.GoldenFlameCombatBehaviors;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.p1nero.dote.entity.custom.GoldenFlame;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GoldenFlamePatch extends HumanoidMobPatch<GoldenFlame> implements IModifyStunTypeEntity, IModifyAttackDamageEntity, ITimeEventListEntity, IModifyAttackSpeedEntity {

    @Nullable
    private StunType stunTypeModify;

    private float damageModify = 0;
    private final List<TimeStampedEvent> list = new ArrayList<>();
    private static final EntityDataAccessor<Float> ATTACK_SPEED = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.FLOAT);

    private boolean onCharged2Hit;
    private boolean onDash;

    public boolean isOnCharged2Hit() {
        return onCharged2Hit;
    }

    public void resetOnCharged2Hit(){
        onCharged2Hit = false;
    }

    public boolean isOnDash() {
        return onDash;
    }

    public void setOnDash(boolean onDash){
        this.onDash = onDash;
    }

    public GoldenFlamePatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void onConstructed(GoldenFlame entityIn) {
        super.onConstructed(entityIn);
        entityIn.getEntityData().define(ATTACK_SPEED, 1.0F);
    }

    public void setStunTypeModify(@Nullable StunType stunTypeModify) {
        this.stunTypeModify = stunTypeModify;
    }

    public void setDamageModify(float damageModify) {
        this.damageModify = damageModify;
    }

    @Override
    public AttackResult tryHarm(Entity target, EpicFightDamageSource epicFightDamageSource, float amount) {
        //击中就变招
        if(epicFightDamageSource.getAnimation() == WOMAnimations.TORMENT_CHARGED_ATTACK_2){
            onCharged2Hit = true;
        }
        return super.tryHarm(target, epicFightDamageSource, amount);
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        //免疫远程
        if(damageSource.isIndirect()){
            this.playAnimationSynchronized(Animations.BIPED_STEP_BACKWARD, 0.0F);
            return AttackResult.missed(0);
        }
        return super.tryHurt(damageSource, amount);
    }

    @Nullable
    @Override
    public EpicFightDamageSource getEpicFightDamageSource() {
        if(epicFightDamageSource != null && stunTypeModify != null){
            epicFightDamageSource.setStunType(stunTypeModify);
        }
        return epicFightDamageSource;
    }

    @Override
    public float getModifiedBaseDamage(float baseDamage) {
        if(damageModify != 0){
            return damageModify;
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
    public void setNewDamage(float damage) {
        damageModify = damage;
    }

    @Override
    public List<TimeStampedEvent> getTimeEventList() {
        return list;
    }

    @Override
    public void addEvent(TimeStampedEvent event) {
        list.add(event);
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
