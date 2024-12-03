package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.efpatch.GoldenFlamePatch;
import com.p1nero.dote.entity.ai.ef.api.IModifyAttackSpeedEntity;
import com.p1nero.dote.entity.ai.ef.api.TimeStampedEvent;
import com.p1nero.dote.entity.custom.GoldenFlame;
import com.p1nero.dote.gameasset.DOTEAnimations;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import yesman.epicfight.data.conditions.entity.HealthPoint;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.p1nero.dote.entity.ai.ef.DOTECombatBehaviors.*;
import static net.minecraftforge.client.ForgeHooksClient.playSound;

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
            humanoidMobPatch.playSound(SoundEvents.WITHER_SHOOT, 0.5f, 0.5f);
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
    public static final Consumer<HumanoidMobPatch<?>> PLAY_SOLAR_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1));
    public static final Consumer<HumanoidMobPatch<?>> PLAY_TIME_TRAVEL_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(WOMSounds.TIME_TRAVEL.get(), 1f, 1f));
    public static final Consumer<HumanoidMobPatch<?>> PLAY_ENDER_TELEPORT_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(SoundEvents.ENDERMAN_TELEPORT, 1f, 1f));
    public static final Consumer<HumanoidMobPatch<?>> PLAY_HIT_BLUNT_HARD_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(EpicFightSounds.BLUNT_HIT_HARD.get(), 1f, 1f));

    public static final Consumer<HumanoidMobPatch<?>> PLAY_SOLAR_BRASERO_CREMATORIO = customAttackAnimation(WOMAnimations.SOLAR_BRASERO_CREMATORIO, 0.3F, 0.8f, null, 0, new TimeStampedEvent(0.3F, (entityPatch -> {
        LivingEntity entity = entityPatch.getOriginal();
        if(entityPatch.getOriginal().level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY() + 1.0, entity.getZ(), 10, 0.0, 0.0, 0.0, 0.1);
            serverLevel.playSound(null, entity.getX(), entity.getY() + 0.75, entity.getZ(), SoundEvents.PLAYER_HURT_ON_FIRE, SoundSource.BLOCKS, 1.0F, 0.5F);
        }
    })));

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
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.8f))
                                    .withinDistance(0, 3.0)
                                    .withinEyeHeight()
                                    .health(0.2F, HealthPoint.Comparator.LESS_RATIO))
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
            // 4/5血下——solar大招三段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(99999).cooldown(1200).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4F, 0.8f))
                                    .custom(IS_NOT_CHARGING)
                                    .health(0.8F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_OBSCURIDAD, 0.3F, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(START_BLUE))
            )
            // 3/5血下——solar大招二段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(200).cooldown(600).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4F, 0.8f))
                                    .custom(IS_NOT_CHARGING)
                                    .health(0.6F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(PLAY_SOLAR_BRASERO_CREMATORIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(PLAY_SOLAR_BRASERO_CREMATORIO))
            )
            // 2/5血下——solar大招三段（烈阳形态）
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(200).cooldown(600).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4F, 0.8f))
                                    .custom(IS_NOT_CHARGING)
                                    .health(0.4F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_INFIERNO, 0.3F, 0.8f))))
            // 1/5血下——反神形态
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(9999999).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENSION, 0.5F))
                                    .custom(IS_NOT_CHARGING)
                                    .custom(CAN_STAR_ANTI_FORM).health(0.2F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(START_ANTI_FORM)))

            //一阶段——平a
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_1, 0.3f, 0.8f))
                                    .custom(IS_NOT_CHARGING)
                                    .withinDistance(0, 4)
                                    .withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_2, 0.1f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_3, 0.0f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(150).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_2, 0.3f, 0.8f))
                                    .custom(IS_NOT_CHARGING)
                                    .withinDistance(0, 4)
                                    .withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.GREATSWORD_AUTO2, 0.3f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.2f, 0.65f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_1, 0.3f, 0.8f))
                                    .custom(IS_NOT_CHARGING)
                                    .withinDistance(0, 4)
                                    .withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_2, 0.1f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_3, 0.0f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1, 0.2f))
                                    .custom(IS_NOT_CHARGING)
                                    .withinDistance(0, 4)
                                    .withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_4, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_4_POLVORA, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
            )
            //所有蓄力连招起点——开始蓄力
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(16).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_CHARGE).custom(IS_NOT_CHARGING).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    goldenFlame.startCharging();
                                }
                            })))

            //一阶段——一蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(100).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 110;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WIND_SLASH, 0.3f, 1.2f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                    if(humanoidMobPatch.getOriginal().getRandom().nextBoolean()){
                                        humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_AUTO_4, 0.2F);
                                        if(humanoidMobPatch instanceof IModifyAttackSpeedEntity entity){
                                            entity.setAttackSpeed(0.6F);
                                        }
                                    } else {
                                        humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_BERSERK_AIRSLAM, 0.2F);
                                        if(humanoidMobPatch instanceof IModifyAttackSpeedEntity entity){
                                            entity.setAttackSpeed(0.7F);
                                        }
                                    }
                                humanoidMobPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);
                            })))

            //二阶段——平a
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(10).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1, 0.2f))
                                    .custom(IS_NOT_CHARGING)
                                    .withinDistance(0, 4)
                                    .withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))
            //二阶段——二蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(200).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).health(0.8f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 80;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_2, 0.5f)))
                            //没击中二选一，击中在下面判断
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal().getRandom().nextBoolean()){
                                    humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.2F);
                                    if(humanoidMobPatch instanceof IModifyAttackSpeedEntity entity){
                                        entity.setAttackSpeed(0.6F);
                                    }
                                } else {
                                    humanoidMobPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.2F);
                                    if(humanoidMobPatch instanceof IModifyAttackSpeedEntity entity){
                                        entity.setAttackSpeed(0.6F);
                                    }
                                    //TODO 狮子斩等腐犬补充
                                }
                                humanoidMobPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);
                            }).custom((humanoidMobPatch -> {
                                if(humanoidMobPatch instanceof GoldenFlamePatch goldenFlamePatch){
                                    return !goldenFlamePatch.isOnCharged2Hit();
                                }
                                return true;
                            }))))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(999999).cooldown(1200).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.5f, 0.6f)).custom(humanoidMobPatch -> {
                                if(humanoidMobPatch instanceof GoldenFlamePatch goldenFlame){
                                    //击中后的变招
                                    if(goldenFlame.isOnCharged2Hit()){
                                        goldenFlame.resetOnCharged2Hit();
                                        return true;
                                    }
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f)))
            )
            //三阶段——三蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(300).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 12).health(0.99f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.3f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.GREATSWORD_AUTO2, 0.3f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch instanceof GoldenFlamePatch goldenFlamePatch){
                                    goldenFlamePatch.setOnDash(true);
                                }
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.2f, 0.6f)).randomChance(0.5F))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch instanceof GoldenFlamePatch goldenFlamePatch){
                                    goldenFlamePatch.setOnDash(false);
                                }
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.9f)))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(999999).cooldown(1200).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f)).custom(humanoidMobPatch -> {
                                if(humanoidMobPatch instanceof GoldenFlamePatch goldenFlame){
                                    if(goldenFlame.isOnDash()){
                                        goldenFlame.setOnDash(false);
                                        return true;
                                    }
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.2F, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.2F, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.2F, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.2F, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.2F, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2_POLVORA, 0.2F, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(20000.0F).cooldown(3).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 12).health(0.99f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_DINAMITA, 0.3f, 0.6f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.1f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})),
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})
                            ))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, 0.1f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_2, 0.2f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_HIT_BLUNT_HARD_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_BRASERO_CREMATORIO)))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(300).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 12).health(0.99f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_DINAMITA, 0.3f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_DASH, 0.3f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_AIRSLAM, 0.1f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))

            //三阶段——平a——瞬闪1
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 12).health(0.99f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERSTEP_BACKWARD, 0.2f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERSTEP_BACKWARD, 0.2f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TIME_TRAVEL, 1f, 1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_DINAMITA, 0.3f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TIME_TRAVEL, 1f, 1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.3f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TIME_TRAVEL, 1f, 1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.RUINE_AUTO_1, 0.3f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.RUINE_AUTO_2, 0.1f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TIME_TRAVEL, 1f, 1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND)))

            //四蓄
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(2500.0F).cooldown(40).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).health(0.4f, HealthPoint.Comparator.LESS_RATIO).withinDistance(0, 16).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 20;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.2f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TIME_TRAVEL, 1f, 1f)))
                            //传送到玩家头顶6格位置
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.RUINE_REDEMPTION, -0.24f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TIME_TRAVEL, 1f, 1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_HIT_BLUNT_HARD_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_INFIERNO, 0.3f, 0.8f)))
            )
            //四阶段——平a
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1, 0.2f))
                                    .custom(IS_NOT_CHARGING)
                                    .withinDistance(0, 4)
                                    .withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))

                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.2f, 0.6f)).health(0.6f, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.0f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.0f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))

                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SLAM_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2_POLVORA, 0.2f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_SOUND))
            );
}