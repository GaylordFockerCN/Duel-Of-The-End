package com.p1nero.dote.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class DOTELootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, DOTELoot.IMMUTABLE_LOOT_TABLES,List.of(
                new LootTableProvider.SubProviderEntry(DOTEBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(DOTEChestLootTables::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(DOTEEntityLootTables::new, LootContextParamSets.ENTITY)
        ));
    }
}
