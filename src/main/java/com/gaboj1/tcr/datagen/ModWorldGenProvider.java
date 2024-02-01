package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.ModBiomeModifiers;
import com.gaboj1.tcr.worldgen.ModConfiguredFeatures;
import com.gaboj1.tcr.worldgen.ModPlacedFeatures;
import com.gaboj1.tcr.worldgen.biome.BiomeLayerStack;
import com.gaboj1.tcr.worldgen.biome.ModBiomes;
import com.gaboj1.tcr.worldgen.dimension.ModDensityFunctions;
import com.gaboj1.tcr.worldgen.dimension.ModDimension;
import com.gaboj1.tcr.worldgen.dimension.ModNoiseSettings;
import com.gaboj1.tcr.worldgen.dimension.ModNoises;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
//            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.DENSITY_FUNCTION, ModDensityFunctions::bootstrap)
            .add(Registries.NOISE, ModNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap)
            .add(BiomeLayerStack.BIOME_STACK_KEY, BiomeLayerStack::bootstrap)
            .add(Registries.BIOME, ModBiomes::boostrap)
            .add(Registries.LEVEL_STEM, ModDimension::bootstrapStem)
            .add(Registries.DIMENSION_TYPE, ModDimension::bootstrapType);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TheCasketOfReveriesMod.MOD_ID));
    }
}
