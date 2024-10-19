package com.gaboj1.tcr.item;

import com.gaboj1.tcr.effect.TCREffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;

public class TCRFoods {
    public static final FoodProperties COMMON = new FoodProperties.Builder().nutrition(4).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.REGENERATION,800),0.5f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,800),0.5f).build();
    public static final FoodProperties DRINK = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,800),0.5f).build();
    public static final FoodProperties DREAM_DA = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.CONFUSION,800),0.5f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,800),0.5f).effect(()->new MobEffectInstance(MobEffects.DAMAGE_BOOST,800),0.5f).build();
    public static final FoodProperties GOLDEN_WIND_AND_DEW = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.GLOWING,600),1.0f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600, 1),1.0F).effect(()->new MobEffectInstance(MobEffects.DAMAGE_BOOST,600, 3),1.0F).effect(()->new MobEffectInstance(MobEffects.HEALTH_BOOST,600, 8),1.0F).build();
    public static final FoodProperties EDEN_APPLE = new FoodProperties.Builder().nutrition(4).alwaysEat().saturationMod(1.2f).effect(()->new MobEffectInstance(MobEffects.GLOWING,800),1.0f).effect(()->new MobEffectInstance(MobEffects.REGENERATION,800),1.0f).effect(()->new MobEffectInstance(MobEffects.HEALTH_BOOST,800),1.0f).build();
    public static final FoodProperties ELDER_CAKE = new FoodProperties.Builder().nutrition(20).alwaysEat().saturationMod(1.2f).build();
    public static final FoodProperties CATNIP = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.ORICHALCUM.get(),800),0.5f).build();
    public static final FoodProperties BLUE_MUSHROOM = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.POISON_RESISTANCE.get(),100),0.5f).build();
    public static final FoodProperties JELLY_CAT = new FoodProperties.Builder().nutrition(2).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.JUMP,800),0.5f).build();
    //光明丹（夜视）
    public static final FoodProperties NIGHT_VISION_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.NIGHT_VISION, 2000, 2), 1.0f).build();
    //登仙丹（提升移速和获得缓降）
    public static final FoodProperties SPEED_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 2), 1.0f).effect(()->new MobEffectInstance(MobEffects.SLOW_FALLING, 2000, 3), 1.0f).build();
    //幸运丹（幸运，海豚恩惠）
    public static final FoodProperties LUCK_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.LUCK, 2000, 3), 1.0f).effect(()->new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 2000, 3), 1.0f).build();
    //避凶丹（减伤）
    public static final FoodProperties DAMAGE_RESISTANCE_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 3), 1.0f).build();
    //避水丹（水下呼吸）
    public static final FoodProperties WATER_RESISTANCE_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.WATER_BREATHING, 2000, 3), 1.0f).build();
    //避火丹（火抗）
    public static final FoodProperties FIRE_RESISTANCE_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 4), 1.0f).build();
    //避寒丹（不会获得寒冷）
    public static final FoodProperties FROZEN_RESISTANCE_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.FROZEN_RESISTANCE.get(), 2000, 3), 1.0f).build();
    //避雷丹（不会受到雷击伤害）
    public static final FoodProperties THUNDER_RESISTANCE_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.THUNDER_RESISTANCE.get(), 2000), 1.0f).build();
    //避毒丹（不会中毒）
    public static final FoodProperties POISON_RESISTANCE_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.POISON_RESISTANCE.get(), 2000), 1.0f).build();
    //九转还魂丹（死后半血复活）
    public static final FoodProperties NINE_TRANSFORMATIONS_RESURRECTION_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.NINE_TRANSFORMATIONS_RESURRECTION.get(), Integer.MAX_VALUE), 1.0f).build();
    //大力丸（增加伤害）
    public static final FoodProperties POWER_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 2), 1.0f).build();
    //碧藕金丹（加生命上限）
    public static final FoodProperties GOLDEN_PELLET = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2f).effect(()->new MobEffectInstance(TCREffects.HEALTH_BOOST.get(), 40), 1.0f).build();

}