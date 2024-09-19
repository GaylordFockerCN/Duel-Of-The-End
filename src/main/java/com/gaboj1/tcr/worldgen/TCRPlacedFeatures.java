package com.gaboj1.tcr.worldgen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * @see net.minecraft.data.worldgen.placement.VegetationPlacements
 */
public class TCRPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ORICHALCUM_ORE_PLACED_KEY = registerKey("orichalcum_ore_placed");

    public static final ResourceKey<PlacedFeature> DENSE_FOREST_SPIRIT_TREE_PLACED_KEY = createKey("dense_forest_spirit_tree_placed");
    public static final ResourceKey<PlacedFeature> DENSE_FOREST_SPIRIT_FLOWER_PLACED_KEY = createKey("dense_forest_spirit_flower_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ORICHALCUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(TCRConfiguredFeatures.ORICHALCUM_ORE_KEY),
                TCROrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(100))));

        register(context, DENSE_FOREST_SPIRIT_TREE_PLACED_KEY, configuredFeatures.getOrThrow(TCRConfiguredFeatures.DENSE_FOREST_SPIRIT_TREE_KEY),
                // first:how many we're placing second:the chance getting extra(the third param)
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(50, 0.1f, 1),//TODO: change to suitable value.
                        TCRBlocks.DENSE_FOREST_SPIRIT_SAPLING.get()));

        register(context, DENSE_FOREST_SPIRIT_FLOWER_PLACED_KEY, configuredFeatures.getOrThrow(TCRConfiguredFeatures.DENSE_FOREST_SPIRIT_FLOWER_KEY),
                CountPlacement.of(3), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    }
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
