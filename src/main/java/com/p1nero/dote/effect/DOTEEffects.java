package com.p1nero.dote.effect;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DOTEEffects {

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
