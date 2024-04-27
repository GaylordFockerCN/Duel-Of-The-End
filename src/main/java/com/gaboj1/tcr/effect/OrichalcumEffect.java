package com.gaboj1.tcr.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class OrichalcumEffect extends MobEffect {

    private int effectDuration;
    protected OrichalcumEffect()  {
        super(MobEffectCategory.BENEFICIAL, 0xffc33d);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(livingEntity instanceof ServerPlayer player){
            if(effectDuration == 0){
                player.setSwimming(false);
            }
            player.setSwimming(true);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.effectDuration = duration;
        return true;
    }
}
