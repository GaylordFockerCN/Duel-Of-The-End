package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.efpatch.GoldenFlamePatch;
import com.p1nero.dote.entity.custom.GoldenFlame;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.data.conditions.entity.HealthPoint;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.p1nero.dote.entity.ai.ef.DOTECombatBehaviors.*;

/**
 * 金焰神王AI
 */
public class GoldenFlameCombatBehaviors {
    /**
     * 调整硬直类型（用完记得设为 null）
     */
    public static <T extends MobPatch<?>> Consumer<T> modifyAttackStunType(@Nullable StunType stunType) {
        return (patch) -> {
            if(patch instanceof GoldenFlamePatch flamePatch){
                flamePatch.setStunTypeModify(stunType);
            }
        };
    }

    /**
     *调整基础伤害值（用完记得设为 0）
     */
    public static <T extends MobPatch<?>> Consumer<T> modifyAttackDamage(float damageModify) {
        return (patch) -> {
            if(patch instanceof GoldenFlamePatch flamePatch){
                flamePatch.setDamageModify(damageModify);
            }
        };
    }

    /**
     * 进入二阶段
     */
    public static final Consumer<HumanoidMobPatch<?>> START_BLUE = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame && goldenFlame.getHealth() <= goldenFlame.getMaxHealth()){
            goldenFlame.setIsBlue(true);
        }
    });

    /**
     * 进入反神形态
     */
    public static final Consumer<HumanoidMobPatch<?>> START_ANTI_FORM = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame && goldenFlame.getAntiFormCooldown() == 0){
            goldenFlame.startAntiForm();
        }
    });

    /**
     * 判断是否反神是否在冷却，防止不会进行别的Behavior
     */
    public static final Function<HumanoidMobPatch<?>, Boolean> CAN_STAR_ANTI_FORM = humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            return goldenFlame.getAntiFormCooldown() == 0;
        }
        return false;
    };

    /**
     * 播放粒子和音效提示
     */
    public static final Consumer<HumanoidMobPatch<?>> FLAME_TIP = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal().level() instanceof ServerLevel serverLevel){
            Vec3 pos = humanoidMobPatch.getOriginal().position();
            serverLevel.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 200, 0, 1, 0, 0.1);
        }
        humanoidMobPatch.playSound(SoundEvents.WITHER_SHOOT, 1, 1);
    });

    public static final Consumer<HumanoidMobPatch<?>> PLAY_SLAM_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1));

    /**
     * 结束蓄力状态并播放粒子音效提示
     */
    public static final Consumer<HumanoidMobPatch<?>> CLEAR_CHARGE = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            goldenFlame.resetCharging();
            if(goldenFlame.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.FLAME, goldenFlame.getX(), goldenFlame.getY(), goldenFlame.getZ(), 200, 0, 1, 0, 0.1);
            }
        }
        humanoidMobPatch.playSound(SoundEvents.WITHER_SHOOT, 1, 1);
    });

    /**
     * 判断是否在蓄力中
     */
    public static final Function<HumanoidMobPatch<?>, Boolean> IS_CHARGING = humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            return goldenFlame.isCharging();
        }
        return false;
    };

    /**
     * 是否不在蓄力中
     */
    public static final Function<HumanoidMobPatch<?>, Boolean> IS_NOT_CHARGING = humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            return !goldenFlame.isCharging();
        }
        return false;
    };

    /**
     * 反神形态
     */
    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> GOLDEN_FLAME_FIST = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO1).withinDistance(0, 2.5).withinEyeHeight().health(0.5F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO2))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.UCHIGATANA_AUTO3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.SWEEPING_EDGE).randomChance(0.6F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.LONGSWORD_DASH).withinDistance(0, 6)))
            ;

    /**
     * 大剑形态
     */
    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> GOLDEN_FLAME_GREAT_SWORD = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            // 1/3血以下开始会变身反神形态
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(9999999).cooldown(0).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENSION, 0.5F))
                                    .custom(CAN_STAR_ANTI_FORM).health(0.33F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(START_ANTI_FORM)))
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
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
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
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3_POLVORA).custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2))
//                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_3_POLVORA))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_AUTO_4))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_2_POLVORA).randomChance(0.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            //开始蓄力
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1200.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_CHARGE).custom(IS_NOT_CHARGING).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    goldenFlame.startCharging();
                                }
                            })))
            //一蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).randomChance(0.25F).withinEyeHeight().custom(humanoidMobPatch -> {
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
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).randomChance(0.25F).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 80;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_CHARGED_ATTACK_2))
//                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal().getHealth() < humanoidMobPatch.getOriginal().getMaxHealth() / 2){
                                    humanoidMobPatch.playAnimationSynchronized(WOMAnimations.SOLAR_HORNO, 0.5F);
                                } else {
                                    if(humanoidMobPatch.getOriginal().getRandom().nextBoolean()){
                                        humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_AUTO_4, 0.3F);
                                    } else {
                                        humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3F);
                                    }
                                }
                                humanoidMobPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);
                            })))
            //三蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(4, 12).randomChance(0.25F).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 1).custom(humanoidMobPatch -> {
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
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 16).withinEyeHeight().custom(humanoidMobPatch -> DOTEArchiveManager.getWorldLevel() >= 2).custom(humanoidMobPatch -> {
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
//                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(MOVE_TO_TARGET))
//                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_HORNO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.SOLAR_AUTO_2_POLVORA))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)));

}