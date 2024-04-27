package com.gaboj1.tcr.particle;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRModParticles {
    public static final DeferredRegister<ParticleType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TheCasketOfReveriesMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> MY_PARTICLE =
            REGISTRY.register("my_particles.json",() -> new SimpleParticleType(true));
}
