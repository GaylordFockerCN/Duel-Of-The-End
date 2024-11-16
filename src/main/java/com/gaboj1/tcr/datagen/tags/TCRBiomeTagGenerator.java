package com.gaboj1.tcr.datagen.tags;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.gaboj1.tcr.worldgen.biome.TCRBiomeTags.*;

public class TCRBiomeTagGenerator extends BiomeTagsProvider {


    public TCRBiomeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, DuelOfTheEndMod.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }

    @Override
    public @NotNull String getName() {
        return "Duel Of The End Tags";
    }
}