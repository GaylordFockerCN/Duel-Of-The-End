package com.gaboj1.tcr.entity.ai;

import com.gaboj1.tcr.entity.custom.GoldenFlame;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.data.conditions.entity.HealthPoint;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public class DOTECombatBehaviors {
    public static final Consumer<HumanoidMobPatch<?>> ROTATE_TO_TARGET = (humanoidMobPatch -> {
        if(humanoidMobPatch.getTarget() != null){
            humanoidMobPatch.rotateTo(humanoidMobPatch.getTarget(), 30F, true);
        }
    });

    public static final Consumer<HumanoidMobPatch<?>> MOVE_TO_TARGET = (humanoidMobPatch -> {
        if(humanoidMobPatch.getTarget() != null){
            humanoidMobPatch.getOriginal().moveTo(humanoidMobPatch.getTarget().position());
            humanoidMobPatch.rotateTo(humanoidMobPatch.getTarget(), 30F, true);
        }
    });

    public static final Consumer<HumanoidMobPatch<?>> RANDOM_STEP = (humanoidMobPatch -> {
        List<StaticAnimation> steps = new ArrayList<>();
        steps.add(WOMAnimations.ENDERSTEP_BACKWARD);
        steps.add(WOMAnimations.ENDERSTEP_FORWARD);
        steps.add(WOMAnimations.ENDERSTEP_LEFT);
        steps.add(WOMAnimations.ENDERSTEP_RIGHT);
        humanoidMobPatch.playAnimationSynchronized(steps.get(new Random().nextInt(steps.size())), 0.0F);
    });

    public static final Consumer<HumanoidMobPatch<?>> FLAME_TIP = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal().level() instanceof ServerLevel serverLevel){
            Vec3 pos = humanoidMobPatch.getOriginal().position();
            serverLevel.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 200, 0, 1, 0, 0.1);
        }
        humanoidMobPatch.playSound(SoundEvents.WITHER_SHOOT, 1, 1);
    });

    public static final Consumer<HumanoidMobPatch<?>> PLAY_SLAM_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1));

    public static final Consumer<HumanoidMobPatch<?>> CLEAR_CHARGE = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            goldenFlame.resetCharging();
            if(goldenFlame.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.FLAME, goldenFlame.getX(), goldenFlame.getY(), goldenFlame.getZ(), 200, 0, 1, 0, 0.1);
            }
        }
        humanoidMobPatch.playSound(SoundEvents.WITHER_SHOOT, 1, 1);
    });

    public static final Function<HumanoidMobPatch<?>, Boolean> IS_CHARGING = humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            return goldenFlame.isCharging();
        }
        return false;
    };

    public static final Function<HumanoidMobPatch<?>, Boolean> IS_NOT_CHARGING = humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            return !goldenFlame.isCharging();
        }
        return false;
    };

    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> SENBAI = CombatBehaviors.<HumanoidMobPatch<?>>builder()
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
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.RUSHING_TEMPO3))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(50.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_KICK_COMBO).withinDistance(0, 2.5))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_STEP))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AIR_SLASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.KATANA_AUTO_3).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
            );

    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> GOLDEN_FLAME = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_2).custom(IS_NOT_CHARGING).withinDistance(0, 4).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_1).custom(IS_NOT_CHARGING).withinDistance(0, 4).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_4)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_1).custom(IS_NOT_CHARGING).withinDistance(0, 4).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3_POLVORA))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3_POLVORA).health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3_POLVORA))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3_POLVORA))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_2_POLVORA).randomChance(0.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            //开始蓄力
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(300.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_CHARGE).custom(IS_NOT_CHARGING).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    goldenFlame.startCharging();
                                }
                            })))
            //一蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).randomChance(0.5F).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 110;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            //二蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).randomChance(0.5F).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 80;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_CHARGED_ATTACK_2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal().getHealth() < humanoidMobPatch.getOriginal().getMaxHealth() / 2){
                                    humanoidMobPatch.playAnimationSynchronized(WOMAnimations.SOLAR_HORNO, 0.0F);
                                } else {
                                    if(humanoidMobPatch.getOriginal().getRandom().nextBoolean()){
                                        humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_AUTO_4, 0.0F);
                                    } else {
                                        humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.0F);
                                    }
                                }
                                humanoidMobPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);
                            })))
            //三蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(4, 12).randomChance(0.5F).withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_DASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.GREATSWORD_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            //四蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(200.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 16).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 20;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_DASH))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.GREATSWORD_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AIRSLAM))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_BERSERK_AUTO_1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_BERSERK_AUTO_2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_HORNO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_2_POLVORA))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)));


}
