package com.gaboj1.tcr.worldgen.noise;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SKY_ISLANDS = createKey("sky_islands_noise_gen");//空岛
    public static final ResourceKey<NoiseGeneratorSettings> PLAIN = createKey("plain_noise_gen");//大平地

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        context.register(SKY_ISLANDS, ModNoiseBuilders.skyIslandsNoiseSettings(densityFunctions, noise));
        context.register(PLAIN, ModNoiseBuilders.plainNoiseSettings(densityFunctions, noise));
    }

}
