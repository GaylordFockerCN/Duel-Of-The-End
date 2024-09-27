package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
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

public class TCREntityLootTables extends EntityLootSubProvider {

    protected TCREntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {

        add(TCREntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(TCRItems.ELDER_STAFF.get()))));

        add(TCREntities.JELLY_CAT.get(), emptyLootTable());//TODO 加点残忍的掉落物
        add(TCREntities.SQUIRREL.get(), emptyLootTable());//TODO 加点残忍的掉落物
        add(TCREntities.CRAB.get(), emptyLootTable());//TODO 加点残忍的掉落物

        add(TCREntities.P1NERO.get(), emptyLootTable());
        add(TCREntities.FAKE_PLAYER.get(), emptyLootTable());

        add(TCREntities.PASTORAL_PLAIN_VILLAGER.get(), emptyLootTable());
        add(TCREntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get(), emptyLootTable());
        add(TCREntities.PASTORAL_PLAIN_STATIONARY_VILLAGER.get(), emptyLootTable());

        add(TCREntities.VILLAGER2.get(), emptyLootTable());
        add(TCREntities.VILLAGER2_STATIONARY.get(), emptyLootTable());
        add(TCREntities.VILLAGER2_TALKABLE.get(), emptyLootTable());
        add(TCREntities.MIAO_YIN.get(), emptyLootTable());
        add(TCREntities.SHANG_REN.get(), emptyLootTable());
        add(TCREntities.WANDERER.get(), emptyLootTable());
        add(TCREntities.RECEPTIONIST.get(), emptyLootTable());
        add(TCREntities.TRIAL_MASTER.get(), emptyLootTable());
        add(TCREntities.CANG_LAN.get(), emptyLootTable());
        add(TCREntities.ZHEN_YU.get(), emptyLootTable());
        add(TCREntities.DUAN_SHAN.get(), emptyLootTable());
        add(TCREntities.CUI_HUA.get(), emptyLootTable());
        add(TCREntities.YUN_YI.get(), emptyLootTable());
        add(TCREntities.YAN_XIN.get(), emptyLootTable());
        add(TCREntities.SECOND_BOSS.get(), emptyLootTable());

        add(TCREntities.SWORD.get(),emptyLootTable());
        add(TCREntities.RAIN_SCREEN_SWORD.get(),emptyLootTable());
        add(TCREntities.RAIN_SCREEN_SWORD_FOR_BOSS2.get(),emptyLootTable());

        /*Biome1 start*/
        add(TCREntities.SMALL_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.HEART_OF_THE_SAPLING.get())))
        );

        add(TCREntities.MIDDLE_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.BASIC_RESIN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get())))
        );

        add(TCREntities.TREE_GUARDIAN.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.BARK_OF_THE_GUARDIAN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.INTERMEDIATE_RESIN.get()).setWeight(1))
                                .add(LootItem.lootTableItem(TCRItems.ADVANCED_RESIN.get()).setWeight(2)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.005F))
                                .add(LootItem.lootTableItem(TCRItems.SUPER_RESIN.get()))
                                .add(LootItem.lootTableItem(TCRItems.COPY_RESIN.get())))
        );

        add(TCREntities.HORRIBLE_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.BASIC_RESIN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get())))
        );

        add(TCREntities.WIND_FEATHER_FALCON.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.BASIC_RESIN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get())))
        );



        add(TCREntities.SPRITE.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.STARLIT_DEWDROP.get())))
        );

        add(TCREntities.YGGDRASIL.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.2F, 1))
                                .add(LootItem.lootTableItem(TCRItems.TREE_DEMON_HORN.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.2F, 1))
                                .add(LootItem.lootTableItem(TCRItems.TREE_DEMON_MASK.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.3F, 1))
                                .add(LootItem.lootTableItem(TCRItems.TREE_DEMON_BRANCH.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.3F, 1))
                                .add(LootItem.lootTableItem(TCRItems.TREE_DEMON_FRUIT.get())))

                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1F, 1))
                                .add(LootItem.lootTableItem(TCRItems.COPY_RESIN.get())))
        );

        add(TCREntities.TREE_CLAW.get(), emptyLootTable());
        /*Biome1 end*/

        //Biome2 start
        add(TCREntities.TIGER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 1))
                                .add(LootItem.lootTableItem(TCRItems.TIGER_SOUL_ICE.get()).setWeight(1))
                                .add(LootItem.lootTableItem(TCRItems.ICE_TIGER_CLAW.get()).setWeight(2)))
        );

        add(TCREntities.BOXER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.BRAWLER_GLOVES.get())))
        );

        add(TCREntities.BIG_HAMMER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 1))
                                .add(LootItem.lootTableItem(TCRItems.HAMMER_BEARER_FRAGMENT.get())))
        );

        add(TCREntities.SNOW_SWORDMAN.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(Items.SNOWBALL)))
        );

        add(TCREntities.SWORD_CONTROLLER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(TCRItems.SWORDMASTER_TALISMAN.get())))
        );
        //Biome2 end


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
        return TCREntities.REGISTRY.getEntries().stream().map(RegistryObject::get);
    }
}