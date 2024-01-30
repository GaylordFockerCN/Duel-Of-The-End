package com.gaboj1.tcr.worldgen.biome;

import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.function.Consumer;

public final class BiomeMaker {

    public static List<TerrainColumn> makeBiomeList(HolderGetter<Biome> biomeRegistry, Holder<Biome> undergroundBiome) {
        return List.of(
//                biomeColumnWithUnderground(-0.9F, 0.15F, biomeRegistry, TFBiomes.SWAMP, undergroundBiome),
//                biomeColumnWithUnderground(-0.2F, 0.05F, biomeRegistry, TFBiomes.FIRE_SWAMP, undergroundBiome),
//
//                biomeColumnWithUnderground(0.025F, 0.005F, biomeRegistry, TFBiomes.DARK_FOREST, undergroundBiome),
//                biomeColumnWithUnderground(0.025F, 0.005F, biomeRegistry, TFBiomes.DARK_FOREST_CENTER, undergroundBiome),
//
//                biomeColumnWithUnderground(0.05F, 0.15F, biomeRegistry, TFBiomes.SNOWY_FOREST, undergroundBiome),
//                biomeColumnWithUnderground(0.025F, 0.05F, biomeRegistry, TFBiomes.GLACIER, undergroundBiome),
//
//                biomeColumnWithUnderground(3.0F, 0.25F, biomeRegistry, TFBiomes.HIGHLANDS, undergroundBiome),
//                biomeColumnToBedrock(7.0F, 0.1F, biomeRegistry, TFBiomes.THORNLANDS),
//                biomeColumnToBedrock(13.75F, 0.025F, biomeRegistry, TFBiomes.FINAL_PLATEAU)


                biomeColumnWithUnderground(-0.9F, 0.15F, biomeRegistry, ModBiomes.PASTORAL_PLAINS, undergroundBiome),
                biomeColumnWithUnderground(-0.2F, 0.05F, biomeRegistry, ModBiomes.DENSE_FOREST, undergroundBiome)


        );
    }

    private static TerrainColumn biomeColumnWithUnderground(float noiseDepth, float noiseScale, HolderGetter<Biome> biomeRegistry, ResourceKey<Biome> key, Holder<Biome> undergroundBiome) {
        Holder.Reference<Biome> biomeHolder = biomeRegistry.getOrThrow(key);

        biomeHolder.bindKey(key);

        return makeColumn(noiseDepth, noiseScale, biomeHolder, treeMap -> {
            // This will put the transition boundary around Y-8
            treeMap.put(Math.min(noiseDepth - 1, -1), biomeHolder);
            treeMap.put(Math.min(noiseDepth - 3, -3), undergroundBiome);
        });
    }

    private static TerrainColumn biomeColumnToBedrock(float noiseDepth, float noiseScale, HolderGetter<Biome> biomeRegistry, ResourceKey<Biome> key) {
        Holder.Reference<Biome> biomeHolder = biomeRegistry.getOrThrow(key);

        biomeHolder.bindKey(key);

        return makeColumn(noiseDepth, noiseScale, biomeHolder, treeMap -> treeMap.put(0, biomeHolder));
    }

    private static TerrainColumn makeColumn(float noiseDepth, float noiseScale, Holder<Biome> biomeHolder, Consumer<Float2ObjectSortedMap<Holder<Biome>>> layerBuilder) {
        return new TerrainColumn(biomeHolder, Util.make(new Float2ObjectAVLTreeMap<>(), layerBuilder), noiseDepth, noiseScale);
    }
}
