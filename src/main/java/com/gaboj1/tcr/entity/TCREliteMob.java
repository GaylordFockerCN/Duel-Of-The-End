package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.TCRConfig;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public interface TCREliteMob {


    ServerBossEvent getBossInfo();
}
