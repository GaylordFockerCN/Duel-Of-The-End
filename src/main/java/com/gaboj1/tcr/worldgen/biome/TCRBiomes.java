package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.worldgen.TCRPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class TCRBiomes {

    public static final ResourceKey<Biome> AIR = createBiomeKey("air_biome");

    public static final ResourceKey<Biome> FINAL = createBiomeKey("final_biome");

    //Pastoral Plains & the aging forest
    public static final ResourceKey<Biome> PASTORAL_PLAINS = createBiomeKey("pastoral_plains_biome");
    public static final ResourceKey<Biome> DENSE_FOREST = createBiomeKey("dense_forest_biome");


    public static final ResourceKey<Biome> DESERT = createBiomeKey("desert");
    public static final ResourceKey<Biome> DESERT_CENTER = createBiomeKey("desert_center");


    //山水画廊 和 青云之巅
    // Gallery of Serene Landscapes & Summit of Azure Skies
    public static final ResourceKey<Biome> GALLERY_OF_SERENE = createBiomeKey("gallery_of_serene_biome");
    public static final ResourceKey<Biome> AZURE_SKIES = createBiomeKey("azure_skies_biome");


    //威严之水 和 亚特兰蒂斯
    //Aquatic Majesty & Atlantis
    public static final ResourceKey<Biome> AQUATIC_MAJESTY = createBiomeKey("aquatic_majesty_biome");
    public static final ResourceKey<Biome> ATLANTIS = createBiomeKey("atlantis_biome");
    public static final ResourceKey<Biome> RIVER = createBiomeKey("river");


    public static final ResourceKey<Biome> biomeBorder = TCRBiomes.AIR;
    public static final ResourceKey<Biome> finalBiome = TCRBiomes.FINAL;


    public static final ResourceKey<Biome> biome1 = TCRBiomes.PASTORAL_PLAINS;
    public static final ResourceKey<Biome> biome1Center = TCRBiomes.DENSE_FOREST;


    public static final ResourceKey<Biome> biome2 = TCRBiomes.GALLERY_OF_SERENE;
    public static final ResourceKey<Biome> biome2Center = TCRBiomes.AZURE_SKIES;


    public static final ResourceKey<Biome> biome3 = TCRBiomes.DESERT;
    public static final ResourceKey<Biome> biome3Center = TCRBiomes.DESERT_CENTER;


    public static final ResourceKey<Biome> biome4 = TCRBiomes.AQUATIC_MAJESTY;
    public static final ResourceKey<Biome> biome4Center = TCRBiomes.ATLANTIS;

    public static ResourceKey<Biome> createBiomeKey(String name){
        return ResourceKey.create(Registries.BIOME,
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }

    public static void boostrap(BootstapContext<Biome> context) {

        context.register(AIR, createAirBiome(context));
        context.register(FINAL, createFinalBiome(context));

        context.register(PASTORAL_PLAINS, createPastoralPlainsBiome(context));
        context.register(DENSE_FOREST, createDenseForestBiome(context));

        context.register(GALLERY_OF_SERENE, createGalleryOfSereneBiome(context));
        context.register(AZURE_SKIES, createAzureSkiesBiome(context));

        context.register(DESERT, createDesertBiome(context));
        context.register(DESERT_CENTER, createDesertBiome(context));

        context.register(AQUATIC_MAJESTY, createAquaticMajestyBiome(context));
        context.register(ATLANTIS, createTestBiome(context,0x0060ff));

        context.register(RIVER, createTestBiome(context,0x0060ff));

    }

    public static Biome createTestBiome(BootstapContext<Biome> context,int pGrassColorOverride) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
                .temperature(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(pGrassColorOverride)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createAirBiome(BootstapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
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

    public static Biome createFinalBiome(BootstapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.0F)
                .temperature(2.0F)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .grassColorOverride(0xffffff)
                        .foliageColorOverride(0xffffff)
                        .fogColor(12638463)
                        .skyColor(0xff9af7)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createPastoralPlainsBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.JELLY_CAT.get(), 2, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.LLAMA, 1, 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TCRPlacedFeatures.CATNIP_PLACED_KEY);

        biomeBuilder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(0x76b373)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(new Music(TCRSounds.BIOME1VILLAGE.getHolder().orElseThrow(), 12000, 24000, true))
                        .build())
                .build();
    }

    public static Biome createDenseForestBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TCREntities.SMALL_TREE_MONSTER.get(), 5, 1, 3));
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TCREntities.MIDDLE_TREE_MONSTER.get(), 3, 1, 2));
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TCREntities.TREE_GUARDIAN.get(), 1, 1, 1));
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TCRPlacedFeatures.DENSE_FOREST_SPIRIT_TREE_PLACED_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TCRPlacedFeatures.DENSE_FOREST_SPIRIT_FLOWER_PLACED_KEY);
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
                        .backgroundMusic(new Music(TCRSounds.BIOME1FOREST.getHolder().orElseThrow(), 12000, 24000, true))
                        .build())
                .build();
    }

    public static Biome createGalleryOfSereneBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.SQUIRREL.get(), 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 1, 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_CHERRY);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(11983713)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createAzureSkiesBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.SQUIRREL.get(), 1, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.SWORD_CONTROLLER.get(), 1, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.BOXER.get(), 1, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.BIG_HAMMER.get(), 1, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.SNOW_SWORDMAN.get(), 1, 1, 1));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.TIGER.get(), 1, 1, 1));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_CHERRY);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.9f)
                .temperature(-0.3f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(11983713)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createDesertBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_CACTUS_DESERT);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_DESERT);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0F)
                .temperature(2.0F)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(0xffea00)
                        .foliageColorOverride(0xffea00)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome createDarkSakuraBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(11983713)
                        .waterColor(0x25faf2)
                        .waterFogColor(0x1b8bbf)
                        .fogColor(0x22a1e6)
                        .skyColor(0xcbf2ff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }


    public static Biome createAquaticMajestyBiome(BootstapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TCREntities.CRAB.get(), 1, 1, 2));
        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .grassColorOverride(0x5490f3)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

}