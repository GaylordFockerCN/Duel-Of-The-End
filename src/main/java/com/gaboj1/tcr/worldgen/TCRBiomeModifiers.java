package com.gaboj1.tcr.worldgen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class TCRBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ORICHALCUM_ORE_KEY = registerKey("add_orichalcum_ore_key");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        context.register(ADD_ORICHALCUM_ORE_KEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TCRBiomeTags.IS_TCR),
                HolderSet.direct(placedFeatures.getOrThrow(TCRPlacedFeatures.ORICHALCUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name));
    }
}