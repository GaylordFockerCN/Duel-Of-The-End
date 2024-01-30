package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.LOGS)
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get())
                .add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get())
                .add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
    }
}