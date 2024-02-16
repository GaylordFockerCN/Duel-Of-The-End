package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.datagen.loot.ModBlockLootTables;
import com.gaboj1.tcr.datagen.loot.ModChestLootTables;
import com.gaboj1.tcr.loot.TCRLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, TCRLoot.IMMUTABLE_LOOT_TABLES,List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModChestLootTables::new, LootContextParamSets.CHEST)
        ));
    }
}
