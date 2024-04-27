package com.gaboj1.tcr.effect;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCREffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TheCasketOfReveriesMod.MOD_ID);
    public static final RegistryObject<MobEffect> ORICHALCUM = REGISTRY.register("orichalcum", OrichalcumEffect::new);

}
