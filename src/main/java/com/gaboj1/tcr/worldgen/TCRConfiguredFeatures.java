package com.gaboj1.tcr.worldgen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

/**
 * @see net.minecraft.data.worldgen.features.VegetationFeatures
 */
public class TCRConfiguredFeatures {

    //Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORICHALCUM_ORE_KEY = registerKey("orichalcum_ore");

    //Plants
    public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_FOREST_SPIRIT_TREE_KEY = registerKey("dense_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_FOREST_SPIRIT_FLOWER_KEY = registerKey("dense_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CATNIP_KEY = registerKey("catnip_place");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> orichalcum_ore = List.of(OreConfiguration.target(stoneReplaceable,
                        TCRBlocks.ORICHALCUM_ORE.get().defaultBlockState()));

        register(context, ORICHALCUM_ORE_KEY, Feature.ORE, new OreConfiguration(orichalcum_ore, 9));

        register(context, DENSE_FOREST_SPIRIT_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                new MegaJungleTrunkPlacer(10,12,19),
                BlockStateProvider.simple(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get()),
                new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                new TwoLayersFeatureSize(1, 1, 2))
                .decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))//添加藤蔓
                .build());

        register(context, DENSE_FOREST_SPIRIT_FLOWER_KEY, Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.020833334F,
                        List.of(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get().defaultBlockState(), TCRBlocks.BLUE_MUSHROOM.get().defaultBlockState()))))));

        register(context, CATNIP_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 2, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, TCRBlocks.CATNIP.get().defaultBlockState(), List.of(TCRBlocks.CATNIP.get().defaultBlockState()), List.of(TCRBlocks.CATNIP.get().defaultBlockState()))))));

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
