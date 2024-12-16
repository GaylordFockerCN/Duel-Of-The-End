package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.entity.ai.ef.api.*;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.clientbound.SyncPos0Packet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 提供一些通用预设
 */
public class DOTECombatBehaviors {

    /**
     * 播放动画，带ConvertTime也带变速
     */
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(StaticAnimation animation, float convertTime, float attackSpeed, @Nullable StunType stunType, float damage, TimeStampedEvent... events) {
        return (patch) -> {
            if (patch instanceof IModifyAttackSpeedEntityPatch entity) {
                entity.setAttackSpeed(attackSpeed);
            }
            if (patch instanceof IModifyStunTypeEntityPatch entity) {
                entity.setStunType(stunType);
            }
            if (patch instanceof IModifyAttackDamageEntityPatch entity) {
                entity.setNewDamage(damage);
            }
            if (patch instanceof ITimeEventListEntityPatch entity) {
                entity.clearEvents();
                for (TimeStampedEvent event : events) {
                    event.resetExecuted();
                    entity.addEvent(event);
                }
            }
            patch.playAnimationSynchronized(animation, convertTime);
        };
    }

    /**
     * 播放动画，带ConvertTime也带变速
     */
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(StaticAnimation animation, float convertTime, float attackSpeed, @Nullable StunType stunType, float damage) {
        return (patch) -> {
            if (patch instanceof IModifyAttackSpeedEntityPatch entity) {
                entity.setAttackSpeed(attackSpeed);
            }
            if (patch instanceof IModifyStunTypeEntityPatch entity) {
                entity.setStunType(stunType);
            }
            if (patch instanceof IModifyAttackDamageEntityPatch entity) {
                entity.setNewDamage(damage);
            }
            patch.playAnimationSynchronized(animation, convertTime);
        };
    }

    /**
     * 播放动画，带ConvertTime也带变速
     */
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(StaticAnimation animation, float convertTime, float attackSpeed, @Nullable StunType stunType) {
        return customAttackAnimation(animation, convertTime, attackSpeed, stunType, 0);
    }

    /**
     * 播放动画，带ConvertTime也带变速
     */
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(StaticAnimation animation, float convertTime, float attackSpeed) {
        return customAttackAnimation(animation, convertTime, attackSpeed, null, 0);
    }

    /**
     * 播放动画，带ConvertTime不带变速
     */
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(StaticAnimation animation, float convertTime) {
        return customAttackAnimation(animation, convertTime, 1.0F);
    }

    /**
     * 转向目标
     */
    public static final Consumer<HumanoidMobPatch<?>> ROTATE_TO_TARGET = (humanoidMobPatch -> {
        if (humanoidMobPatch.getTarget() != null) {
            humanoidMobPatch.rotateTo(humanoidMobPatch.getTarget(), 30F, true);
        }
    });
    /**
     * 瞬移到目标边上（可能会到身后）
     */
    public static final Consumer<HumanoidMobPatch<?>> MOVE_TO_TARGET = (humanoidMobPatch -> {
        if (humanoidMobPatch.getTarget() != null) {
            Vec3 view = humanoidMobPatch.getTarget().getViewVector(1.0F);
            Vec3 dir = new Vec3(view.x, 0, view.z);
            humanoidMobPatch.getOriginal().moveTo(humanoidMobPatch.getTarget().position().add(dir.normalize().scale(2)));
            humanoidMobPatch.rotateTo(humanoidMobPatch.getTarget(), 30F, true);
        }
    });

    /**
     * 随机方向跨步
     */
    public static final Consumer<HumanoidMobPatch<?>> RANDOM_ENDER_STEP = (humanoidMobPatch -> {
        List<StaticAnimation> steps = new ArrayList<>();
        steps.add(WOMAnimations.ENDERSTEP_BACKWARD);
        steps.add(WOMAnimations.ENDERSTEP_FORWARD);
        steps.add(WOMAnimations.ENDERSTEP_LEFT);
        steps.add(WOMAnimations.ENDERSTEP_RIGHT);
        humanoidMobPatch.playAnimationSynchronized(steps.get(new Random().nextInt(steps.size())), 0.0F);
    });


    /**
     * 传送到玩家前面
     *
     * @param invert invert为true则传背后
     */
    public static <T extends MobPatch<?>> Consumer<T> teleportToFront(boolean invert, int minDis, int maxDis) {
        return (humanoidMobPatch -> {
            if (humanoidMobPatch.getTarget() != null) {
                LivingEntity target = humanoidMobPatch.getTarget();
                Vec3 targetPos = target.position();
                Vec3 view = target.getViewVector(1.0F);
                if (invert) {
                    view = view.scale(-1);
                }
                Vec3 dir = new Vec3(view.x, 0, view.z);
                Vec3 toTeleport;
                if (minDis == maxDis) {
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
     *
     * @param invert invert为true则传底下
     */
    public static <T extends MobPatch<?>> Consumer<T> teleportToUp(boolean invert, int minDis, int maxDis) {
        return (humanoidMobPatch -> {
            if (humanoidMobPatch.getTarget() != null) {
                LivingEntity target = humanoidMobPatch.getTarget();
                Vec3 targetPos = target.position();
                Vec3 dir = new Vec3(0, invert ? -1 : 1, 0);
                Vec3 toTeleport;
                if (minDis == maxDis) {
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
     *
     * @param dis 追击到距离玩家多远的距离
     */
    public static <T extends MobPatch<?>> Consumer<T> chaseTarget(float dis) {
        return (humanoidMobPatch -> {
            if (humanoidMobPatch.getTarget() != null) {
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
     *
     * @param dis 传送点到玩家的距离
     */
    public static Consumer<HumanoidMobPatch<?>> randomTeleport(int dis) {
        return (humanoidMobPatch) -> {
            if (humanoidMobPatch.getTarget() != null) {
                LivingEntity target = humanoidMobPatch.getTarget();
                Vec3 targetPos = target.position();
                double angle = target.getRandom().nextDouble() * 2 * Math.PI;
                double newX = targetPos.x + dis * Math.cos(angle);
                double newZ = targetPos.z + dis * Math.sin(angle);
                Vec3 toTeleport = new Vec3(newX, targetPos.y, newZ);
                humanoidMobPatch.getOriginal().xo = toTeleport.x;
                humanoidMobPatch.getOriginal().xOld = toTeleport.x;
                humanoidMobPatch.getOriginal().yo = toTeleport.y;
                humanoidMobPatch.getOriginal().yOld = toTeleport.y;
                humanoidMobPatch.getOriginal().zo = toTeleport.z;
                humanoidMobPatch.getOriginal().zOld = toTeleport.z;
                PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncPos0Packet(humanoidMobPatch.getOriginal().getId(), toTeleport.x, toTeleport.y, toTeleport.z));
                humanoidMobPatch.getOriginal().setPos(toTeleport);
                humanoidMobPatch.getOriginal().getLookControl().setLookAt(humanoidMobPatch.getTarget());
            }
        };
    }

    public static Function<HumanoidMobPatch<?>, Boolean> attackLevelCheck(int min, int max) {
        return (patch) -> {
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(patch.getTarget(), LivingEntityPatch.class);
            if (targetPatch == null) {
                return false;
            } else {
                int level = targetPatch.getEntityState().getLevel();
                return min <= level && level <= max;
            }
        };
    }

    public static Function<HumanoidMobPatch<?>, Boolean> targetUsingItem(boolean isEdible) {
        return humanoidMobPatch -> {
            LivingEntity target = humanoidMobPatch.getTarget();
            if (!target.isUsingItem()) {
                return false;
            } else {
                ItemStack item = target.getUseItem();
                if (isEdible) {
                    return item.getItem() instanceof PotionItem || item.getItem().isEdible();
                } else {
                    return !(item.getItem() instanceof PotionItem) && !item.getItem().isEdible();
                }
            }
        };
    }

}
