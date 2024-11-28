package com.p1nero.dote.entity.ai.ef;

import com.p1nero.dote.entity.IModifyAttackSpeedEntity;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * 提供一些通用预设
 */
public class DOTECombatBehaviors {

    /**
     * 播放动画，带ConvertTime也带变速
     */
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(StaticAnimation animation, float convertTime, float attackSpeed) {
        return (patch) -> {
            if(patch.getOriginal() instanceof IModifyAttackSpeedEntity entity){
                entity.setAttackSpeed(attackSpeed);
            }
            patch.playAnimationSynchronized(animation, convertTime);
        };
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
        if(humanoidMobPatch.getTarget() != null){
            humanoidMobPatch.rotateTo(humanoidMobPatch.getTarget(), 30F, true);
        }
    });

    /**
     * 瞬移到目标边上（可能会到身后）
     */
    public static final Consumer<HumanoidMobPatch<?>> MOVE_TO_TARGET = (humanoidMobPatch -> {
        if(humanoidMobPatch.getTarget() != null){
            humanoidMobPatch.getOriginal().moveTo(humanoidMobPatch.getTarget().position());
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

}
