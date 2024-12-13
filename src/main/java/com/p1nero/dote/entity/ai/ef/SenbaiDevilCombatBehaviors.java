package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.custom.SenbaiDevil;
import com.p1nero.dote.gameasset.DOTEAnimations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.data.conditions.entity.HealthPoint;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.function.Consumer;

import static com.p1nero.dote.entity.ai.ef.DOTECombatBehaviors.*;

/**
 * 森白影魔AI
 */
public class SenbaiDevilCombatBehaviors {

    public static final Consumer<HumanoidMobPatch<?>> SUMMON_LIGHTNING = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal().level() instanceof ServerLevel serverLevel){
            EntityType.LIGHTNING_BOLT.spawn(serverLevel, humanoidMobPatch.getOriginal().getOnPos(), MobSpawnType.MOB_SUMMONED);
        }
    });

    public static final Consumer<HumanoidMobPatch<?>> LEFT_ENTITY_AFTER_IMAGE = (humanoidMobPatch -> {
        PathfinderMob mob = humanoidMobPatch.getOriginal();
        humanoidMobPatch.getOriginal().level().addParticle(WOMParticles.ENTITY_AFTER_IMAGE_WEAPON.get(), mob.getX(), mob.getY(), mob.getZ(), Double.longBitsToDouble(mob.getId()), 0.0, 0.0);
    });

    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> SENBAI = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            //变身
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(9999999).cooldown(0).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD)
                                    .custom((humanoidMobPatch -> {
                                        if(humanoidMobPatch.getOriginal() instanceof SenbaiDevil senbaiDevil){
                                            return !senbaiDevil.isPhase2();
                                        }
                                        return false;
                                    }))
                                    .health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior((humanoidMobPatch -> {
                                        if(humanoidMobPatch.getOriginal() instanceof SenbaiDevil senbaiDevil){
                                            humanoidMobPatch.playAnimationSynchronized(Animations.ENDERMAN_DEATH, 0.15F);
                                            humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 0.6F);
                                            if(senbaiDevil.level() instanceof ServerLevel serverLevel){
                                                EntityType.LIGHTNING_BOLT.spawn(serverLevel, senbaiDevil.getOnPos(), MobSpawnType.TRIGGERED);
                                                EntityType.LIGHTNING_BOLT.spawn(serverLevel, senbaiDevil.getOnPos(), MobSpawnType.TRIGGERED);
                                            }
                                            senbaiDevil.setPhase2(true);
                                        }
                                    })))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.SAKURA_DANCE, 0.15F)))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(20.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SUMMON_LIGHTNING))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(5000.0F).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.15F)).withinDistance(0.0F, 2.5F).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.SAKURA_DANCE))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(5000.0F).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.15F)).withinDistance(0.0F, 2.5F).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.SAKURA_DANCE))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_3, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 1.8F))).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3, 0.15F)))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_3, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 1.8F))).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3, 0.15F)))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_3, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.15F)).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.15F)).withinDistance(0, 8))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.15F)).withinDistance(0, 8))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_3, 0.15F)))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.SWEEPING_EDGE).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.LONGSWORD_DASH).withinDistance(0, 6)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.25F)).withinDistance(0.0F, 2.5F).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_AUTO1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_DASH).withinDistance(0, 6))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.RUSHING_TEMPO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.25F)).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.RUSHING_TEMPO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.25F)).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.RUSHING_TEMPO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.25F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 1.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3))
            );

}
