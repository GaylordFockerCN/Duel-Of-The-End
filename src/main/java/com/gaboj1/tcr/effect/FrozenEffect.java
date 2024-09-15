package com.gaboj1.tcr.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class FrozenEffect extends MobEffect {
    protected FrozenEffect() {
        super(MobEffectCategory.HARMFUL, 0X79faff);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int level) {
        if(entity.level() instanceof ServerLevel serverLevel){
            if(level == 2){
                entity.setPos(entity.position());
                if(entity instanceof Player player){
                    player.teleportTo(player.getX(), player.getY(), player.getZ());
                }
            }
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
