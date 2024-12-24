package com.p1nero.dote.effect;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DOTEEffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DuelOfTheEndMod.MOD_ID);
    public static final RegistryObject<MobEffect> ARMOR_DEBUFF = REGISTRY.register("armor_debuff",() -> new SimpleMobEffect(MobEffectCategory.HARMFUL, 0X6c6a5c)
            .addAttributeModifier(Attributes.ARMOR, "22AEBB56-113B-4498-935B-2F7F68114514", -0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ARMOR, "22AEBB56-514B-4498-935B-2F7F68114514", -0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL));

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
