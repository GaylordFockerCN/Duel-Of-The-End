package com.gaboj1.tcr.worldgen.noise;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class ModNoiseBuilders {

    private static final SurfaceRules.RuleSource GRASS_BLOCK = SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState());
    private static final SurfaceRules.RuleSource DIRT = SurfaceRules.state(Blocks.DIRT.defaultBlockState());
    private static final SurfaceRules.RuleSource AIR = SurfaceRules.state(Blocks.AIR.defaultBlockState());

    public static NoiseGeneratorSettings skylandsNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        return new NoiseGeneratorSettings(
                new NoiseSettings(32, 48, 1, 1), // noiseSettings default:0 128 2 1
                Blocks.STONE.defaultBlockState(), // defaultBlock
                Blocks.GRASS_BLOCK.defaultBlockState(), // defaultFluid default:water
                makeNoiseRouter(densityFunctions, noise), // noiseRouter
                surfaceRules(), // surfaceRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }


    //TODO 填补空隙
    public static SurfaceRules.RuleSource surfaceRules() {
        SurfaceRules.RuleSource air = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.AIR),AIR));

        SurfaceRules.RuleSource finalBiome = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.FINAL),AIR));
//        SurfaceRules.RuleSource finalBiome = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.FINAL),DIRT));

        SurfaceRules.RuleSource noWater = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);

        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, noWater), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT));
//        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.aboveBottom(10),10),DIRT));

        SurfaceRules.RuleSource denseForest = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.DENSE_FOREST),GRASS_BLOCK), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT));
        SurfaceRules.RuleSource all = SurfaceRules.sequence(finalBiome,surface,air,denseForest);

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(finalBiome)
                .add(air)
                .add(surface)
                .add(denseForest)
//                .add(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), all))//试试看abovePre这个
                ;


        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        return createNoiseRouter(pDensityFunctions, pNoiseParameters, buildFinalDensity(pDensityFunctions));

//        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), getFunction(pDensityFunctions, CONTINENTS), getFunction(pDensityFunctions,  EROSION), DensityFunctions.zero(), getFunction(pDensityFunctions, RIDGES), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());

    }

    private static DensityFunction noiseGradientDensity(DensityFunction p_212272_, DensityFunction p_212273_) {
        DensityFunction $$2 = DensityFunctions.mul(p_212273_, p_212272_);
        return DensityFunctions.mul(DensityFunctions.constant(4.0), $$2.quarterNegative());
    }

    private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions) {
        DensityFunction density = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"base_3d_noise")));
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));//-0.13
//        density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
//        density = slide(density, 0, 64, 64, 0, -0.2, 8, 40, -0.1);
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.15));//-0.15
//        density = DensityFunctions.blendDensity(density);
        //不知道有无效果。。
        density = DensityFunctions.interpolated(density);
        density = DensityFunctions.interpolated(density);
        density = DensityFunctions.interpolated(density);
        density = DensityFunctions.interpolated(density);
//        density = density.squeeze();
//        return postProcess(slide(density, -64, 384, 80,  64, -0.078125, 0, 24, 0.1171875));
        return density;
    }
    private static DensityFunction postProcess(DensityFunction p_224493_) {
        DensityFunction $$1 = DensityFunctions.blendDensity(p_224493_);
        return DensityFunctions.mul(DensityFunctions.interpolated($$1), DensityFunctions.constant(0.64)).squeeze();
    }

    /**
     * [CODE COPY] - {@link NoiseRouterData (DensityFunction, int, int, int, int, double, int, int, double)} (DensityFunction, int, int, int, int, double, int, int, double)}.
     */
    private static DensityFunction slide(DensityFunction density, int minY, int maxY, int fromYTop, int toYTop, double offset1, int fromYBottom, int toYBottom, double offset2) {
        DensityFunction topSlide = DensityFunctions.yClampedGradient(minY + maxY - fromYTop, minY + maxY - toYTop, 1, 0);
        density = DensityFunctions.lerp(topSlide, offset1, density);
        DensityFunction bottomSlide = DensityFunctions.yClampedGradient(minY + fromYBottom, minY + toYBottom, 0, 1);
        return DensityFunctions.lerp(bottomSlide, offset2, density);
    }

    /**
     * [CODE COPY] - {@link NoiseRouterData(HolderGetter, HolderGetter, DensityFunction)}.<br><br>
     * Logic that called {@link NoiseRouterData (DensityFunction)} has been moved to {@link ModNoiseBuilders#buildFinalDensity(HolderGetter)}.
     */
    private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise, DensityFunction finalDensity) {
        DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
        DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(ModNoises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(ModNoises.VEGETATION));

//        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(Noises.TEMPERATURE));
//        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(Noises.VEGETATION));
        return new NoiseRouter(
                DensityFunctions.zero(), // barrier noise
                DensityFunctions.zero(), // fluid level floodedness noise
                DensityFunctions.zero(), // fluid level spread noise
                DensityFunctions.zero(), // lava noise
                temperature, // temperature
                vegetation, // vegetation
                DensityFunctions.zero(), // continentalness noise
                DensityFunctions.zero(), // erosion noise
                DensityFunctions.zero(), // depth
                DensityFunctions.zero(), // ridges
                DensityFunctions.zero(), // initial density without jaggedness, not sure if this is needed. Some vanilla dimensions use this while others don't.
                finalDensity, // finaldensity
                DensityFunctions.zero(), // veinToggle
                DensityFunctions.zero(), // veinRidged
                DensityFunctions.zero()); // veinGap
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

}
