package com.gaboj1.tcr.datagen.tags;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TCRBlockTagGenerator extends BlockTagsProvider {
    public TCRBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.LOGS)
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get())
                .add(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get())
                .add(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get())
                .add(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());

        this.tag(BlockTags.FENCES)
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(TCRBlocks.ORICHALCUM_ORE.get()).addTag(Tags.Blocks.ORES);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TCRBlocks.ORICHALCUM_ORE.get());

    }
}