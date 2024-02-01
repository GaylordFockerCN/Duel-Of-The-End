package com.gaboj1.tcr.worldgen.dimension;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

public class ModDensityFunctions {
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_AETHER = createKey("base_3d_noise");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        context.register(BASE_3D_NOISE_AETHER, BlendedNoise.createUnseeded(
                0.25, // xz scale
                0.25, // y scale
                80.0, // xz factor
                160.0, // y factor
                8.0)); // smear scale multiplier, capped at 8
    }
}
