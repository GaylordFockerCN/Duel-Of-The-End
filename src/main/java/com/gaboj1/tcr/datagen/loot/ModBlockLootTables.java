package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.item.TCRModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

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
        this.add(TCRModBlocks.ORICHALCUM_ORE.get(), (block) -> this.createCopperLikeOreDrops(block, TCRModItems.RAW_ORICHALCUM.get(), 1, 1));

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
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get());
        this.dropSelf(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_TRAPDOOR.get());

        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get(),
                block -> createSlabItemTable(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get()));
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get(),
                block -> createDoorTable(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get()));

    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item, float min, float max) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return TCRModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
