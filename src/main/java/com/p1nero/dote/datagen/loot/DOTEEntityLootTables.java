package com.p1nero.dote.datagen.loot;

import com.p1nero.dote.entity.DOTEEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class DOTEEntityLootTables extends EntityLootSubProvider {

    protected DOTEEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {

        add(DOTEEntities.REAPER.get(), emptyLootTable());
        add(DOTEEntities.DARK_ADVANCE.get(), emptyLootTable());
        add(DOTEEntities.SAND_CAPTAIN_COFFIN.get(), emptyLootTable());
        add(DOTEEntities.SAND_CAPTAIN.get(), emptyLootTable());
        add(DOTEEntities.SENBAI_DEVIL.get(), emptyLootTable());
        add(DOTEEntities.SLAUGHTER_GENERAL.get(), emptyLootTable());

        add(DOTEEntities.GOLDEN_FLAME.get(), emptyLootTable());

        add(DOTEEntities.MS_ABYSS.get(), emptyLootTable());
        add(DOTEEntities.LIU_GUANG.get(), emptyLootTable());

        add(DOTEEntities.ABYSS_DWELLER.get(), emptyLootTable());

        add(DOTEEntities.FALLEN_JUDGE.get(), emptyLootTable());
        add(DOTEEntities.SAGE.get(), emptyLootTable());
        add(DOTEEntities.SHAO_QIN.get(), emptyLootTable());
        add(DOTEEntities.SINCER_WARRIOR.get(), emptyLootTable());
        add(DOTEEntities.THEBANCHENG.get(), emptyLootTable());
        add(DOTEEntities.THECOWCOWCOW7.get(), emptyLootTable());
        add(DOTEEntities.THEHOTSUMMER.get(), emptyLootTable());
        add(DOTEEntities.THESIXGOOGLE.get(), emptyLootTable());
        add(DOTEEntities.THESUNWUKONG.get(), emptyLootTable());
        add(DOTEEntities.THEZHAOZILONG.get(), emptyLootTable());
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