package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.DOTEConfig;
import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

/**
 * 用于在升级的时候加血量
 */
public interface ModifyAttackSpeedEntity {
    float getAttackSpeed();
}