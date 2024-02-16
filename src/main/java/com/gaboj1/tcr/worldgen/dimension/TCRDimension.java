package com.gaboj1.tcr.worldgen.dimension;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.*;
import com.gaboj1.tcr.worldgen.noise.ModNoiseSettings;
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
import java.util.Random;

public class TCRDimension {
    
    //Realm of the Dream 梦之域 TODO:变量名懒得改了，翻译别忘了
    public static final ResourceKey<LevelStem> SKY_ISLAND_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "realm_of_the_dream"));
    public static final ResourceKey<Level> SKY_ISLAND_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "realm_of_the_dream"));
    public static final ResourceKey<DimensionType> SKY_ISLAND_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "realm_of_the_dream_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(SKY_ISLAND_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                true, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                0.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {

        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                TCRBiomeProvider.create(biomeRegistry),//TODO 换成世界种子，如果不能换的话要把seed从这里去掉否则图都一样了
                noiseGenSettings.getOrThrow(ModNoiseSettings.SKY_ISLANDS));

        TCRChunkGenerator chunkGenerator = new TCRChunkGenerator(wrappedChunkGenerator,noiseGenSettings.getOrThrow(ModNoiseSettings.SKY_ISLANDS));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(TCRDimension.SKY_ISLAND_TYPE), chunkGenerator);

        context.register(SKY_ISLAND_KEY, stem);

    }
}