package com.gaboj1.tcr.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class TCRLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, TCRLoot.IMMUTABLE_LOOT_TABLES,List.of(
                new LootTableProvider.SubProviderEntry(TCRBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(TCRChestLootTables::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(TCREntityLootTables::new, LootContextParamSets.ENTITY)
        ));
    }
}
