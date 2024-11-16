package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.datagen.lang.TCRLangGenerator;
import com.gaboj1.tcr.datagen.loot.TCRLootTableProvider;
import com.gaboj1.tcr.datagen.sound.TCRSoundGenerator;
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
        generator.addProvider(event.includeClient(), new TCRBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new TCRItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new TCRLangGenerator(output));
        generator.addProvider(event.includeClient(), new TCRSoundGenerator(output, helper));

        //Server
        generator.addProvider(event.includeServer(), new TCRRecipeGenerator(output));
        generator.addProvider(event.includeServer(), TCRLootTableProvider.create(output));
        generator.addProvider(event.includeServer(), new TCRAdvancementData(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new TCREntityTagGenerator(output, lookupProvider, helper));

        TCRBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new TCRBlockTagGenerator(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new TCRItemTagGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new TCRPoiTypeTagsProvider(output, lookupProvider, helper));

        DatapackBuiltinEntriesProvider datapackProvider = new TCRWorldGenProvider(output, lookupProvider);
        CompletableFuture<HolderLookup.Provider> provider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), new TCRBiomeTagGenerator(output, provider, helper));
        generator.addProvider(event.includeServer(), datapackProvider);
    }
}
