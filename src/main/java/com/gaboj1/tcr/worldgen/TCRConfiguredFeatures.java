package com.gaboj1.tcr.worldgen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
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

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> orichalcum_ore = List.of(OreConfiguration.target(stoneReplaceable,
                        TCRModBlocks.ORICHALCUM_ORE.get().defaultBlockState()));

        register(context, ORICHALCUM_ORE_KEY, Feature.ORE, new OreConfiguration(orichalcum_ore, 9));

        register(context, DENSE_FOREST_SPIRIT_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                new MegaJungleTrunkPlacer(10,12,19),

                BlockStateProvider.simple(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get()),
                new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),

                new TwoLayersFeatureSize(1, 1, 2)).build());

        register(context, DENSE_FOREST_SPIRIT_FLOWER_KEY, Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.020833334F,
                        List.of(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get().defaultBlockState()))))));


    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
