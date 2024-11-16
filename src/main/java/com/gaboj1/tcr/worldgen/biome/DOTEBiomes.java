package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class DOTEBiomes {
    public static final ResourceKey<Biome> AIR = createBiomeKey("air");
    public static final ResourceKey<Biome> BIOME1 = createBiomeKey("biome1");
    public static final ResourceKey<Biome> BIOME2 = createBiomeKey("biome2");

    public static ResourceKey<Biome> createBiomeKey(String name){
        return ResourceKey.create(Registries.BIOME,
                new ResourceLocation(DuelOfTheEndMod.MOD_ID, name));
    }

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(AIR, createAirBiome(context));
        context.register(BIOME1, createMBiome(context));
        context.register(BIOME2, createPBiome(context));
    }

    public static Biome createAirBiome(BootstapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.0f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x000000)
                        .waterFogColor(0x1b8bbf)
                        .grassColorOverride(0xffffff)
                        .foliageColorOverride(0xffffff)
                        .fogColor(0x000000)
                        .skyColor(0x000000)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createMBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_JUNGLE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x25faf2)
                        .waterFogColor(0x1b8bbf)
                        .grassColorOverride(0xffffff)
                        .foliageColorOverride(0xffffff)//藤蔓也会变
                        .fogColor(0xffffff)
                        .skyColor(0xffffff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
//                        .backgroundMusic(new Music(TCRSounds.BIOME1FOREST.getHolder().orElseThrow(), 12000, 24000, true))
                        .build())
                .build();
    }
    public static Biome createPBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.2f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x25faf2)
                        .waterFogColor(0x1b8bbf)
                        .grassColorOverride(0x4e7dae)
                        .foliageColorOverride(0x4e7dae)//藤蔓也会变
                        .fogColor(0x000000)
                        .skyColor(0x000000)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
//                        .backgroundMusic(new Music(TCRSounds.BIOME1FOREST.getHolder().orElseThrow(), 12000, 24000, true))
                        .build())
                .build();
    }

}