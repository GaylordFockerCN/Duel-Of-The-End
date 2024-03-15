package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.init.TCRModItems;
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

@SuppressWarnings("deprecation")
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
        add(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get(), emptyLootTable());
        add(TCRModEntities.PASTORAL_PLAIN_VILLAGER1.get(), emptyLootTable());
        add(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get(), emptyLootTable());

        add(TCRModEntities.SMALL_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 4))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
        );

        add(TCRModEntities.MIDDLE_TREE_MONSTER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.BASIC_RESIN.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.INTERMEDIATE_RESIN.get())))
        );

        add(TCRModEntities.TREE_GUARDIAN.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 2))
                                .add(LootItem.lootTableItem(TCRModItems.INTERMEDIATE_RESIN.get())))
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

        add(TCRModEntities.YGGDRASIL.get(),
                LootTable.lootTable()
                        .withPool((LootPool.lootPool()
                                .setRolls((UniformGenerator.between(0,2)))
                                .add(LootItem.lootTableItem(TCRModItems.TREE_DEMON_HORN.get())))));
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