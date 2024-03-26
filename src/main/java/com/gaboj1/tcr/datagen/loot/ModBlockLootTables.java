package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.init.TCRModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get());

        //copy from VanillaBlockLoot (我真是天才~
        this.add(TCRModBlocks.PORTAL_BED.get(), (block) -> this.createSinglePropConditionTable(block, BedBlock.PART, BedPart.HEAD));

//        this.dropSelf(TCRModBlocks.PORTAL_BLOCK.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get());
        this.add(TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(), createPotFlowerItemTable(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get()));

        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
        this.dropSelf(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
        this.dropSelf(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());

        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get(), block ->
                createLeavesDrops(block, TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return TCRModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
