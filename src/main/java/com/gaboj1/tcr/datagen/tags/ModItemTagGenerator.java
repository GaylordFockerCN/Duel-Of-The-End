package com.gaboj1.tcr.datagen.tags;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.item.TCRModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get().asItem())
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get().asItem())
                .add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get().asItem())
                .add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get().asItem());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(TCRModItems.ORICHALCUM_HELMET.get())
                .add(TCRModItems.ORICHALCUM_CHESTPLATE.get())
                .add(TCRModItems.ORICHALCUM_LEGGINGS.get())
                .add(TCRModItems.ORICHALCUM_BOOTS.get());
    }
}