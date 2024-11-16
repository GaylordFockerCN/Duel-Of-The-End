package com.gaboj1.tcr.client.particle;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRParticles {
    public static final DeferredRegister<ParticleType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> MY_PARTICLE =
            REGISTRY.register("my_particles.json",() -> new SimpleParticleType(true));
}
