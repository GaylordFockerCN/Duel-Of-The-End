package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.TCRConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * 用于在升级的时候加血量
 */
public interface LevelableEntity {
    default void levelUp(int level){
        if(level > 0 && this instanceof LivingEntity entity){
            AttributeInstance instance = entity.getAttribute(Attributes.MAX_HEALTH);
            if(instance != null){
                AttributeModifier levelModifier = new AttributeModifier("level"+level, TCRConfig.MOB_MULTIPLIER_WHEN_WORLD_LEVEL_UP.get(), AttributeModifier.Operation.MULTIPLY_BASE);
//                instance.removeModifiers();
                instance.addPermanentModifier(levelModifier);
            }

        }
    }
}