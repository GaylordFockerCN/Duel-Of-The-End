package com.gaboj1.tcr.effect;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCREffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DuelOfTheEndMod.MOD_ID);

    /**
     * 避雷
     */
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
    }

    /**
     * 九转还魂
     */
    public static void onEntityDie(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
    }

    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
    }

}
