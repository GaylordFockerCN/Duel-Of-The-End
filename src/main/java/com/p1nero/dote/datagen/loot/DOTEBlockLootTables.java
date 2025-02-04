package com.p1nero.dote.datagen.loot;

import com.p1nero.dote.block.DOTEBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DOTEBlockLootTables extends BlockLootSubProvider {
    public DOTEBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(DOTEBlocks.BETTER_STRUCTURE_BLOCK.get());
        this.dropSelf(DOTEBlocks.SENBAI_SPAWNER.get());
        this.dropSelf(DOTEBlocks.GOLDEN_FLAME_SPAWNER.get());
        this.dropSelf(DOTEBlocks.TAR_SPAWNER.get());
        this.dropSelf(DOTEBlocks.TPP_SPAWNER.get());
        this.dropSelf(DOTEBlocks.TSE_SPAWNER.get());
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
        return DOTEBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
