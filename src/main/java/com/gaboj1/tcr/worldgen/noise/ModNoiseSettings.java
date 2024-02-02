package com.gaboj1.tcr.worldgen.noise;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.tf.TFSurfaceRules;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class ModNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SKY_ISLANDS = createKey("sky_islands_noise_gen");//the Aether

    public static final ResourceKey<NoiseGeneratorSettings> TWILIGHT_NOISE_GEN = createKey("twilight_noise_gen");//Twilight Forest

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        context.register(SKY_ISLANDS, ModNoiseBuilders.skylandsNoiseSettings(densityFunctions, noise));
        context.register(TWILIGHT_NOISE_GEN, tfDefault());
    }

    public static NoiseGeneratorSettings tfDefault() {
        NoiseSettings tfNoise = NoiseSettings.create(
                -32, //TODO Deliberate over this. For now it'll be -32
                256,
                1,
                2
        );

        return new NoiseGeneratorSettings(
                tfNoise,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                new NoiseRouter(
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero()
                ),

                TFSurfaceRules.tfSurface(),
                List.of(),
                0,
                false,
                false,
                false,
                false
        );
    }

}
