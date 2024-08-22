package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.item.TCRModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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

        builder.accept(TCRLoot.VILLAGE1USUAL, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.CATNIP.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAM_TA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.BEER.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.BLUE_BANANA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.GREEN_BANANA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.PINE_CONE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.JUICE_TEA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DRINK1.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DRINK2.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.COOKIE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
        );

        builder.accept(TCRLoot.VILLAGE1SPECIAL1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN_PLUS.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.RED_WINE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ELDER_CAKE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 5.0F))
                        .add(LootItem.lootTableItem(TCRModItems.GREEN_BANANA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.SPYGLASS)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 8.0F)))
                )

        );

        builder.accept(TCRLoot.VILLAGE1SPECIAL2, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ORICHALCUM_BOOTS.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ORICHALCUM_LEGGINGS.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ORICHALCUM_CHESTPLATE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ORICHALCUM_HELMET.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.RAW_ORICHALCUM.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 8.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 3.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DESERT_EAGLE_AMMO.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 6.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ORICHALCUM.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                )
        );

        builder.accept(TCRLoot.TREE_HOUSE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.INTERMEDIATE_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ADVANCED_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.SUPER_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.EDEN_APPLE.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.BLUE_BANANA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.GREEN_BANANA.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.APPLE)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 5.0F))
                        .add(LootItem.lootTableItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
        );

        builder.accept(TCRLoot.BOSS1_TOWER_TOP, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN_PLUS.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.ADVANCED_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.SUPER_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
        );

        builder.accept(TCRLoot.BOSS1TOWER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.DREAMSCAPE_COIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(TCRModItems.INTERMEDIATE_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 5.0F))
                        .add(LootItem.lootTableItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.SUPER_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                )
        );

        builder.accept(TCRLoot.BOSS1TREETOP, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.COPY_RESIN.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(TCRModItems.SUPER_RESIN.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                )
        );

        builder.accept(TCRLoot.ALTAR1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(TCRModItems.WITHERING_TOUCH.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT))
                )
        );

    }
}
