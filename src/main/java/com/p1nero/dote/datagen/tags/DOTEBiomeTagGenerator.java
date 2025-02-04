package com.p1nero.dote.datagen.tags;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.worldgen.biome.DOTEBiomeTags;
import com.p1nero.dote.worldgen.biome.DOTEBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DOTEBiomeTagGenerator extends BiomeTagsProvider {

    public DOTEBiomeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, DuelOfTheEndMod.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(DOTEBiomeTags.IN_DOTE).add(DOTEBiomes.M_BIOME, DOTEBiomes.P_BIOME, DOTEBiomes.AIR);
    }

    @Override
    public @NotNull String getName() {
        return "Duel Of The End Biome Tags";
    }
}