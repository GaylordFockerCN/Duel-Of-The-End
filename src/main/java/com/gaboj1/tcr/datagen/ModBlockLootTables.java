package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.init.TCRModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get());

        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
        this.dropSelf(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
        this.dropSelf(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());

        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get(), block ->
                createLeavesDrops(block, TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_SAPLING.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return TCRModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
