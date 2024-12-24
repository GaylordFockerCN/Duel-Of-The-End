package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.custom.SenbaiDevil;
import com.p1nero.dote.gameasset.DOTEAnimations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.data.conditions.entity.HealthPoint;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import static com.p1nero.dote.entity.ai.ef.DOTECombatBehaviors.*;

/**
 * 森白影魔AI
 */
public class SenbaiDevilCombatBehaviors {

    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> SENBAI = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            //变身
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(9999999).cooldown(9999).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WK_DODGE_B1)
                                    .health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WK_DODGE_B2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior((humanoidMobPatch -> {
                                        if (humanoidMobPatch.getOriginal() instanceof SenbaiDevil senbaiDevil) {
                                            humanoidMobPatch.playAnimationSynchronized(Animations.ENDERMAN_DEATH, 0.15F);
                                            humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 0.6F);
                                            if (senbaiDevil.level() instanceof ServerLevel serverLevel) {
                                                EntityType.LIGHTNING_BOLT.spawn(serverLevel, senbaiDevil.getOnPos(), MobSpawnType.TRIGGERED);
                                                EntityType.LIGHTNING_BOLT.spawn(serverLevel, senbaiDevil.getOnPos(), MobSpawnType.TRIGGERED);
                                            }
                                        }
                                    })))
                            //3级后追击
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2)).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 0.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.SAKURA_DANCE, 0.15F, 0.5F)))
            )
            //普通连段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.35F, 0.7F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_3, 0.15F)))
            )
            //普通连段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_COMBOA1, 0.35F, 0.7F)).withinDistance(0.0F, 2.5F).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_COMBOA2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_COMBOC1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_COMBOC2, 0.15F)))
            )
            //普通连段，半血后概率加突刺
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.35F, 0.7F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO3, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 0.8F))).health(0.5F, HealthPoint.Comparator.LESS_RATIO).randomChance(0.6F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.SWORD_AIR_SLASH, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.LONGSWORD_DASH, 0.15F)))
            )
            //2级世界后新增普通连段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LONGSWORD_OLD_AUTO1, 0.35F, 0.7F)).withinDistance(0.0F, 2.5F).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LONGSWORD_OLD_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LONGSWORD_OLD_AUTO3, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 0.8F))).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LONGSWORD_OLD_DASH, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LONGSWORD_OLD_AIRSLASH, 0.15F)))
            )
            //2级世界后半血下新增连段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(0.5F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_1, 0.35F, 0.7F)).health(0.5F, HealthPoint.Comparator.LESS_RATIO).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1, 0.15F, 0.5F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_AUTO_2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.15F, 0.5F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2)))
                            //3级多一段
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 0.8F))).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.15F, 0.5F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(LEFT_ENTITY_AFTER_IMAGE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3, 0.15F, 0.5F)))
            )
            //2级半血后技能
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(2).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 0.8F))).health(0.5F, HealthPoint.Comparator.LESS_RATIO).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_SPECIALATTACK2, 0.65F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_SPECIALATTACK2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.YULLIAN_SPECIALATTACK2, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.SAKURA_DANCE))
            )
            //3级半血后技能
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 0.8F))).health(0.5F, HealthPoint.Comparator.LESS_RATIO).withinDistance(0.0F, 2.5F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.65F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.15F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DOTEAnimations.SAKURA_DANCE))
            )
            //瞬步斩
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10).cooldown(150).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_AUTO1, 0.25F, 0.7F)).withinDistance(0.0F, 2.5F).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_AUTO1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.5F, 0.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.TACHI_DASH, 0.2F)))
            )
            //十字斩
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.ENDERMAN_KICK_COMBO, 0.25F)).withinDistance(0.0F, 2.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_DEATH, 0.2F, 0.8F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.UCHIGATANA_SHEATHING_DASH, 0.25F, 1.1F)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.RUSHING_TEMPO3, 0.0F, 1.1F)))
            );

}
