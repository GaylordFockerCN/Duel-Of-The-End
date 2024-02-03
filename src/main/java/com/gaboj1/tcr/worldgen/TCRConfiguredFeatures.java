package com.gaboj1.tcr.worldgen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;

public class TCRConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_FOREST_SPIRIT_TREE_KEY = registerKey("dense_tree");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {



        register(context, DENSE_FOREST_SPIRIT_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                new MegaJungleTrunkPlacer(10,12,19),

                BlockStateProvider.simple(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get()),
                new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),

                new TwoLayersFeatureSize(1, 1, 2)).build());
//        decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, new LeaveVineDecorator(0.25F)))
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
