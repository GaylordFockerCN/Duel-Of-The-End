package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.item.TCRItems;
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

public class TCRBlockLootTables extends BlockLootSubProvider {
    public TCRBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(TCRBlocks.BETTER_STRUCTURE_BLOCK.get());

        //copy from VanillaBlockLoot (我真是天才~
        this.add(TCRBlocks.PORTAL_BED.get(), (block) -> this.createSinglePropConditionTable(block, BedBlock.PART, BedPart.HEAD));
        this.add(TCRBlocks.ORICHALCUM_ORE.get(), (block) -> this.createCopperLikeOreDrops(block, TCRItems.RAW_ORICHALCUM.get(), 1, 1));
        this.dropSelf(TCRBlocks.ORICHALCUM_BLOCK.get());

        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get());
        this.add(TCRBlocks.CATNIP.get(), (block) -> this.createCopperLikeOreDrops(block, TCRItems.CATNIP.get(), 1, 3));
        this.add(TCRBlocks.BLUE_MUSHROOM.get(), (block) -> this.createCopperLikeOreDrops(block, TCRItems.BLUE_MUSHROOM.get(), 1, 3));
        this.dropSelf(TCRBlocks.THIRST_BLOOD_ROSE.get());
        this.dropSelf(TCRBlocks.LAZY_ROSE.get());
        this.dropSelf(TCRBlocks.MELANCHOLIC_ROSE.get());
        this.dropSelf(TCRBlocks.WITHERED_ROSE.get());
        this.add(TCRBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(), createPotFlowerItemTable(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get()));
        this.add(TCRBlocks.POTTED_CATNIP.get(), createPotFlowerItemTable(TCRItems.CATNIP.get()));
        this.add(TCRBlocks.POTTED_BLUE_MUSHROOM.get(), createPotFlowerItemTable(TCRItems.BLUE_MUSHROOM.get()));
        this.add(TCRBlocks.POTTED_THIRST_BLOOD_ROSE.get(), createPotFlowerItemTable(TCRBlocks.THIRST_BLOOD_ROSE.get()));
        this.add(TCRBlocks.POTTED_LAZY_ROSE.get(), createPotFlowerItemTable(TCRBlocks.LAZY_ROSE.get()));
        this.add(TCRBlocks.POTTED_MELANCHOLIC_ROSE.get(), createPotFlowerItemTable(TCRBlocks.MELANCHOLIC_ROSE.get()));
        this.add(TCRBlocks.POTTED_WITHERED_ROSE.get(), createPotFlowerItemTable(TCRBlocks.WITHERED_ROSE.get()));


        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
        this.dropSelf(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
        this.dropSelf(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());

        this.add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get(), block ->
                createLeavesDrops(block, TCRBlocks.DENSE_FOREST_SPIRIT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_SAPLING.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_VINE.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get());
        this.dropSelf(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_TRAPDOOR.get());

        this.add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get(),
                block -> createSlabItemTable(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get()));
        this.add(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get(),
                block -> createDoorTable(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get()));

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
        return TCRBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
