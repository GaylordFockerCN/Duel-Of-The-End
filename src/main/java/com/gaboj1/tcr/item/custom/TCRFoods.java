package com.gaboj1.tcr.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;

public class TCRFoods {
    public static final FoodProperties COMMON = new FoodProperties.Builder().nutrition(4).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.REGENERATION,200),0.5f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200),0.5f).build();
    public static final FoodProperties DRINK = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200),0.5f).build();
    public static final FoodProperties DREAM_DA = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.CONFUSION,200),0.5f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200),0.5f).effect(()->new MobEffectInstance(MobEffects.DAMAGE_BOOST,200),0.5f).build();
    public static final FoodProperties EDEN_APPLE = new FoodProperties.Builder().nutrition(4).alwaysEat().saturationMod(1.2f).effect(()->new MobEffectInstance(MobEffects.GLOWING,200),1.0f).effect(()->new MobEffectInstance(MobEffects.REGENERATION,200),1.0f).effect(()->new MobEffectInstance(MobEffects.HEALTH_BOOST,200),1.0f).build();
    public static final FoodProperties ELDER_CAKE = new FoodProperties.Builder().nutrition(20).alwaysEat().saturationMod(1.2f).build();

}