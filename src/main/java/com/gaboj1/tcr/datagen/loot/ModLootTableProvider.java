package com.gaboj1.tcr.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, TCRLoot.IMMUTABLE_LOOT_TABLES,List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModChestLootTables::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY)
        ));
    }
}
