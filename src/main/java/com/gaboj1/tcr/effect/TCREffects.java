package com.gaboj1.tcr.effect;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCREffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TheCasketOfReveriesMod.MOD_ID);
    public static final RegistryObject<MobEffect> ORICHALCUM = REGISTRY.register("orichalcum", OrichalcumEffect::new);
    //copy from MOVEMENT_SPEED
    public static final RegistryObject<MobEffect> FLY_SPEED = REGISTRY.register("fly_speed",() -> new FlySpeedEffect(MobEffectCategory.BENEFICIAL, 3402751).addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.50000000298023224, AttributeModifier.Operation.MULTIPLY_TOTAL));

}
