package com.gaboj1.tcr.worldgen.noise;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;


public class ModDensityFunctions {
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_AETHER = createKey("base_3d_noise");
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_PLAIN = createKey("base_3d_noise_plain");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        context.register(BASE_3D_NOISE_AETHER, BlendedNoise.createUnseeded(0.25, 0.25, 80.0, 160.0, 8.0)//(0.25, 0.25, 80.0, 160.0, 8.0)
//                BlendedNoise.createUnseeded(
//                0.25, // xz scale
//                0.25, // y scale
//                80.0, // xz factor
//                160.0, // y factor
//                8.0)
        ); // smear scale multiplier, capped at 8

        context.register(BASE_3D_NOISE_PLAIN, BlendedNoise.createUnseeded(0.25, 0.125, 80.0, 160.0, 4.0));
        HolderGetter<DensityFunction> densityFunctionGetter = context.lookup(Registries.DENSITY_FUNCTION);

//
//        DensityFunction $$17 = registerAndWrap(context, depthKey, DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5, -1.5), $$15));
//        DensityFunction $$18 = registerAndWrap(context, jaggednessKey, splineWithBlending(DensityFunctions.spline(TerrainProvider.overworldJaggedness($$11, $$12, $$13, $$14, amplified)), BLENDING_JAGGEDNESS));
//        DensityFunction $$19 = DensityFunctions.mul($$18, jaggedNoise.halfNegative());
//        DensityFunction $$20 = noiseGradientDensity($$16, DensityFunctions.add($$17, $$19));
//        context.register(BASE_3D_NOISE_PLAIN, DensityFunctions.add($$20, getFunction(densityFunctionGetter, BASE_3D_NOISE_OVERWORLD)));
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

}
