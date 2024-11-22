package com.p1nero.dote.capability.efpatch;

import com.mojang.datafixers.util.Pair;
import com.nameless.indestructible.api.animation.types.CommandEvent;
import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 坚不可摧没给接口只能自己写一个
 */
public class DOTEAdvancedCustomHumanoidMobPatchProvider extends AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider {
    public DOTEAdvancedCustomHumanoidMobPatchProvider(){

    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider(Map<WeaponCategory, Map<Style, CombatBehaviors.Builder<HumanoidMobPatch<?>>>> AHCombatBehaviors, Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, StaticAnimation>>>> AHWeaponMotions, Map<WeaponCategory, Map<Style, AdvancedCustomHumanoidMobPatch.GuardMotion>> guardMotions, int regenStaminaStandbyTime, boolean hasStunReduction, float maxStunShield, int reganShieldStandbyTime, float reganShieldMultiply, float staminaLoseMultiply, float guardRadius, float attackRadius, List<Pair<LivingMotion, StaticAnimation>> defaultAnimations, Map<StunType, StaticAnimation> stunAnimations, Map<Attribute, Double> attributeValues, Faction faction, double chasingSpeed, float scale, boolean hasBossBar, ResourceLocation bossBar, String name, List<CommandEvent.StunEvent> stunEvent, SoundEvent swingSound, SoundEvent hitSound, HitParticleType hitParticle) {
        this.AHCombatBehaviors = AHCombatBehaviors;
        this.AHWeaponMotions = AHWeaponMotions;
        this.guardMotions = guardMotions;
        this.regenStaminaStandbyTime = regenStaminaStandbyTime;
        this.hasStunReduction = hasStunReduction;
        this.maxStunShield = maxStunShield;
        this.reganShieldStandbyTime = reganShieldStandbyTime;
        this.reganShieldMultiply = reganShieldMultiply;
        this.staminaLoseMultiply = staminaLoseMultiply;
        this.guardRadius = guardRadius;
        this.attackRadius = attackRadius;
        this.defaultAnimations = defaultAnimations;
        this.stunAnimations = stunAnimations;
        this.attributeValues = attributeValues;
        this.faction = faction;
        this.chasingSpeed = chasingSpeed;
        this.scale = scale;
        this.hasBossBar = hasBossBar;
        this.bossBar = bossBar;
        this.name = name;
        this.stunEvent = stunEvent;
        this.swingSound = swingSound;
        this.hitSound = hitSound;
        this.hitParticle = hitParticle;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setAHCombatBehaviors(Map<WeaponCategory, Map<Style, CombatBehaviors.Builder<HumanoidMobPatch<?>>>> AHCombatBehaviors) {
        this.AHCombatBehaviors = AHCombatBehaviors;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setAHWeaponMotions(Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, StaticAnimation>>>> AHWeaponMotions) {
        this.AHWeaponMotions = AHWeaponMotions;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setGuardMotions(Map<WeaponCategory, Map<Style, AdvancedCustomHumanoidMobPatch.GuardMotion>> guardMotions) {
        this.guardMotions = guardMotions;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setRegenStaminaStandbyTime(int regenStaminaStandbyTime) {
        this.regenStaminaStandbyTime = regenStaminaStandbyTime;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setAttributeValues(Map<Attribute, Double> attributeValues) {
        this.attributeValues = attributeValues;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setBossBar(ResourceLocation bossBar) {
        this.bossBar = bossBar;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setChasingSpeed(double chasingSpeed) {
        this.chasingSpeed = chasingSpeed;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setDefaultAnimations(List<Pair<LivingMotion, StaticAnimation>> defaultAnimations) {
        this.defaultAnimations = defaultAnimations;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setFaction(Faction faction) {
        this.faction = faction;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setGuardRadius(float guardRadius) {
        this.guardRadius = guardRadius;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setHasBossBar(boolean hasBossBar) {
        this.hasBossBar = hasBossBar;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setHasStunReduction(boolean hasStunReduction) {
        this.hasStunReduction = hasStunReduction;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setHitParticle(HitParticleType hitParticle) {
        this.hitParticle = hitParticle;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setHitSound(SoundEvent hitSound) {
        this.hitSound = hitSound;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setMaxStunShield(float maxStunShield) {
        this.maxStunShield = maxStunShield;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setName(String name) {
        this.name = name;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setReganShieldMultiply(float reganShieldMultiply) {
        this.reganShieldMultiply = reganShieldMultiply;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setReganShieldStandbyTime(int reganShieldStandbyTime) {
        this.reganShieldStandbyTime = reganShieldStandbyTime;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setStaminaLoseMultiply(float staminaLoseMultiply) {
        this.staminaLoseMultiply = staminaLoseMultiply;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setStunAnimations(Map<StunType, StaticAnimation> stunAnimations) {
        this.stunAnimations = stunAnimations;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setStunEvent(List<CommandEvent.StunEvent> stunEvent) {
        this.stunEvent = stunEvent;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setSwingSound(SoundEvent swingSound) {
        this.swingSound = swingSound;
        return this;
    }

    public DOTEAdvancedCustomHumanoidMobPatchProvider setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
        return this;
    }
}
