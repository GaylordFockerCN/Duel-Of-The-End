package com.p1nero.dote.datagen;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.worldgen.DOTEBiomeModifiers;
import com.p1nero.dote.worldgen.DOTEConfiguredFeatures;
import com.p1nero.dote.worldgen.DOTEPlacedFeatures;
import com.p1nero.dote.worldgen.biome.DOTEBiomes;
import com.p1nero.dote.worldgen.noise.DOTEDensityFunctions;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.noise.DOTENoiseSettings;
import com.p1nero.dote.worldgen.noise.DOTENoises;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DOTEWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.STRUCTURE, TCRStructures::bootstrap)
//            .add(Registries.STRUCTURE_SET, TCRStructureSets::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, DOTEConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, DOTEPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, DOTEBiomeModifiers::bootstrap)
            .add(Registries.DENSITY_FUNCTION, DOTEDensityFunctions::bootstrap)
            .add(Registries.NOISE, DOTENoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, DOTENoiseSettings::bootstrap)
            .add(Registries.BIOME, DOTEBiomes::boostrap)
            .add(Registries.LEVEL_STEM, DOTEDimension::bootstrapStem)
            .add(Registries.DIMENSION_TYPE, DOTEDimension::bootstrapType);

    public DOTEWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DuelOfTheEndMod.MOD_ID));
    }
}
