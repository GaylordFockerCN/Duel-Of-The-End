package com.gaboj1.tcr.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * 概率提升生命上限
 */
public class GoldenPelletHealthBoostEffect extends MobEffect {
    protected GoldenPelletHealthBoostEffect() {
        super(MobEffectCategory.HARMFUL, 0X79faff);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int level) {
        if(entity.level() instanceof ServerLevel){
            AttributeInstance instance = entity.getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier modifier = new AttributeModifier(UUID.fromString("11AEAA56-376B-4498-935B-2F7F6811451"+entity.getRandom().nextInt(10)), "from gold pellet", 2, AttributeModifier.Operation.ADDITION);
            boolean success = false;
            if(instance != null && !instance.hasModifier(modifier)){
                instance.addPermanentModifier(modifier);
                success = true;
            }
            if(entity instanceof Player player){
                if(success){
                    player.displayClientMessage(Component.literal("info.the_casket_of_reveries.add_health_success"), true);
                } else {
                    player.displayClientMessage(Component.literal("info.the_casket_of_reveries.add_health_failed"), true);
                }
            }
            entity.removeEffect(TCREffects.HEALTH_BOOST.get());
        }
    }
}
