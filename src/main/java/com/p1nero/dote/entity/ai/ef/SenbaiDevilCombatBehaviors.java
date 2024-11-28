package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.custom.SenbaiDevil;
import net.minecraft.server.level.ServerLevel;
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
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(9999999).cooldown(0).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior((humanoidMobPatch -> {
                                        if(humanoidMobPatch.getOriginal() instanceof SenbaiDevil senbaiDevil){
                                            humanoidMobPatch.playAnimationSynchronized(Animations.ENDERMAN_DEATH, 0.15F);
                                            if(senbaiDevil.level() instanceof ServerLevel serverLevel){
                                                EntityType.LIGHTNING_BOLT.spawn(serverLevel, senbaiDevil.getOnPos(), MobSpawnType.TRIGGERED);
                                                EntityType.LIGHTNING_BOLT.spawn(serverLevel, senbaiDevil.getOnPos(), MobSpawnType.TRIGGERED);
                                            }
                                            senbaiDevil.setPhase2(true);
                                        }
                                    }))
                                    .custom((humanoidMobPatch -> {
                                        if(humanoidMobPatch.getOriginal() instanceof SenbaiDevil senbaiDevil){
                                            return !senbaiDevil.isPhase2();
                                        }
                                        return false;
                                    }))
                                    .health(0.5F, HealthPoint.Comparator.LESS_RATIO)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO1).withinDistance(0, 2.5).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.SWEEPING_EDGE).randomChance(0.6F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.LONGSWORD_DASH).withinDistance(0, 6)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO1).withinDistance(0, 2.5).withinEyeHeight().withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2).withinDistance(0, 2.5))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_AUTO1).withinDistance(0, 2.5))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_AUTO2).withinDistance(0, 2.5))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.TACHI_DASH).withinDistance(0, 6))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_KICK_COMBO).withinDistance(0, 2.5))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_ROLL_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playAnimationSynchronized(Animations.UCHIGATANA_AIR_SLASH, 0.15F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.RUSHING_TEMPO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO1).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1).withinDistance(0, 2.5).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).withinDistance(0, 2.5).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_KICK_COMBO).withinDistance(0, 2.5))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_ENDER_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch -> humanoidMobPatch.playAnimationSynchronized(Animations.UCHIGATANA_AIR_SLASH, 0.15F))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
            );

}
