package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.datagen.lang.DOTELangGenerator;
import com.gaboj1.tcr.datagen.loot.DOTELootTableProvider;
import com.gaboj1.tcr.datagen.sound.DOTESoundGenerator;
import com.gaboj1.tcr.datagen.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //Client
        generator.addProvider(event.includeClient(), new DOTEBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new DOTEItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new DOTELangGenerator(output));
        generator.addProvider(event.includeClient(), new DOTESoundGenerator(output, helper));

        //Server
        generator.addProvider(event.includeServer(), new DOTERecipeGenerator(output));
        generator.addProvider(event.includeServer(), DOTELootTableProvider.create(output));
        generator.addProvider(event.includeServer(), new DOTEAdvancementData(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new DOTEEntityTagGenerator(output, lookupProvider, helper));

        DOTEBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new DOTEBlockTagGenerator(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new DOTEItemTagGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new DOTEPoiTypeTagsProvider(output, lookupProvider, helper));

        DatapackBuiltinEntriesProvider datapackProvider = new DOTEWorldGenProvider(output, lookupProvider);
        CompletableFuture<HolderLookup.Provider> provider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), new DOTEBiomeTagGenerator(output, provider, helper));
        generator.addProvider(event.includeServer(), datapackProvider);
    }
}
