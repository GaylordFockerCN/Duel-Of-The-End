package com.gaboj1.tcr.worldgen.noise;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.stream.Stream;

public class ModNoiseBuilders {

    private static final SurfaceRules.RuleSource GRASS_BLOCK = SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState());
    private static final SurfaceRules.RuleSource DIRT = SurfaceRules.state(Blocks.DIRT.defaultBlockState());
    private static final SurfaceRules.RuleSource AIR = SurfaceRules.state(Blocks.AIR.defaultBlockState());
    private static final SurfaceRules.RuleSource WATER = SurfaceRules.state(Blocks.WATER.defaultBlockState());
    private static final SurfaceRules.RuleSource STONE = SurfaceRules.state(Blocks.STONE.defaultBlockState());


    public static NoiseGeneratorSettings skyIslandsNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
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

    public static NoiseGeneratorSettings plainNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        return new NoiseGeneratorSettings(
                new NoiseSettings(32, 256, 1, 2), // noiseSettings default:0 128 2 1
                Blocks.STONE.defaultBlockState(), // defaultBlock
                Blocks.GRASS_BLOCK.defaultBlockState(), // defaultFluid default:water
//                pMakeNoiseRouter(densityFunctions, noise), // noiseRouter
                overworld(densityFunctions,noise),
                pSurfaceRules(), // surfaceRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }

    //普通群系地表规则（瞎填
    public static SurfaceRules.RuleSource surfaceRules() {

        //让边界和中心群系全为空气
        SurfaceRules.RuleSource air = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.AIR),AIR));
        SurfaceRules.RuleSource finalBiome = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.FINAL),AIR));

        //海洋
        SurfaceRules.RuleSource water = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.biome4),WATER));
        //瞎几把操作..
        SurfaceRules.RuleSource noWater = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, noWater), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT));
        SurfaceRules.RuleSource denseForest = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.DENSE_FOREST),GRASS_BLOCK), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT));
        SurfaceRules.RuleSource all = SurfaceRules.sequence(finalBiome,surface,air,denseForest);

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(finalBiome)
                .add(air)
                .add(water)
                .add(surface)
                .add(denseForest)
                .add(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), all))//试试看abovePre这个
                ;


        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    //平坦模式地表规则（较为单调，但是村庄能正常生成）
    public static SurfaceRules.RuleSource pSurfaceRules() {

        //让边界和中心群系全为空气
        SurfaceRules.RuleSource air = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.AIR),AIR));
        SurfaceRules.RuleSource finalBiome = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.FINAL),AIR));

        //海洋
//        SurfaceRules.RuleSource water = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.biome4),WATER));
//        SurfaceRules.RuleSource water = SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.biome4), SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), STONE), SurfaceRules.ifTrue(surfaceNoiseAbove(-0.5), WATER)));
        SurfaceRules.RuleSource water = SurfaceRules.ifTrue(SurfaceRules.isBiome(TCRBiomes.biome4), SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(-1.75), WATER)));
        SurfaceRules.RuleSource overworldLike = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                //make everything else grass
                                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        //check if we're above ground, so hollow hills dont have grassy floors
                                                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(-4), 1), GRASS_BLOCK))),

                                //if we're around the area hollow hill floors are, check if we're underwater. If so place some dirt.
                                //This fixes streams having weird stone patches
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(
                                                SurfaceRules.yStartCheck(VerticalAnchor.absolute(-4), 1)),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(
                                                                SurfaceRules.waterBlockCheck(-1, 0)), DIRT))))),
                //dirt goes under the grass of course!
                //check if we're above ground, so hollow hills dont have dirt floors
                SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(-6), 1),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT))))));

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(finalBiome)
                .add(air)
                .add(water)
                .add(overworldLike)
                .add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("stone", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), STONE));
        ;


        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
    }

    private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        return createNoiseRouter(pDensityFunctions, pNoiseParameters, buildFinalDensity(pDensityFunctions));

//        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), getFunction(pDensityFunctions, CONTINENTS), getFunction(pDensityFunctions,  EROSION), DensityFunctions.zero(), getFunction(pDensityFunctions, RIDGES), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());

    }
    private static NoiseRouter pMakeNoiseRouter(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        return createNoiseRouter(pDensityFunctions, pNoiseParameters, pBuildFinalDensity(pDensityFunctions));

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

    private static DensityFunction pBuildFinalDensity(HolderGetter<DensityFunction> densityFunctions) {

        DensityFunction density = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"base_3d_noise_plain")));

//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));//-0.13
//        density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
//        density = slide(density, 0, 64, 64, 0, -0.2, 8, 40, -0.1);
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.15));//-0.15
//        density = DensityFunctions.blendDensity(density);
        //不知道有无效果。。
//        density = DensityFunctions.interpolated(density);
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

    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
    public static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
    public static final ResourceKey<DensityFunction> CONTINENTS_LARGE = createKey("overworld_large_biomes/continents");
    public static final ResourceKey<DensityFunction> EROSION_LARGE = createKey("overworld_large_biomes/erosion");
    private static final ResourceKey<DensityFunction> FACTOR_LARGE = createKey("overworld_large_biomes/factor");
    private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("overworld_large_biomes/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese");
    private static final ResourceKey<DensityFunction> FACTOR_AMPLIFIED = createKey("overworld_amplified/factor");
    private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("overworld_amplified/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese");
    private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
    private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_END = createKey("end/sloped_cheese");
    private static ResourceKey<DensityFunction> createKey(String location) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(location));
    }

    /**
     * 抄原版的主世界噪声
     * 关键方法：
     *  DensityFunction function0 = DensityFunctions.interpolated($$15);//平滑
     *  DensityFunction function =  DensityFunctions.add(DensityFunctions.yClampedGradient(-32, 256, 100, -100),function0);//缩放！！！！！！！！！！！超级关键的方法！试了好几十次才试出来
     * @param densityFunctions
     * @param noiseParameters
     * @return
     */
    protected static NoiseRouter overworld(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {

        //照抄不重要
        DensityFunction $$4 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5);
        DensityFunction $$5 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67);
        DensityFunction $$6 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143);
        DensityFunction $$7 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA));
        DensityFunction $$8 = getFunction(densityFunctions, SHIFT_X);
        DensityFunction $$9 = getFunction(densityFunctions, SHIFT_Z);
        DensityFunction $$10 = DensityFunctions.shiftedNoise2d($$8, $$9, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction $$11 = DensityFunctions.shiftedNoise2d($$8, $$9, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction $$12 = getFunction(densityFunctions, FACTOR);
        DensityFunction $$13 = getFunction(densityFunctions, DEPTH);
        DensityFunction $$14 = noiseGradientDensity(DensityFunctions.cache2d($$12), $$13);

        //决定了地形
        DensityFunction $$15 = getFunction(densityFunctions, SLOPED_CHEESE );
        //16-18为洞穴
        DensityFunction $$16 = DensityFunctions.mul(DensityFunctions.constant(5.0), getFunction(densityFunctions, ENTRANCES));
        DensityFunction $$18 = getFunction(densityFunctions, NOODLE);
//        DensityFunction function = slide($$18, -32, 256, 128, 0, -0.2, 8, 40, -0.1);
//        DensityFunction function = slide($$15, -32, 256, 128, 0, -0.2, 8, 40, -0.1);
        DensityFunction function0 = DensityFunctions.interpolated($$15);//平滑
        DensityFunction function =  DensityFunctions.add(DensityFunctions.yClampedGradient(-32, 256, 100, -100),function0);//缩放！！！！！！！！！！！超级关键的方法！试了好几十次才试出来

        return new NoiseRouter($$4, $$5, $$6, $$7, $$10, $$11, getFunction(densityFunctions, CONTINENTS), getFunction(densityFunctions, EROSION), $$13, getFunction(densityFunctions, RIDGES), slideOverworld(false, DensityFunctions.add($$14, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0)), function, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }

    protected static NoiseRouter overworld_BACK_UP(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters, boolean large, boolean amplified) {
        DensityFunction $$4 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5);
        DensityFunction $$5 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67);
        DensityFunction $$6 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143);
        DensityFunction $$7 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA));
        DensityFunction $$8 = getFunction(densityFunctions, SHIFT_X);
        DensityFunction $$9 = getFunction(densityFunctions, SHIFT_Z);
        DensityFunction $$10 = DensityFunctions.shiftedNoise2d($$8, $$9, 0.25, noiseParameters.getOrThrow(large ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE));
        DensityFunction $$11 = DensityFunctions.shiftedNoise2d($$8, $$9, 0.25, noiseParameters.getOrThrow(large ? Noises.VEGETATION_LARGE : Noises.VEGETATION));
        DensityFunction $$12 = getFunction(densityFunctions, large ? FACTOR_LARGE : (amplified ? FACTOR_AMPLIFIED : FACTOR));
        DensityFunction $$13 = getFunction(densityFunctions, large ? DEPTH_LARGE : (amplified ? DEPTH_AMPLIFIED : DEPTH));
        DensityFunction $$14 = noiseGradientDensity(DensityFunctions.cache2d($$12), $$13);
        DensityFunction $$15 = getFunction(densityFunctions, large ? SLOPED_CHEESE_LARGE : (amplified ? SLOPED_CHEESE_AMPLIFIED : SLOPED_CHEESE));
        DensityFunction $$16 = DensityFunctions.min($$15, DensityFunctions.mul(DensityFunctions.constant(5.0), getFunction(densityFunctions, ENTRANCES)));
        DensityFunction $$18 = DensityFunctions.min(postProcess(slideOverworld(amplified, $$16)), getFunction(densityFunctions, NOODLE));
        DensityFunction function = slide($$18, -32, 256, 128, 0, -0.2, 8, 40, -0.1);
        return new NoiseRouter($$4, $$5, $$6, $$7, $$10, $$11, getFunction(densityFunctions, large ? CONTINENTS_LARGE : CONTINENTS), getFunction(densityFunctions, large ? EROSION_LARGE : EROSION), $$13, getFunction(densityFunctions, RIDGES), slideOverworld(amplified, DensityFunctions.add($$14, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0)), function, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }

    private static DensityFunction slideOverworld(boolean amplified, DensityFunction densityFunction) {
        return slide(densityFunction, -64, 384, amplified ? 16 : 80, amplified ? 0 : 64, -0.078125, 0, 24, amplified ? 0.4 : 0.1171875);
    }


}
