package com.gaboj1.tcr.effect;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.armor.OrichalcumArmorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCREffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TheCasketOfReveriesMod.MOD_ID);
    public static final RegistryObject<MobEffect> ORICHALCUM = REGISTRY.register("orichalcum", OrichalcumEffect::new);
    //copy from MOVEMENT_SPEED
    public static final RegistryObject<MobEffect> FLY_SPEED = REGISTRY.register("fly_speed",() -> new FlySpeedEffect(MobEffectCategory.BENEFICIAL, 3402751).addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.50000000298023224, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> FROZEN = REGISTRY.register("frozen",() -> new FrozenEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68114514", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> THUNDER_RESISTANCE = REGISTRY.register("thunder_resistance", ()->new SimpleMobEffect(MobEffectCategory.BENEFICIAL, 0xFCFF9C));
    public static final RegistryObject<MobEffect> POISON_RESISTANCE = REGISTRY.register("poison_resistance", ()->new SimpleMobEffect(MobEffectCategory.BENEFICIAL, 0x5AA970));
    public static final RegistryObject<MobEffect> FROZEN_RESISTANCE = REGISTRY.register("frozen_resistance", ()->new SimpleMobEffect(MobEffectCategory.BENEFICIAL, 0x0385A6));
    public static final RegistryObject<MobEffect> HEALTH_BOOST = REGISTRY.register("health_boost", GoldenPelletHealthBoostEffect::new);
    public static final RegistryObject<MobEffect> NINE_TRANSFORMATIONS_RESURRECTION = REGISTRY.register("nine_transformations_resurrection", ()->new SimpleMobEffect(MobEffectCategory.BENEFICIAL, 0){
        private int color = 0;
        @Override
        public int getColor() {
            if(color > 0xFFFFFF){
                color = 0;
            }
            return color++;//五颜六色hhhh
        }
    });

    /**
     * 避雷避毒
     */
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity.hasEffect(THUNDER_RESISTANCE.get()) && event.getSource().is(DamageTypes.LIGHTNING_BOLT)){
            event.setCanceled(true);
        }
        if(entity.hasEffect(POISON_RESISTANCE.get()) && entity.hasEffect(MobEffects.POISON)){
            entity.removeEffect(MobEffects.POISON);
        }
    }

    /**
     * 九转还魂
     */
    public static void onEntityDie(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity.hasEffect(NINE_TRANSFORMATIONS_RESURRECTION.get())){
            entity.setHealth(entity.getMaxHealth() / 2);
            entity.removeEffect(NINE_TRANSFORMATIONS_RESURRECTION.get());
            entity.level().playSound(null , entity.getX(),entity.getY(),entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS,1,1);
            if(entity instanceof Player player){
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.resurrection"), true);
            }
            event.setCanceled(true);
        }
    }

    /**
     * 解冻
     */
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity.hasEffect(FROZEN_RESISTANCE.get())){
            if(livingEntity.hasEffect(TCREffects.FROZEN.get())){
                livingEntity.removeEffect(TCREffects.FROZEN.get());
            }
        }

    }

}
