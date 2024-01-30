package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.worldgen.ModPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {


    public static final ResourceKey<Biome> AIR = createBiomeKey("air_biome");

    //Pastoral Plains & the aging forest
    public static final ResourceKey<Biome> PASTORAL_PLAINS = createBiomeKey("pastoral_plains_biome");
    public static final ResourceKey<Biome> DENSE_FOREST = createBiomeKey("dense_forest_biome");



    public static ResourceKey<Biome> createBiomeKey(String name){
        return ResourceKey.create(Registries.BIOME,
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    public static void boostrap(BootstapContext<Biome> context) {

        context.register(AIR, createAirBiome(context));
        context.register(PASTORAL_PLAINS, createDenseForestBiome(context));
        context.register(DENSE_FOREST, createDenseForestBiome(context));

    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
//        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
//        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
//        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
//        BiomeDefaultFeatures.addDefaultSprings(builder);
//        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome createAirBiome(BootstapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x25faf2)
                        .waterFogColor(0x1b8bbf)
                        .grassColorOverride(0x4e7dae)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x22a1e6)
                        .skyColor(0xcbf2ff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createDenseForestBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCRModEntities.TIGER.get(), 5, 4, 4));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
//        globalOverworldGeneration(biomeBuilder);
//        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
//        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
//        BiomeDefaultFeatures.addFerns(biomeBuilder);
//        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
//        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

//        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
//        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.DENSE_FOREST_SPIRIT_TREE_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x25faf2)
                        .waterFogColor(0x1b8bbf)
                        .grassColorOverride(0x4e7dae)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x22a1e6)
                        .skyColor(0xcbf2ff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }
}