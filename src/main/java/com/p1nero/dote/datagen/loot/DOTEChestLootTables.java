package com.p1nero.dote.datagen.loot;

import com.p1nero.dote.item.DOTEItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.function.BiConsumer;

public class DOTEChestLootTables implements LootTableSubProvider {
    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(DOTELoot.CHURCH1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.ADGRAIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 7.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.TIESTONES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.TIESTONEH.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.TIESTONEC.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.TIESTONEL.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 5.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 5.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 5.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 5.0F)))
                )
        );
        builder.accept(DOTELoot.CHURCH2, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.ADGRAIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 10.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 8.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 8.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.WKNIGHT_INGOT.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                )
        );
        builder.accept(DOTELoot.CHURCH_RARE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(DOTEItems.ROT_GREATSWORD.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.NETHERROT_INGOT.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.WKNIGHT_INGOT.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                )
        );

        builder.accept(DOTELoot.WATCHTOWER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(DOTEItems.NETHERROT_INGOT.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(DOTEItems.WKNIGHT_INGOT.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(EpicFightItems.UCHIGATANA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(EpicFightItems.NETHERITE_GREATSWORD.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(EpicFightItems.NETHERITE_LONGSWORD.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(EpicFightItems.NETHERITE_SPEAR.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(EpicFightItems.NETHERITE_TACHI.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(EpicFightItems.NETHERITE_DAGGER.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(DOTEItems.ADVENTURESPAR.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(DOTEItems.ADVENTURESPAR.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.NETHERITE_INGOT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F)))
                )
        );

    }
}
