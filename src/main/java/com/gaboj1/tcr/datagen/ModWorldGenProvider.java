package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.TCRConfiguredFeatures;
import com.gaboj1.tcr.worldgen.TCRPlacedFeatures;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.gaboj1.tcr.worldgen.noise.ModDensityFunctions;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import com.gaboj1.tcr.worldgen.noise.ModNoiseSettings;
import com.gaboj1.tcr.worldgen.noise.ModNoises;
//import com.gaboj1.tcr.worldgen.structure.TCRStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.STRUCTURE, TCRStructures::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, TCRConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, TCRPlacedFeatures::bootstrap)
            .add(Registries.DENSITY_FUNCTION, ModDensityFunctions::bootstrap)
            .add(Registries.NOISE, ModNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap)
            .add(Registries.BIOME, TCRBiomes::boostrap)
            .add(Registries.LEVEL_STEM, TCRDimension::bootstrapStem)
            .add(Registries.DIMENSION_TYPE, TCRDimension::bootstrapType);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TheCasketOfReveriesMod.MOD_ID));
    }
}
