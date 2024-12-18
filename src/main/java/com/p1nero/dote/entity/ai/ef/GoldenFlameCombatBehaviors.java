package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.capability.efpatch.GoldenFlamePatch;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.entity.ai.ef.api.IModifyAttackSpeedEntityPatch;
import com.p1nero.dote.entity.ai.ef.api.TimeStampedEvent;
import com.p1nero.dote.entity.custom.GoldenFlame;
import com.p1nero.dote.gameasset.DOTEAnimations;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.clientbound.SyncPos0Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.particle.WOMParticles;
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
     * 开始隐身
     */
    public static final Consumer<HumanoidMobPatch<?>> SET_HIDE = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            goldenFlame.setShouldRender(false);
            if(goldenFlame.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(WOMParticles.TELEPORT.get(), humanoidMobPatch.getOriginal().position().x, humanoidMobPatch.getOriginal().position().y + 1.0, humanoidMobPatch.getOriginal().position().z, 1, 0.0, 0.0, 0.0, 0.0);
                humanoidMobPatch.playSound(SoundEvents.ENDERMAN_TELEPORT, 1, 1);
            }
        }
    });

    /**
     * 结束隐身
     */
    public static final Consumer<HumanoidMobPatch<?>> SET_NOT_HIDE = (humanoidMobPatch -> {
        if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
            goldenFlame.setShouldRender(true);
            if(goldenFlame.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(WOMParticles.TELEPORT_REVERSE.get(), humanoidMobPatch.getOriginal().position().x, humanoidMobPatch.getOriginal().position().y + 1.0, humanoidMobPatch.getOriginal().position().z, 1, 0.0, 0.0, 0.0, 0.0);
            }
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
    public static final Consumer<HumanoidMobPatch<?>> PLAY_HIT_BLUNT_HARD_SOUND = (humanoidMobPatch -> humanoidMobPatch.playSound(EpicFightSounds.BLUNT_HIT_HARD.get(), 1f, 1f));

    /**
     * 传送到玩家前面
     * @param invert invert为true则传背后
     */
    public static <T extends MobPatch<?>> Consumer<T> teleportToFront(boolean invert, int minDis, int maxDis) {
        return (humanoidMobPatch -> {
            if(humanoidMobPatch.getTarget() != null){
                LivingEntity target = humanoidMobPatch.getTarget();
                Vec3 targetPos = target.position();
                Vec3 view = target.getViewVector(1.0F);
                if(invert){
                    view = view.scale(-1);
                }
                Vec3 dir = new Vec3(view.x, 0, view.z);
                Vec3 toTeleport;
                if(minDis == maxDis){
                    toTeleport = targetPos.add(dir.normalize().scale(minDis));//传送范围
                } else {
                    toTeleport = targetPos.add(dir.normalize().scale(target.getRandom().nextInt(minDis, maxDis)));//传送范围
                }
                humanoidMobPatch.getOriginal().teleportTo(toTeleport.x, toTeleport.y, toTeleport.z);
                humanoidMobPatch.getOriginal().getLookControl().setLookAt(humanoidMobPatch.getTarget());
            }
        });
    }


    /**
     * 传送到玩家头上
     * @param invert invert为true则传底下
     */
    public static <T extends MobPatch<?>> Consumer<T> teleportToUp(boolean invert, int minDis, int maxDis) {
        return (humanoidMobPatch -> {
            if(humanoidMobPatch.getTarget() != null){
                LivingEntity target = humanoidMobPatch.getTarget();
                Vec3 targetPos = target.position();
                Vec3 dir = new Vec3(0, invert ? -1 : 1, 0);
                Vec3 toTeleport;
                if(minDis == maxDis){
                    toTeleport = targetPos.add(dir.normalize().scale(minDis));//传送范围
                } else {
                    toTeleport = targetPos.add(dir.normalize().scale(target.getRandom().nextInt(minDis, maxDis)));//传送范围
                }
                humanoidMobPatch.getOriginal().teleportTo(toTeleport.x, toTeleport.y, toTeleport.z);
                humanoidMobPatch.getOriginal().getLookControl().setLookAt(humanoidMobPatch.getTarget());
            }
        });
    }



    /**
     * 追击玩家
     * @param dis 追击到距离玩家多远的距离
     */
    public static <T extends MobPatch<?>> Consumer<T> chaseTarget(float dis) {
        return (humanoidMobPatch -> {
            if(humanoidMobPatch.getTarget() != null){
                LivingEntity target = humanoidMobPatch.getTarget();
                LivingEntity self = humanoidMobPatch.getOriginal();
                Vec3 toTeleport = target.position().add(self.position().subtract(target.position()).normalize().scale(dis));
                humanoidMobPatch.getOriginal().moveTo(toTeleport.x, toTeleport.y, toTeleport.z);
                humanoidMobPatch.getOriginal().getLookControl().setLookAt(humanoidMobPatch.getTarget());
            }
        });
    }

    /**
     * 随机传送到玩家边上
     */
    public static final Consumer<HumanoidMobPatch<?>> RANDOM_TELEPORT = (humanoidMobPatch -> {
        if(humanoidMobPatch.getTarget() != null){
            LivingEntity target = humanoidMobPatch.getTarget();
            Vec3 targetPos = target.position();
            double angle = target.getRandom().nextDouble() * 2 * Math.PI;
            double dis = 5.0;
            double newX = targetPos.x + dis * Math.cos(angle);
            double newZ = targetPos.z + dis * Math.sin(angle);
            Vec3 toTeleport = new Vec3(newX, targetPos.y, newZ);
            humanoidMobPatch.getOriginal().xo = toTeleport.x;
            humanoidMobPatch.getOriginal().xOld = toTeleport.x;
            humanoidMobPatch.getOriginal().yo = toTeleport.y;
            humanoidMobPatch.getOriginal().yOld = toTeleport.y;
            humanoidMobPatch.getOriginal().zo = toTeleport.z;
            humanoidMobPatch.getOriginal().zOld = toTeleport.z;
//            if(humanoidMobPatch.getOriginal() instanceof GoldenFlame flame){
//                flame.setShouldRender(false);
//            }
            PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncPos0Packet(humanoidMobPatch.getOriginal().getId(), toTeleport.x, toTeleport.y, toTeleport.z));
//            humanoidMobPatch.getOriginal().randomTeleport(toTeleport.x, toTeleport.y, toTeleport.z, true);
            humanoidMobPatch.getOriginal().setPos(toTeleport);
            humanoidMobPatch.getOriginal().getLookControl().setLookAt(humanoidMobPatch.getTarget());
//            if(humanoidMobPatch.getOriginal() instanceof GoldenFlame flame){
//                flame.setShouldRender(true);
//            }
//            if(humanoidMobPatch.getOriginal().level() instanceof ServerLevel serverLevel){
//                serverLevel.sendParticles(WOMParticles.TELEPORT.get(), humanoidMobPatch.getOriginal().position().x, humanoidMobPatch.getOriginal().position().y + 1.0, humanoidMobPatch.getOriginal().position().z, 1, 0.0, 0.0, 0.0, 0.0);
//            }

        }
    });

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
            //常规出拳2种4套
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10F).cooldown(600).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.5f))
                                    .withinDistance(0, 3.0).withinEyeHeight()
                                    .health(0.99F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, 0.1f, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_4, 0.2f, 0.6f))))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(0.1F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.5f))
                                    .withinDistance(0, 3.0).withinEyeHeight()
                                    .health(0.99F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, 0.1f, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_4, 0.2f, 0.6f))))

            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10F).cooldown(600).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.5f))
                                    .withinDistance(0, 3.0).withinEyeHeight()
                                    .health(0.99F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, 0.1f, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_DEATHFALL, 0.3f, 0.6f))))
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(0.1F).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.5f))
                                    .withinDistance(0, 3.0).withinEyeHeight()
                                    .health(0.99F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, 0.1f, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_DEATHFALL, 0.3f, 0.6f))))
            //黄火拳+蓝火拳
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(6F).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.5f))
                                    .withinDistance(0, 3.0).withinEyeHeight()
                                    .health(0.99F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, 0.1f, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1_POLVORA, 0.3f, 0.8f,  StunType.LONG, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.7f, 1,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1))),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_AUTO_3_POLVORA.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.15f, 1,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1))),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_AUTO_3_POLVORA.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.15f, 1,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1))),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_AUTO_3_POLVORA.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_DEATHFALL, 0.3f, 0.6f, StunType.LONG, 1.3f,
                                    new TimeStampedEvent(0.35f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1f, 1f);})))))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(6F).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, 0.3f, 0.5f))
                                    .withinDistance(0, 3.0)
                                    .withinEyeHeight()
                                    .health(0.99F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, 0.1f, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_IMPACTO, 0.2f, 0.5f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {
                                        livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);
                                        livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);
                                    })))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.4f, 0.9f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1f, 1f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0f, 0.9f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0f, 0.9f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.KATANA_FATAL_DRAW_DASH, 0.5f, 0.8f, null, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.KATANA_FATAL_DRAW_DASH.getTotalTime(), 0))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENDED_BLINK, 0f, 0.8f, StunType.HOLD, 1.3f,
                                    new TimeStampedEvent(0.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.ANTITHEUS_ASCENDED_BLINK.getTotalTime(), 0.1f))))))
            )


    ;

    /**
     * 大剑形态
     */
    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> GOLDEN_FLAME_GREAT_SWORD = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            // 4/5血下——solar大招三段
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(99999).cooldown(1200).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4F, 0.8f, StunType.HOLD, 1f,
                                            new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.8f, 0.8f);}))))
                                    .custom(IS_NOT_CHARGING)
                                    .health(0.8F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_OBSCURIDAD, 0.3F, 0.8f, StunType.LONG, 1f,
                                            new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.5f, 0.5f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(START_BLUE))
            )
            // 1/5血下——反神形态
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(9999999).cooldown(60).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(customAttackAnimation(WOMAnimations.ANTITHEUS_ASCENSION, 0.5f, 0.8f, null, 1f,
                                            new TimeStampedEvent(0.4f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.WITHER_SHOOT, 0.5f, 0.5f))),
                                            new TimeStampedEvent(0.17f, (livingEntityPatch -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 9))))))
                                    .custom(IS_NOT_CHARGING)
                                    .custom(CAN_STAR_ANTI_FORM).health(0.2F, HealthPoint.Comparator.LESS_RATIO))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder()
                                    .behavior(START_ANTI_FORM)))

            //一阶段——平a
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1F).cooldown(80).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_1, 0.3f, 0.8f))
                                    .custom(IS_NOT_CHARGING).withinDistance(0, 3).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_2, 0.1f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_3, 0.0f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f, StunType.KNOCKDOWN, 1f,
                                    new TimeStampedEvent(0.15f, (livingEntityPatch -> {livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);})),
                                    new TimeStampedEvent(1.1f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1F).cooldown(80).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_1, 0.3f, 0.8f))
                                    .custom(IS_NOT_CHARGING).withinDistance(0, 3).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_2, 0.1f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_3, 0.0f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.1f, 0.7f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.5f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(1F).cooldown(80).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1, 0.2f))
                                    .custom(IS_NOT_CHARGING).withinDistance(0, 3).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_4, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_4_POLVORA, 0.2f, 0.9f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            //二阶段——平a
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(2F).cooldown(160).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1, 0.2f))
                                    .custom(IS_NOT_CHARGING).withinDistance(0, 3).health(0.8f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f, null, 1f,
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f,  StunType.KNOCKDOWN, 1f,
                                    new TimeStampedEvent(0.15f, (livingEntityPatch -> {livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);})),
                                    new TimeStampedEvent(1.1f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            //三阶段——平a
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(2.4F).cooldown(400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1, 0.2f))
                                    .custom(IS_NOT_CHARGING).withinDistance(0, 3).health(0.6f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.2f, 0.9f, null, 1f,
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_1_POLVORA, 0.3f, 0.8f,  StunType.LONG, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.7f, 1,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1))),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_AUTO_3_POLVORA.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.15f, 1,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1))),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_AUTO_3_POLVORA.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3_POLVORA, 0.15f, 1,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1))),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_AUTO_3_POLVORA.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4F, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.8f, 0.8f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_OBSCURIDAD, 0.3F, 0.8f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.5f, 0.5f);})))))
            )
            //所有蓄力连招起点——开始蓄力
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(3F).cooldown(120).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.BIPED_STEP_BACKWARD, 0.1f)).withinDistance(0, 3))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(WOMAnimations.TORMENT_CHARGE).custom(IS_NOT_CHARGING).withinEyeHeight())
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    goldenFlame.startCharging();
                                }
                            })))

            //一阶段-一蓄-1
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10F).cooldown(80).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 110;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WIND_SLASH, 0.3f, 1.2f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.1f, 0.7f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.5f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1))))))
            )
            //一阶段-一蓄-2
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(10F).cooldown(80).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 4).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 110;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.WIND_SLASH, 0.3f, 1.2f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_AIRSLAM, 0.2f, 0.7f, StunType.KNOCKDOWN, 1.3f,
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            //二阶段-二蓄-1
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(12F).cooldown(160).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 6).health(0.8f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 80;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_2, 0.4f, 0.8f, StunType.LONG, 1f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior((humanoidMobPatch)->{
                                if(humanoidMobPatch instanceof GoldenFlamePatch patch){
                                    patch.resetOnCharged2Hit();
                                }
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.1f, 0.7f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.5f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f))
                                    .custom(humanoidMobPatch -> {
                                        if(humanoidMobPatch instanceof GoldenFlamePatch goldenFlame){
                                            //击中后的变招
                                            if(goldenFlame.isOnTormentAuto4Hit()){
                                                goldenFlame.resetOnCharged2Hit();
                                                return true;
                                            }
                                        }
                                        return false;
                                    }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f,  StunType.KNOCKDOWN, 1f,
                                    new TimeStampedEvent(0.15f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f))),
                                    new TimeStampedEvent(1.1f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1))))))
            )
            //二阶段-二蓄-2
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(12F).cooldown(240).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 6).health(0.8f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if(humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame){
                                    return goldenFlame.getChargingTimer() <= 80;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_2, 0.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.1f, 0.7f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.5f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LION_CLAW, 0.2F, 1, StunType.KNOCKDOWN, 1,
                                    new TimeStampedEvent(1.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            //二阶段——瞬闪1
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100F).cooldown(700).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERSTEP_BACKWARD, 0.1f))
                                    .health(0.8f, HealthPoint.Comparator.LESS_RATIO)
                                    .withinDistance(0, 3).withinEyeHeight()
                                    .custom(IS_NOT_CHARGING).custom(attackLevelCheck(1, 2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.AGONY_RISING_EAGLE, 0.3f, 0.4f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.17f, (livingEntityPatch -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30)))),
                                    new TimeStampedEvent(0.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.AGONY_RISING_EAGLE.getTotalTime(), 0.17f))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToUp(false, 6, 6)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_HORNO, 0.15f, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.TOTEM_USE, 1, 1)))
                            )))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_TELEPORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(false, 7, 7)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_DINAMITA, 0.2f, 0.7f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.1F, (livingEntityPatch ->{livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1)))
                            )))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_TELEPORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(true, 1, 2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.4F, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.8f, 0.8f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_OBSCURIDAD, 0.3F, 0.8f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.5f, 0.5f);})))))
            )
            //三阶段——三蓄-1
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(15F).cooldown(360).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 12).health(0.6f, HealthPoint.Comparator.LESS_RATIO)
                                    .withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.1f, 0.7f,  StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.GREATSWORD_AUTO2, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_AUTO_4, 0.1f, 0.7f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.5f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.2f, 0.65f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(1.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.TORMENT_CHARGED_ATTACK_1.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.LION_CLAW, 0.2F, 1, StunType.KNOCKDOWN, 1,
                                    new TimeStampedEvent(1.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            //三阶段——三蓄-2
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(15).cooldown(360).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE).withinDistance(0, 12).health(0.6f, HealthPoint.Comparator.LESS_RATIO).withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.1f, 0.7f,  StunType.LONG, 1f,
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_AUTO_2, 0.2f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_AUTO_1, 0f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(3.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_AUTO_2, 0f, 0.8f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(2.5f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f, null, 1f,
                                    new TimeStampedEvent(0.15f, (livingEntityPatch -> {livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);})),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.TORMENT_CHARGED_ATTACK_3.getTotalTime());})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_AIRSLAM, 0.2f, 0.7f, StunType.KNOCKDOWN, 1.3f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.ENTITY_MOVE.get(), 0.8f, 0.8f);})),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);}))
                            )))
            )
            //三阶段-读攻击1
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(400F).cooldown(1200).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.SSTEP_BACKWARD, 0.1f, 1, null, 1,
                                        new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(DOTESounds.DODGE.get(), 1f, 1f);}))))
                                    .health(0.6f, HealthPoint.Comparator.LESS_RATIO)
                                    .withinDistance(0, 3).withinEyeHeight()
                                    .custom(IS_NOT_CHARGING).custom(attackLevelCheck(1, 2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.AGONY_RISING_EAGLE, 0.3f, 0.4f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.15f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 1, 1))),
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20)))),
                                    new TimeStampedEvent(0.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.AGONY_RISING_EAGLE.getTotalTime(), 0.2f))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0, 1, null, 1f,
                                    new TimeStampedEvent(0.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.TORMENT_CHARGED_ATTACK_1.getTotalTime(), 0.7f))),
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.TORMENT_CHARGED_ATTACK_1.getTotalTime())))
                            )))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.WRATHFUL_LIGHTING, 0.1f, 1, null, 1f,
                                    new TimeStampedEvent(0.1f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(Animations.WRATHFUL_LIGHTING.getTotalTime()))))))

                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_HORNO, 0.15f, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_HORNO.getTotalTime(),0.2f))),
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.TOTEM_USE, 1, 1))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, 0.2f, 0.6f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_IMPACTO, 0.2f, 0.5f, null, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {
                                        livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);
                                        livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);
                                    })))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.4f, 0.9f, null, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1f, 1f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0f, 0.9f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0f, 0.9f, StunType.LONG, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO, 0.2f, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 0.8f, 0.8f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_SOLAR_BRASERO_CREMATORIO))
            )
            //三阶段——瞬闪2
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(200F).cooldown(800).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERSTEP_BACKWARD, 0.1f))
                                    .health(0.6f, HealthPoint.Comparator.LESS_RATIO)
                                    .withinDistance(0, 3).withinEyeHeight()
                                    .custom(IS_NOT_CHARGING).custom(attackLevelCheck(1, 2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.AGONY_RISING_EAGLE, 0.3f, 0.4f, StunType.SHORT, 1f,
                                    new TimeStampedEvent(0.17f, (livingEntityPatch -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30)))),
                                    new TimeStampedEvent(0.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.AGONY_RISING_EAGLE.getTotalTime(), 0.17f))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToUp(false, 6, 6)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_HORNO, 0.15f, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.TOTEM_USE, 1, 1)))
                            )))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_TELEPORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(false, 7, 7)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_DINAMITA, 0.2f, 0.7f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.1F, (livingEntityPatch ->{livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);})),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1)))
                            )))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_TELEPORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(false, 1, 2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.1f, 0.9f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_3, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_TELEPORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(false, 6, 6)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(ROTATE_TO_TARGET))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.1f, 0.7f,  StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, 0.2f, 0.7f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.3f, 0.65f, StunType.SHORT, 1.3f,
                                    new TimeStampedEvent(1.15f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            )
            //四阶段-瞬闪3
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(600F).cooldown(1400).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(DOTEAnimations.SSTEP_BACKWARD, 0.1f))
                                .health(0.4f, HealthPoint.Comparator.LESS_RATIO)
                                .withinDistance(0, 3).withinEyeHeight()
                                .custom(IS_NOT_CHARGING).custom(attackLevelCheck(1, 2)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.ENDERSTEP_RIGHT, 0.2f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(RANDOM_TELEPORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(false, 1, 1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))

                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_2, 0.1f, 0.9f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_4, 0.2f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_AUTO_4_POLVORA, 0.2f, 0.9f, StunType.FALL, 1f,
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1))))))

                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToFront(true, 3, 3)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_IMPACTO, 0.2f, 0.5f, null, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> {
                                        livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);
                                        livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1, 1);
                                    })))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(chaseTarget(1)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0.4f, 0.9f, null, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1f, 1f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0, 0.9f, null, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1f, 1f);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, 0, 0.9f, null, 1f,
                                    new TimeStampedEvent(0.2f, (livingEntityPatch -> {livingEntityPatch.playSound(WOMSounds.SOLAR_HIT.get(), 1f, 1f);})))))

                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.BIPED_HIT_SHORT))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.BIPED_HIT_SHORT, 0.2f, 1, null, 1,
                                    new TimeStampedEvent(0, (livingEntityPatch -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30)))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(PLAY_TIME_TRAVEL_SOUND))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(Animations.ENDERMAN_TP_EMERGENCE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(teleportToUp(false, 6, 6)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(SET_NOT_HIDE))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_HORNO, 0.15f, 0.8f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.4f, (livingEntityPatch -> livingEntityPatch.playSound(SoundEvents.TOTEM_USE, 1, 1))),
                                    new TimeStampedEvent(0.7f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.SOLAR_HORNO.getTotalTime())))
                            )))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.SOLAR_BRASERO_INFIERNO, 0.4f, 0.8f, StunType.HOLD, 2f)))
            )
            //四阶段-四蓄力
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(20F).cooldown(480).canBeInterrupted(false).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(CLEAR_CHARGE)
                                    .withinDistance(0, 12).health(0.4f, HealthPoint.Comparator.LESS_RATIO)
                                    .withinEyeHeight().custom(humanoidMobPatch -> {
                                if (humanoidMobPatch.getOriginal() instanceof GoldenFlame goldenFlame) {
                                    return goldenFlame.getChargingTimer() <= 50;
                                }
                                return false;
                            }))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_DASH, 0.1f, 0.7f,  StunType.LONG, 1f,
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(Animations.GREATSWORD_AUTO2, 0.3f, 0.9f)))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_BERSERK_DASH, 0.4f, 0.6f, StunType.HOLD, 1f,
                                    new TimeStampedEvent(0.3f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})),
                                    new TimeStampedEvent(0.6f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})),
                                    new TimeStampedEvent(1.15f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.2f, 0.65f, StunType.LONG, 1f,
                                    new TimeStampedEvent(1.0f, (livingEntityPatch -> livingEntityPatch.getAnimator().getPlayerFor(null).setElapsedTime(WOMAnimations.TORMENT_CHARGED_ATTACK_1.getTotalTime()))))))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().behavior(customAttackAnimation(WOMAnimations.TORMENT_CHARGED_ATTACK_3, 0.3f, 0.6f, StunType.KNOCKDOWN, 1f,
                                    new TimeStampedEvent(0.15f, (livingEntityPatch -> {livingEntityPatch.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);})),
                                    new TimeStampedEvent(1.1f, (livingEntityPatch -> {livingEntityPatch.playSound(EpicFightSounds.GROUND_SLAM.get(), 1, 1);})))))
            );
}