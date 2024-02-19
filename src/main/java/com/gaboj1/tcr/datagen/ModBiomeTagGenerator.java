package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagGenerator extends BiomeTagsProvider {
    public static final TagKey<Biome> IS_TCR = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("in_tcr"));
    public static final TagKey<Biome> VALID_BIOME1 = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("valid_biome1"));

    public ModBiomeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, TheCasketOfReveriesMod.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(IS_TCR).add(
                TCRBiomes.AIR,
                TCRBiomes.biome1,
                TCRBiomes.biome1Center,
                TCRBiomes.biome2,
                TCRBiomes.biome2Center,
                TCRBiomes.biome3,
                TCRBiomes.biome3Center,
                TCRBiomes.biome4,
                TCRBiomes.biome4Center,
                TCRBiomes.FINAL
        );

        tag(VALID_BIOME1).add(TCRBiomes.biome1,TCRBiomes.biome1Center);


        //TODO:apparently using forge and vanilla tags allows other mods to spawn stuff in our biomes. Will keep these commented out here just in case we need to reference them later.
        //vanilla biome categories
//		tag(BiomeTags.IS_FOREST).add(
//				TFBiomes.FOREST, TFBiomes.DENSE_FOREST, TFBiomes.FIREFLY_FOREST,
//				TFBiomes.OAK_SAVANNAH, TFBiomes.MUSHROOM_FOREST, TFBiomes.DENSE_MUSHROOM_FOREST,
//				TFBiomes.DARK_FOREST, TFBiomes.DARK_FOREST_CENTER,
//				TFBiomes.SNOWY_FOREST, TFBiomes.HIGHLANDS
//		);
//		tag(BiomeTags.IS_MOUNTAIN).add(TFBiomes.HIGHLANDS);
//		tag(BiomeTags.IS_HILL).add(TFBiomes.THORNLANDS);

        //forge biome categories
//		tag(Tags.Biomes.IS_DENSE).add(TFBiomes.DENSE_FOREST, TFBiomes.DENSE_MUSHROOM_FOREST, TFBiomes.DARK_FOREST, TFBiomes.DARK_FOREST_CENTER, TFBiomes.SNOWY_FOREST, TFBiomes.THORNLANDS);
//		tag(Tags.Biomes.IS_SPARSE).add(TFBiomes.CLEARING, TFBiomes.OAK_SAVANNAH, TFBiomes.GLACIER, TFBiomes.FINAL_PLATEAU);
//		tag(Tags.Biomes.IS_PLAINS).add(TFBiomes.CLEARING);
//		tag(Tags.Biomes.IS_MUSHROOM).add(TFBiomes.MUSHROOM_FOREST, TFBiomes.DENSE_MUSHROOM_FOREST);
//		tag(Tags.Biomes.IS_RARE).add(TFBiomes.ENCHANTED_FOREST, TFBiomes.SPOOKY_FOREST, TFBiomes.CLEARING, TFBiomes.DENSE_MUSHROOM_FOREST, TFBiomes.LAKE);
//		tag(Tags.Biomes.IS_WATER).add(TFBiomes.LAKE, TFBiomes.STREAM);
//		tag(Tags.Biomes.IS_MAGICAL).add(TFBiomes.ENCHANTED_FOREST, TFBiomes.DARK_FOREST_CENTER);
//		tag(Tags.Biomes.IS_SPOOKY).add(TFBiomes.SPOOKY_FOREST, TFBiomes.DARK_FOREST, TFBiomes.DARK_FOREST_CENTER);
//		tag(Tags.Biomes.IS_DEAD).add(TFBiomes.SPOOKY_FOREST, TFBiomes.THORNLANDS, TFBiomes.FINAL_PLATEAU);
//		tag(Tags.Biomes.IS_SWAMP).add(TFBiomes.SWAMP, TFBiomes.FIRE_SWAMP);
//		tag(Tags.Biomes.IS_SNOWY).add(TFBiomes.SNOWY_FOREST);
//		tag(Tags.Biomes.IS_CONIFEROUS).add(TFBiomes.SNOWY_FOREST, TFBiomes.HIGHLANDS);
//		tag(Tags.Biomes.IS_COLD).add(TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER);
//		tag(Tags.Biomes.IS_WASTELAND).add(TFBiomes.GLACIER, TFBiomes.THORNLANDS, TFBiomes.FINAL_PLATEAU);
//		tag(Tags.Biomes.IS_DRY).add(TFBiomes.THORNLANDS, TFBiomes.FINAL_PLATEAU);
//		tag(Tags.Biomes.IS_PLATEAU).add(TFBiomes.FINAL_PLATEAU);
//		tag(Tags.Biomes.IS_UNDERGROUND).add(TFBiomes.UNDERGROUND);

        //other vanilla tags
//        tag(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS).addTag(IS_TWILIGHT);
//        tag(BiomeTags.WITHOUT_PATROL_SPAWNS).addTag(IS_TWILIGHT);
//        tag(BiomeTags.WITHOUT_ZOMBIE_SIEGES).addTag(IS_TWILIGHT);

        //even though we won't spawn vanilla frogs, we'll still add support for the variants
//        tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).add(TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER);
//        tag(BiomeTags.SPAWNS_SNOW_FOXES).add(TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER);
//        tag(BiomeTags.SNOW_GOLEM_MELTS).add(TFBiomes.OAK_SAVANNAH, TFBiomes.FIRE_SWAMP);
//        tag(BiomeTags.SPAWNS_WARM_VARIANT_FROGS).add(TFBiomes.OAK_SAVANNAH, TFBiomes.FIRE_SWAMP);
//
//        tag(BiomeTags.HAS_CLOSER_WATER_FOG).add(TFBiomes.SPOOKY_FOREST, TFBiomes.SWAMP, TFBiomes.FIRE_SWAMP);
    }

    @Override
    public String getName() {
        return "The Casket Of Reveries Biome Tags";
    }
}