package com.p1nero.dote.gameasset;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.entity.ai.ef.api.IModifyAttackSpeedEntityPatch;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTEAnimations {

    public static StaticAnimation BIPED_HIT_LONG2;
    public static StaticAnimation GUARD_BREAK1;
    public static StaticAnimation GUARD_BREAK2;
    public static StaticAnimation KNOCKDOWN;
    //旧版本闪避动作

    public static StaticAnimation SSTEP_FORWARD;
    public static StaticAnimation SSTEP_BACKWARD;
    public static StaticAnimation SSTEP_LEFT;
    public static StaticAnimation SSTEP_RIGHT;

    //黑猴后滚
    public static StaticAnimation WK_DODGE_B1;
    public static StaticAnimation WK_DODGE_B2;
    public static StaticAnimation WK_DODGE_B3;

    public static StaticAnimation BIPED_HOLD_KATANA_SHEATHING;
    public static StaticAnimation BIPED_KATANA_SCRAP;
    public static StaticAnimation BIPED_HOLD_KATANA;
    public static StaticAnimation KATANA_AUTO1;
    public static StaticAnimation KATANA_AUTO2;
    public static StaticAnimation KATANA_AUTO3;
    public static StaticAnimation KATANA_SHEATHING_AUTO;
    public static StaticAnimation KATANA_SHEATHING_DASH;
    public static StaticAnimation KATANA_AIR_SLASH;
    public static StaticAnimation KATANA_SHEATH_AIR_SLASH;
    public static StaticAnimation FATAL_DRAW;
    public static StaticAnimation FATAL_DRAW_DASH;
    public static StaticAnimation RUN_KATANA;
    public static StaticAnimation WALK_KATANA;
    public static StaticAnimation BLADE_RUSH_FINISHER;

    public static StaticAnimation GREATSWORD_OLD_AUTO1;
    public static StaticAnimation GREATSWORD_OLD_AUTO2;
    public static StaticAnimation GREATSWORD_OLD_AUTO3;
    public static StaticAnimation GREATSWORD_OLD_AIRSLASH;
    public static StaticAnimation GREATSWORD_OLD_DASH;
    public static StaticAnimation GREATSWORD_OLD_IDLE;
    public static StaticAnimation GREATSWORD_OLD_WALK;
    public static StaticAnimation GREATSWORD_OLD_RUN;
    public static StaticAnimation WIND_SLASH;


    public static StaticAnimation DUAL_SLASH;

    public static StaticAnimation SSPEAR_ONEHAND_AUTO;
    public static StaticAnimation SSPEAR_DASH;
    public static StaticAnimation SSPEAR_TWOHAND_AUTO1;
    public static StaticAnimation SSPEAR_TWOHAND_AUTO2;
    public static StaticAnimation SPEAR_SLASH;

    public static StaticAnimation LONGSWORD_OLD_AUTO1;
    public static StaticAnimation LONGSWORD_OLD_AUTO2;
    public static StaticAnimation LONGSWORD_OLD_AUTO3;
    public static StaticAnimation LONGSWORD_OLD_AUTO4;
    public static StaticAnimation LONGSWORD_OLD_DASH;
    public static StaticAnimation LONGSWORD_OLD_AIRSLASH;
    public static StaticAnimation LETHAL_SLICING_START;
    public static StaticAnimation LETHAL_SLICING_ONCE;
    public static StaticAnimation LETHAL_SLICING_TWICE;
    public static StaticAnimation LETHAL_SLICING_ONCE1;


    public static StaticAnimation TACHI_TWOHAND_AUTO_1;
    public static StaticAnimation TACHI_TWOHAND_AUTO_2;
    public static StaticAnimation TACHI_TWOHAND_AUTO_3;
    public static StaticAnimation TACHI_TWOHAND_AUTO_4;

    public static StaticAnimation SWORD_ONEHAND_AUTO1;
    public static StaticAnimation SWORD_ONEHAND_AUTO2;
    public static StaticAnimation SWORD_ONEHAND_AUTO3;
    public static StaticAnimation SWORD_ONEHAND_AUTO4;
    public static StaticAnimation SWORD_ONEHAND_DASH;
    public static StaticAnimation SWORD_SLASH;

    public static StaticAnimation KATANA_SKILL2;
    public static StaticAnimation KATANA_SKILL3;
    public static StaticAnimation LION_CLAW;

    public static StaticAnimation YULLIAN_IDLE;
    public static StaticAnimation YULLIAN_WALK;
    public static StaticAnimation YULLIAN_RUN;
    public static StaticAnimation YULLIAN_COMBOA1;
    public static StaticAnimation YULLIAN_COMBOA2;
    public static StaticAnimation YULLIAN_COMBOA3;
    public static StaticAnimation YULLIAN_COMBOB1;
    public static StaticAnimation YULLIAN_COMBOC1;
    public static StaticAnimation YULLIAN_COMBOC2;
    public static StaticAnimation YULLIAN_DODGEATTACK;
    public static StaticAnimation YULLIAN_JUMP_HEAVYATTACK;
    public static StaticAnimation YULLIAN_JUMPPATTACK;
    public static StaticAnimation YULLIAN_SPECIALATTACK1;
    public static StaticAnimation YULLIAN_SPECIALATTACK2;
    public static StaticAnimation YULLIAN_SPECIALATTACK3;
    public static StaticAnimation YULLIAN_DASHAHATTCK;
    public static StaticAnimation SAKURA_DANCE;
    public static StaticAnimation WATERBIRDS_DANCE_WILDLY_A1;
    public static StaticAnimation WATERBIRDS_DANCE_WILDLY_A2;
    public static StaticAnimation WATERBIRDS_DANCE_WILDLY_A3;


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(DuelOfTheEndMod.MOD_ID, DOTEAnimations::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;
        SSTEP_FORWARD = new DodgeAnimation(0.15F, 0.55F, "biped/new/dodge/step_forward", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(DOTESounds.DODGE.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SSTEP_BACKWARD = new DodgeAnimation(0.05F, 0.55F, "biped/new/dodge/step_backward", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(DOTESounds.DODGE.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SSTEP_LEFT = new DodgeAnimation(0.02F, 0.50F, "biped/new/dodge/step_left", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(DOTESounds.DODGE.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SSTEP_RIGHT = new DodgeAnimation(0.02F, 0.50F, "biped/new/dodge/step_right", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(DOTESounds.DODGE.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        BIPED_HIT_LONG2 = new LongHitAnimation(0.08F, "biped/hit/hit_long", biped);
        KNOCKDOWN = new KnockdownAnimation(0.08F, "biped/hit/knockdown", biped);
        GUARD_BREAK1 = new LongHitAnimation(0.05F, "biped/hit/guard_break1", biped);
        GUARD_BREAK2 = new LongHitAnimation(0.05F, "biped/hit/guard_break2", biped);


        BIPED_HOLD_KATANA_SHEATHING = new StaticAnimation(true, "biped/new/katana/hold_katana_sheath", biped);
        BIPED_HOLD_KATANA = new StaticAnimation(true, "biped/new/katana/hold_katana", biped);
        RUN_KATANA = new StaticAnimation(true, "biped/new/katana/run_katana", biped);
        WALK_KATANA = new StaticAnimation(true, "biped/new/katana/walk_unsheath", biped);
        BIPED_KATANA_SCRAP = (new StaticAnimation(false, "biped/new/katana/katana_scrap", biped))
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(EpicFightSounds.SWORD_IN.get()));
        KATANA_AUTO1 = new BasicAttackAnimation(0.15F, 0.05F, 0.16F, 0.2F, null, biped.toolR, "biped/new/katana/katana_auto1", biped);
        KATANA_AUTO2 = new BasicAttackAnimation(0.20F, 0.05F, 0.11F, 0.2F, null, biped.toolR, "biped/new/katana/katana_auto2", biped);
        KATANA_AUTO3 = new BasicAttackAnimation(0.16F, 0.1F, 0.21F, 0.59F, null, biped.toolR, "biped/new/katana/katana_auto3", biped);
        KATANA_SHEATHING_AUTO = (new BasicAttackAnimation(0.06F, 0.05F, 0.1F, 0.65F, ColliderPreset.BATTOJUTSU, biped.rootJoint, "biped/new/katana/katana_sheath_auto", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get());
        KATANA_SHEATHING_DASH = (new DashAttackAnimation(0.06F, 0.05F, 0.05F, 0.11F, 0.65F, null, biped.toolR, "biped/new/katana/katana_sheath_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get());
        KATANA_AIR_SLASH = new AirSlashAnimation(0.1F, 0.05F, 0.16F, 0.3F, null, biped.toolR, "biped/new/katana/katana_airslash", biped);
        KATANA_SHEATH_AIR_SLASH = (new AirSlashAnimation(0.1F, 0.1F, 0.16F, 0.3F, null, biped.toolR, "biped/new/katana/katana_sheath_airslash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F);
        FATAL_DRAW = (new AttackAnimation(0.15F, 0.0F, 0.7F, 0.81F, 1.0F, ColliderPreset.BATTOJUTSU, biped.rootJoint, "biped/new/katana/skill/fatal_draw", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(EpicFightSounds.SWORD_IN.get()));
        FATAL_DRAW_DASH = (new AttackAnimation(0.15F, 0.43F, 0.85F, 0.851F, 1.4F, DOTEColliders.FATAL_DRAW_DASH, biped.rootJoint, "biped/new/katana/skill/fatal_draw_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(AnimationEvent.TimeStampedEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(EpicFightSounds.SWORD_IN.get()))
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.85F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                );

        BLADE_RUSH_FINISHER = new AttackAnimation(0.15F, 0.0F, 0.1F, 0.26F, 0.75F, DOTEColliders.BLADE_RUSH_FINISHER, biped.rootJoint, "biped/new/blade_rush_finisher", biped)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        GREATSWORD_OLD_IDLE = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/hold_greatsword", biped);
        GREATSWORD_OLD_WALK = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/walk_greatsword", biped);
        GREATSWORD_OLD_RUN = new MovementAnimation(0.1F, true, "biped/new/dual_greatsword/living/run_greatsword", biped);
        GREATSWORD_OLD_AUTO1 = (new BasicAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, InteractionHand.MAIN_HAND, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_twohand_auto_1", biped))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
        GREATSWORD_OLD_AUTO2 = (new BasicAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, InteractionHand.MAIN_HAND, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_twohand_auto_2", biped))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
        GREATSWORD_OLD_AUTO3 = (new BasicAttackAnimation(0.1F, 0.4F, 0.5F, 0.6F, InteractionHand.MAIN_HAND, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_twohand_auto_3", biped))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F);
        GREATSWORD_OLD_DASH = (new DashAttackAnimation(0.11F, 0.4F, 0.65F, 0.8F, 1.2F, ColliderPreset.GREATSWORD, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_dash", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE);
        GREATSWORD_OLD_AIRSLASH = (new AirSlashAnimation(0.1F, 0.5F, 0.55F, 0.71F, 0.75F, false, null, biped.toolR, "biped/new/dual_greatsword/combat/greatsword_airslash", biped));
        WIND_SLASH = new AttackAnimation(0.2F, "biped/new/wind_slash", biped,
                new AttackAnimation.Phase(.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        LETHAL_SLICING_START = new AttackAnimation(0.15F, 0.05F, 0.10F, 0.15F, 0.38F, ColliderPreset.FIST_FIXED, biped.rootJoint, "biped/new/lethal_slicing_start", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        LETHAL_SLICING_ONCE = new AttackAnimation(0.15F, 0.06F, 0.10F, 0.25F, 0.6F, DOTEColliders.LETHAL_SLICING, biped.rootJoint, "biped/new/lethal_slicing_once", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        LETHAL_SLICING_TWICE = new AttackAnimation(0.015F, 0.06F, 0.10F, 0.35F, 0.6F, DOTEColliders.LETHAL_SLICING, biped.rootJoint, "biped/new/lethal_slicing_twice", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(50.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        LETHAL_SLICING_ONCE1 = new AttackAnimation(0.015F, 0.06F, 0.10F, 0.15F, 0.85F, DOTEColliders.LETHAL_SLICING1, biped.rootJoint, "biped/new/lethal_slicing_once1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get()).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(50.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        SWORD_SLASH = new AttackAnimation(0.20F, 0.1F, 0.35F, 0.46F, 0.79F, ColliderPreset.BIPED_BODY_COLLIDER, biped.toolR, "biped/new/sword_slash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE);


        SSPEAR_ONEHAND_AUTO = new BasicAttackAnimation(0.16F, 0.1F, 0.2F, 0.45F, null, biped.toolR, "biped/new/spear/spear_onehand_auto", biped);
        SSPEAR_TWOHAND_AUTO1 = new BasicAttackAnimation(0.25F, 0.05F, 0.15F, 0.45F, null, biped.toolR, "biped/new/spear/spear_twohand_auto1", biped);
        SSPEAR_TWOHAND_AUTO2 = new BasicAttackAnimation(0.25F, 0.05F, 0.15F, 0.45F, null, biped.toolR, "biped/new/spear/spear_twohand_auto2", biped);
        SSPEAR_DASH = (new DashAttackAnimation(0.16F, 0.05F, 0.2F, 0.3F, 0.7F, null, biped.toolR, "biped/new/spear/spear_dash", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SPEAR_SLASH = (new AttackAnimation(0.11F, "biped/new/spear_slash", biped,
                new AttackAnimation.Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, biped.toolR, null),
                new AttackAnimation.Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, biped.toolR, null),
                new AttackAnimation.Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        DUAL_SLASH = new AttackAnimation(0.1F, "biped/new/dual_slash", biped,
                new AttackAnimation.Phase(.0F, 0.2F, 0.31F, 0.4F, 0.4F, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, biped.toolL, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.65F, 0.75F, 0.85F, 1.15F, Float.MAX_VALUE, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);


        LONGSWORD_OLD_AUTO1 = new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.4F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_1", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_AUTO2 = new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.4F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_2", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_AUTO3 = new BasicAttackAnimation(0.1F, 0.2F, 0.4F, 0.45F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_3", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_AUTO4 = new BasicAttackAnimation(0.2F, 0.3F, 0.4F, 0.7F, null, biped.toolR, "biped/new/longsword/longsword_twohand_auto_4", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);
        LONGSWORD_OLD_DASH = (new DashAttackAnimation(0.15F, 0.1F, 0.3F, 0.5F, 0.7F, null, biped.toolR, "biped/new/longsword/longsword_dash", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        LONGSWORD_OLD_AIRSLASH = new AirSlashAnimation(0.1F, 0.3F, 0.41F, 0.5F, null, biped.toolR, "biped/new/longsword/longsword_airslash", biped);


        TACHI_TWOHAND_AUTO_1 = new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_1", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);
        TACHI_TWOHAND_AUTO_2 = new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_2", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);
        TACHI_TWOHAND_AUTO_3 = new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.45F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_3", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        TACHI_TWOHAND_AUTO_4 = new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.65F, null, biped.toolR, "biped/new/longsword/tachi_twohand_auto_4", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        KATANA_SKILL2 = new AttackAnimation(0.0F, 0.65F, 0.70F, 0.73F, 1.05F, DOTEColliders.YAMATO_P, biped.toolR, "biped/new/longsword/skill/katana_skill2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        KATANA_SKILL3 = new AttackAnimation(0.05F, 0.06F, 0.10F, 0.35F, 0.6F, DOTEColliders.LETHAL_SLICING, biped.rootJoint, "biped/new/longsword/skill/katana_skill3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(15.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        SWORD_ONEHAND_AUTO1 = new BasicAttackAnimation(0.15F, 0.15F, 0.40F, 0.4F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO2 = new BasicAttackAnimation(0.15F, 0.15F, 0.25F, 0.40F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO3 = new BasicAttackAnimation(0.12F, 0.10F, 0.42F, 0.45F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO4 = new BasicAttackAnimation(0.10F, 0.15F, 0.35F, 0.6F, null, biped.toolR, "biped/new/sword/sword_onehand_auto_4", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_DASH = (new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, biped.toolR, "biped/new/sword/sword_onehand_dash", biped))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        LION_CLAW = new AttackAnimation(0.08F, 1.54F, 1.55F, 1.6F, 3.0F, null, biped.toolR, "biped/new/lion_claw", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get()).addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.2F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(EpicFightSounds.WHOOSH_BIG.get()));

        YULLIAN_WALK = new StaticAnimation(true, "biped/yullian/yullian_walk", biped);

        YULLIAN_RUN = new StaticAnimation(true, "biped/yullian/yullian_run", biped);

        YULLIAN_IDLE = new StaticAnimation(true, "biped/yullian/yullian_idle", biped);

        YULLIAN_COMBOA1 = new BasicAttackAnimation(0.1F, 0.8F, 0.93F, 1F, null, biped.toolR,
                "biped/yullian/yullian_comboa1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, (ValueModifier.multiplier(0.7F)))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.9F));

        YULLIAN_COMBOA2 = new BasicAttackAnimation(0.1F, 0.7F, 0.8F, 0.85F, null, biped.toolR,
                "biped/yullian/yullian_comboa2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, (ValueModifier.multiplier(1.2F)))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.9F));

        YULLIAN_COMBOA3 = new BasicAttackAnimation(0.1F, 0.467F, 0.6F, 2.43F, null, biped.toolR,
                "biped/yullian/yullian_comboa3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, (ValueModifier.multiplier(1.5F)));

        YULLIAN_COMBOB1 = new BasicMultipleAttackAnimation(0.1F, "biped/yullian/yullian_combob1", biped,
                new AttackAnimation.Phase(0F, 0.63F, 0.76F, 3.23F, 0.76F, InteractionHand.MAIN_HAND,
                        biped.toolR, null),
                new AttackAnimation.Phase(0.76F, 1.36F, 1.53F, 3.23F, 3.23F, InteractionHand.MAIN_HAND,
                        biped.toolR, null));

        YULLIAN_COMBOC1 = new BasicAttackAnimation(0.1F, 0.4F, 0.5F, 0.73F, null, biped.toolR,
                "biped/yullian/yullian_comboc1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.9F));

        YULLIAN_COMBOC2 = new BasicMultipleAttackAnimation(0.05F, "biped/yullian/yullian_comboc2", biped,
                new AttackAnimation.Phase(0F, 0.5F, 0.9F, 0.9F, 0.9F, InteractionHand.MAIN_HAND,
                        biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, (ValueModifier.multiplier(0.8F))),
                new AttackAnimation.Phase(0.9F, 0.9F, 1.1F, 1.5F, 1.5F, InteractionHand.MAIN_HAND,
                        biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, (ValueModifier.multiplier(0.8F))))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.9F));

        YULLIAN_DODGEATTACK = new BasicAttackAnimation(0.1F, 0.26F, 0.83F, 2.667F, null, biped.toolR,
                "biped/yullian/yullian_dodgeattack", biped);

        YULLIAN_JUMP_HEAVYATTACK = new BasicAttackAnimation(0.1F, 0.967F, 1.06F, 4.267F, null, biped.toolR,
                "biped/yullian/yullian_jump_heavyattack", biped);

        YULLIAN_JUMPPATTACK = new BasicAttackAnimation(0.1F, 0.67F, 0.76F, 2.83F, null, biped.toolR,
                "biped/yullian/yullian_jumpattack", biped);

        YULLIAN_DASHAHATTCK = new BasicAttackAnimation(0.1F, 1.5F, 1.9F, 2.83F, null, biped.toolR,
                "biped/yullian/yullian_dashattack", biped);

        YULLIAN_SPECIALATTACK1 = new BasicAttackAnimation(0.1F, 1.43F, 1.8F, 4.2F, null, biped.toolR,
                "biped/yullian/yullian_specialattack1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 2F));

        YULLIAN_SPECIALATTACK2 = new BasicAttackAnimation(0.1F, 1.23F, 1.8F, 4.167F, null, biped.toolR,
                "biped/yullian/yullian_specialattack2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, (ValueModifier.multiplier(2.5F)))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .newTimePair(0.1F, 1.0F)//无敌帧
                .addStateRemoveOld(EntityState.ATTACK_RESULT, DodgeAnimation.DODGEABLE_SOURCE_VALIDATOR)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 2F))
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.TimeStampedEvent.create((livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch.getOriginal() instanceof Player player && player.getHealth() > 1){
                        player.setHealth(player.getHealth() / 2.0F);
                        livingEntityPatch.playSound(SoundEvents.PLAYER_HURT, 1, 1);
                    } else {
                        livingEntityPatch.playSound(SoundEvents.END_PORTAL_SPAWN, 0.8F, 0.8F);
                    }
                }, AnimationEvent.Side.SERVER));

        YULLIAN_SPECIALATTACK3 = new BasicAttackAnimation(0.1F, 1.567F, 2.0F, 3.8F, null, biped.toolR, "biped/yullian/yullian_specialattack3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 2F));

        SAKURA_DANCE = new BasicMultipleAttackAnimation(0.2F, "biped/sakura_dance/sakura_dance", biped,
                new AttackAnimation.Phase(0F, 0.1F, 0.167F, 0.333F, 0.333F, InteractionHand.MAIN_HAND, biped.toolR, null),
                new AttackAnimation.Phase(0.333F, 0.333F, 0.5F, 0.5F, 0.5F, InteractionHand.MAIN_HAND, biped.toolR, null),
                new AttackAnimation.Phase(0.5F, 0.9F, 1.067F, 1.067F, 1.067F, InteractionHand.MAIN_HAND, biped.toolR, null))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.067F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 0.99F));

        WATERBIRDS_DANCE_WILDLY_A1 = new BasicMultipleAttackAnimation(0.15F, "biped/waterbirds/waterbirds_dance_wildly_a1", biped,
                new AttackAnimation.Phase(0F, 1.533F, 1.6333F, 1.6333F, 1.6333F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)),
                new AttackAnimation.Phase(1.6333F, 1.9F, 1.966F, 1.966F, 1.966F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F)),
                new AttackAnimation.Phase(1.966F, 2.133F, 2.2F, 2.2F, 2.2F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)),
                new AttackAnimation.Phase(2.2F, 2.4F, 2.466F, 2.73F, 4.5F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F)))
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.3F));

        WATERBIRDS_DANCE_WILDLY_A2 = new BasicAttackAnimation(0.15F, 0.8F, 0.9F, 2.5F, null, biped.toolR, "biped/waterbirds/waterbirds_dance_wildly_a2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.5F))
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.3F));

        WATERBIRDS_DANCE_WILDLY_A3 = new BasicMultipleAttackAnimation(0.15F, "biped/waterbirds/waterbirds_dance_wildly_a3", biped,
                new AttackAnimation.Phase(0.0F, 0.933F, 0.9666F, 0.9666F, 0.9666F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)),
                new AttackAnimation.Phase(0.9666F, 1.166F, 1.266F, 1.266F, 1.266F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)),
                new AttackAnimation.Phase(1.266F, 1.5333F, 1.6333F, 1.6333F, 1.6333F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)),
                new AttackAnimation.Phase(1.6333F, 1.9F, 1.966F, 1.966F, 1.966F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)),
                new AttackAnimation.Phase(1.966F, 1.9F, 1.966F, 3.0F, 4.4333F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F)))
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.3F));

        if(WOMAnimations.TIME_TRAVEL != null){
            WOMAnimations.TIME_TRAVEL.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> {
                if (livingEntityPatch instanceof IModifyAttackSpeedEntityPatch patch) {
                    return patch.getAttackSpeed();
                }
                return 1.0F;
            }));
        }

        WK_DODGE_B1 = new DodgeAnimation(0.1F, 0.4F, "biped/new/dodge/wkdodge_b1", 0.6F, 0.8F, biped);

        WK_DODGE_B2 = new DodgeAnimation(0.1F, 0.4F, "biped/new/dodge/wkdodge_b2", 0.6F, 0.8F, biped);

        WK_DODGE_B3 = new DodgeAnimation(0.1F, 0.6F, "biped/new/dodge/wkdodge_b3", 0.6F, 1.35F, biped).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);

    }


    //技能回复体力
    public static final AnimationEvent.AnimationEventConsumer STAMINA = (entitypatch, animation, params) -> {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            float currentStamina = playerPatch.getStamina();
            float maxStamina = playerPatch.getMaxStamina();
            float recoveredStamina = maxStamina * 0.10F;
            playerPatch.setStamina(currentStamina + recoveredStamina);
        }
    };


    public static final AnimationEvent.AnimationEventConsumer STAMINASKILL = (entitypatch, animation, params) -> {
        if (entitypatch instanceof PlayerPatch) {
            PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;
            float maxStamina = playerPatch.getMaxStamina();
            float currentStamina = playerPatch.getStamina();
            float recoveredStamina = maxStamina * 0.25F;
            playerPatch.setStamina(currentStamina + recoveredStamina);
        }
    };
}


