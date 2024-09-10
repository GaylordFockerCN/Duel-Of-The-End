package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.item.TCRModItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {

    protected ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {

        add(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(TCRModItems.ELDER_STAFF.get()))));

        add(TCRModEntities.JELLY_CAT.get(), emptyLootTable());//TODO 加点残忍的掉落物
        add(TCRModEntities.SQUIRREL.get(), emptyLootTable());//TODO 加点残忍的掉落物
        add(TCRModEntities.CRAB.get(), emptyLootTable());//TODO 加点残忍的掉落物

        add(TCRModEntities.P1NERO.get(), emptyLootTable());

        add(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get(), emptyLootTable());
        add(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get(), emptyLootTable());
        add(TCRModEntities.PASTORAL_PLAIN_STATIONARY_VILLAGER.get(), emptyLootTable());

        add(TCRModEntities.VILLAGER2.get(), emptyLootTable());
        add(TCRModEntities.VILLAGER2_STATIONARY.get(), emptyLootTable());
        add(TCRModEntities.VILLAGER2_TALKABLE.get(), emptyLootTable());
        add(TCRModEntities.MIAO_YIN.get(), emptyLootTable());
        add(TCRModEntities.CANG_LAN.get(), emptyLootTable());
        add(TCRModEntities.ZHEN_YU.get(), emptyLootTable());
        add(TCRModEntities.DUAN_SHAN.get(), emptyLootTable());
        add(TCRModEntities.CUI_HUA.get(), emptyLootTable());
        add(TCRModEntities.YUN_YI.get(), emptyLootTable());
        add(TCRModEntities.YAN_XIN.get(), emptyLootTable());


        add(TCRModEntities.TIGER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 0))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        add(TCRModEntities.BOXER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 0))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        add(TCRModEntities.BIG_HAMMER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 0))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        add(TCRModEntities.SNOW_SWORDMAN.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 0))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        add(TCRModEntities.SWORD_CONTROLLER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 0))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        add(TCRModEntities.SECOND_BOSS.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 0))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        /*Biome1 start*/
        add(TCRModEntities.SMALL_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.HEART_OF_THE_SAPLING.get())))
        );

        add(TCRModEntities.MIDDLE_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.ESSENCE_OF_THE_ANCIENT_TREE.get())))
        );

        add(TCRModEntities.TREE_GUARDIAN.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.BARK_OF_THE_GUARDIAN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1, 2))
                                .add(LootItem.lootTableItem(TCRModItems.INTERMEDIATE_RESIN.get()).setWeight(1))
                                .add(LootItem.lootTableItem(TCRModItems.ADVANCED_RESIN.get()).setWeight(3)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.005F))
                                .add(LootItem.lootTableItem(TCRModItems.SUPER_RESIN.get()))
                                .add(LootItem.lootTableItem(TCRModItems.COPY_RESIN.get())))
        );

        add(TCRModEntities.SPRITE.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.STARLIT_DEWDROP.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRModItems.SPIRIT_WAND.get())))
        );

        add(TCRModEntities.YGGDRASIL.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.2F, 1))
                                .add(LootItem.lootTableItem(TCRModItems.TREE_DEMON_HORN.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.2F, 1))
                                .add(LootItem.lootTableItem(TCRModItems.TREE_DEMON_MASK.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.3F, 1))
                                .add(LootItem.lootTableItem(TCRModItems.TREE_DEMON_BRANCH.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.3F, 1))
                                .add(LootItem.lootTableItem(TCRModItems.TREE_DEMON_FRUIT.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1F, 1))
                                .add(LootItem.lootTableItem(TCRModItems.COPY_RESIN.get())))
        );

        add(TCRModEntities.TREE_CLAW.get(), emptyLootTable());
        /*Biome1 end*/

        add(TCRModEntities.SWORD.get(),emptyLootTable());

        add(TCRModEntities.RAIN_SCREEN_SWORD.get(),emptyLootTable());


    }

    public LootTable.Builder emptyLootTable() {
        return LootTable.lootTable();
    }

    public LootTable.Builder fromEntityLootTable(EntityType<?> parent) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(parent.getDefaultLootTable())));
    }

    private static LootTable.Builder sheepLootTableBuilderWithDrop(ItemLike wool) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(wool))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootTableReference.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return TCRModEntities.REGISTRY.getEntries().stream().map(RegistryObject::get);
    }
}