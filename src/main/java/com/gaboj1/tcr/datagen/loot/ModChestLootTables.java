package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.block.TCRModBlocks;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModChestLootTables implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {

        builder.accept(TCRLoot.ENTER_REALM_OF_THE_DREAM, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
//                        .add(LootItem.lootTableItem(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                )
        );

        builder.accept(TCRLoot.FLOWER_ALTAR, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT))
                )
        );

    }
}
