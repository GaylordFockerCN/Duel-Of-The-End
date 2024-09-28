package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.TCRConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

/**
 * 用于在升级的时候加血量
 */
public interface LevelableEntity {
    default void levelUp(int level){
        if(level > 0 && this instanceof LivingEntity entity){
            AttributeInstance instance = entity.getAttribute(Attributes.MAX_HEALTH);
            if(instance != null){
                for(int i = 1; i <= level; i++){
                    AttributeModifier levelModifier = new AttributeModifier(UUID.fromString("d2d114cc-f51f-41ed-a05b-2233bb11451"+level),"level"+level, Math.pow(TCRConfig.MOB_MULTIPLIER_WHEN_WORLD_LEVEL_UP.get(), level), AttributeModifier.Operation.MULTIPLY_BASE);
                    if(i == level){
                        if(!instance.hasModifier(levelModifier)){
                            instance.addPermanentModifier(levelModifier);
                        }
                    } else {
                        instance.removeModifier(levelModifier);
                    }
                }
            }
            entity.setHealth(entity.getMaxHealth());
        }
    }
}