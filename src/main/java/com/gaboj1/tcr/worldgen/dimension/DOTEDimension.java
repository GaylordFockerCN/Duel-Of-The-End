package com.gaboj1.tcr.worldgen.dimension;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.worldgen.biome.*;
import com.gaboj1.tcr.worldgen.noise.DOTENoiseSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class DOTEDimension {
    public static final ResourceKey<LevelStem> P_SKY_ISLAND_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(DuelOfTheEndMod.MOD_ID, "realm_of_the_dream"));
    public static final ResourceKey<Level> P_SKY_ISLAND_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(DuelOfTheEndMod.MOD_ID, "realm_of_the_dream"));
    public static final ResourceKey<DimensionType> P_SKY_ISLAND_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(DuelOfTheEndMod.MOD_ID, "realm_of_the_dream_type"));


    public static void bootstrapType(ResourceKey<DimensionType> typeResourceKey, BootstapContext<DimensionType> context){
        context.register(typeResourceKey, new DimensionType(
//                OptionalLong.empty(),
                OptionalLong.of(12000), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                true, // respawnAnchorWorks
                32, // minY
                1024, // height
                1024, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                0.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        bootstrapType(P_SKY_ISLAND_TYPE, context);
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                DOTEBiomeProvider.create(biomeRegistry),
                noiseGenSettings.getOrThrow(DOTENoiseSettings.PLAIN));

        DOTEChunkGenerator chunkGenerator = new DOTEChunkGenerator(wrappedChunkGenerator, noiseGenSettings.getOrThrow(DOTENoiseSettings.PLAIN));
        LevelStem stem = new LevelStem(dimTypes.getOrThrow(DOTEDimension.P_SKY_ISLAND_TYPE), chunkGenerator);
        context.register(P_SKY_ISLAND_KEY, stem);
    }

}