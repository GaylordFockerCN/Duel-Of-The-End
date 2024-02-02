package com.gaboj1.tcr.worldgen.tf;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.ModBiomes;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;


public class BiomeLayerStack {
    public static final ResourceKey<Registry<BiomeLayerFactory>> BIOME_STACK_KEY = ResourceKey.createRegistryKey(TheCasketOfReveriesMod.namedRegistry("biome_layer_stack"));
    public static final DeferredRegister<BiomeLayerFactory> BIOME_LAYER_STACKS = DeferredRegister.create(BIOME_STACK_KEY, TheCasketOfReveriesMod.MOD_ID);
    public static final Codec<BiomeLayerFactory> DISPATCH_CODEC = BiomeLayerTypes.CODEC.dispatch("layer_type", BiomeLayerFactory::getType, BiomeLayerType::getCodec);
    public static final Codec<Holder<BiomeLayerFactory>> HOLDER_CODEC = RegistryFileCodec.create(BiomeLayerStack.BIOME_STACK_KEY, BiomeLayerStack.DISPATCH_CODEC, true);

    public static final ResourceKey<BiomeLayerFactory> RANDOM_FOREST_BIOMES = registerKey("random_forest_biomes");
    public static final ResourceKey<BiomeLayerFactory> BIOMES_ALONG_STREAMS = registerKey("biomes_along_streams");

    public static ResourceKey<BiomeLayerFactory> registerKey(String name) {
        return ResourceKey.create(BIOME_STACK_KEY, TheCasketOfReveriesMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<BiomeLayerFactory> context) {
        BiomeLayerFactory biomes = getBiomeLayerFactory();

//        biomes = new ZoomLayer.Factory(1000L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1001L, false, Holder.direct(biomes));
//
//        biomes = new StabilizeLayer.Factory(700L, Holder.direct(biomes));
//
//        biomes = new BorderLayer.Factory(500L, TFBiomes.FINAL_PLATEAU, TFBiomes.THORNLANDS, Holder.direct(biomes));
//
//        biomes = new ZoomLayer.Factory(1002L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1003L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1004L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1005L, false, Holder.direct(biomes));

        Holder.Reference<BiomeLayerFactory> randomBiomes = context.register(RANDOM_FOREST_BIOMES, biomes);

        BiomeLayerFactory riverLayer = new SeamLayer.Factory(1L, Biomes.RIVER, List.of(Biomes.GROVE), List.of(
                Pair.of(ModBiomes.PASTORAL_PLAINS, ModBiomes.DENSE_FOREST)
        ), randomBiomes);
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));

        context.register(BIOMES_ALONG_STREAMS, new FilteredBiomeLayer.Factory(100L, Biomes.RIVER, Holder.direct(riverLayer), randomBiomes));
    }

    private static BiomeLayerFactory getBiomeLayerFactory() {
        BiomeLayerFactory biomes = new RandomBiomeLayer.Factory(1L, 15, ImmutableList.of(
                Biomes.GROVE
        ), ImmutableList.of(
                Biomes.BASALT_DELTAS
        ));

        biomes = new KeyBiomesLayer.Factory(1000L, List.of(ModBiomes.DENSE_FOREST, Biomes.FOREST, Biomes.CHERRY_GROVE, Biomes.BAMBOO_JUNGLE), Holder.direct(biomes));
        biomes = new CompanionBiomesLayer.Factory(1000L, List.of(
                Pair.of(ModBiomes.DENSE_FOREST, ModBiomes.PASTORAL_PLAINS),
                Pair.of(Biomes.FOREST, Biomes.PLAINS),
                Pair.of(Biomes.CHERRY_GROVE, Biomes.BEACH),
                Pair.of(Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS)
        ), Holder.direct(biomes));
        return biomes;
    }

    @Deprecated // Needed for older worlds - It is otherwise literally same as above minus the registering
    public static Holder<BiomeLayerFactory> buildDefault() {
        BiomeLayerFactory biomes = getBiomeLayerFactory();

//        biomes = new ZoomLayer.Factory(1000L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1001L, false, Holder.direct(biomes));
//
//        biomes = new StabilizeLayer.Factory(700L, Holder.direct(biomes));
//
//        biomes = new BorderLayer.Factory(500L, TFBiomes.FINAL_PLATEAU, TFBiomes.THORNLANDS, Holder.direct(biomes));
//
//        biomes = new ZoomLayer.Factory(1002L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1003L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1004L, false, Holder.direct(biomes));
//        biomes = new ZoomLayer.Factory(1005L, false, Holder.direct(biomes));

        BiomeLayerFactory riverLayer = new SeamLayer.Factory(1L, Biomes.RIVER, List.of(Biomes.WOODED_BADLANDS), List.of(
                Pair.of(ModBiomes.PASTORAL_PLAINS, ModBiomes.DENSE_FOREST)
        ), Holder.direct(biomes));
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));

        return Holder.direct(new FilteredBiomeLayer.Factory(100L, Biomes.RIVER, Holder.direct(riverLayer), Holder.direct(biomes)));
    }
}