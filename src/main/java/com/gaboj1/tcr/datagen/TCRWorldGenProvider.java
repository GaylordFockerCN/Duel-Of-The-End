package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.worldgen.TCRBiomeModifiers;
import com.gaboj1.tcr.worldgen.TCRConfiguredFeatures;
import com.gaboj1.tcr.worldgen.TCRPlacedFeatures;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.gaboj1.tcr.worldgen.noise.TCRDensityFunctions;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import com.gaboj1.tcr.worldgen.noise.TCRNoiseSettings;
import com.gaboj1.tcr.worldgen.noise.TCRNoises;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TCRWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.STRUCTURE, TCRStructures::bootstrap)
//            .add(Registries.STRUCTURE_SET, TCRStructureSets::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, TCRConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, TCRPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, TCRBiomeModifiers::bootstrap)
            .add(Registries.DENSITY_FUNCTION, TCRDensityFunctions::bootstrap)
            .add(Registries.NOISE, TCRNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, TCRNoiseSettings::bootstrap)
            .add(Registries.BIOME, TCRBiomes::boostrap)
            .add(Registries.LEVEL_STEM, TCRDimension::bootstrapStem)
            .add(Registries.DIMENSION_TYPE, TCRDimension::bootstrapType);

    public TCRWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DuelOfTheEndMod.MOD_ID));
    }
}
