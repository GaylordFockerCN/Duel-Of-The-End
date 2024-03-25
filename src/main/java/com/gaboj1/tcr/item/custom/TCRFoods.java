package com.gaboj1.tcr.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class TCRFoods {
    public static final FoodProperties BLUE_BANANA = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).build();
    public static final FoodProperties DREAM_DA = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.CONFUSION,200),0.1f).build();
}
