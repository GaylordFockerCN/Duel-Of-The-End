package com.p1nero.dote.datagen.loot;

import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class DOTEEntityLootTables extends EntityLootSubProvider {

    protected DOTEEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(DOTEEntities.SENBAI_DEVIL.get(),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 3))
                        .add(LootItem.lootTableItem(DOTEItems.NETHERITESS.get()))));
        add(DOTEEntities.GOLDEN_FLAME.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 3))
                        .add(LootItem.lootTableItem(DOTEItems.WITHERC.get()))));
        add(DOTEEntities.STAR_CHASER.get(), emptyLootTable());
        add(DOTEEntities.DOTE_PIGLIN.get(),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2, 5))
                        .add(LootItem.lootTableItem(DOTEItems.ADVENTURESPAR.get()))));
        add(DOTEEntities.DOTE_ZOMBIE.get(),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 3))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(DOTEItems.ADGRAIN.get()))));
        add(DOTEEntities.DOTE_ZOMBIE_2.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1, 3))
                                .add(LootItem.lootTableItem(DOTEItems.ADVENTURESPAR.get()))));
        add(DOTEEntities.THE_ARBITER_OF_RADIANCE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(DOTEItems.HOLY_RADIANCE_SEED.get()))));
        add(DOTEEntities.THE_PYROCLAS_OF_PURGATORY.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(DOTEItems.CORE_OF_HELL.get()))));
        add(DOTEEntities.THE_SHADOW_OF_THE_END.get(), emptyLootTable());
        add(DOTEEntities.GUIDE_NPC.get(), emptyLootTable());
        add(DOTEEntities.KNIGHT_COMMANDER.get(), emptyLootTable());
        add(DOTEEntities.SCARLET_HIGH_PRIEST.get(), emptyLootTable());
        add(DOTEEntities.BLACK_HOLE.get(), emptyLootTable());
        add(DOTEEntities.FLAME_CIRCLE.get(), emptyLootTable());
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
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return DOTEEntities.REGISTRY.getEntries().stream().map(RegistryObject::get);
    }
}