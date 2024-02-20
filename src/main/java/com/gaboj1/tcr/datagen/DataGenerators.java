package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.datagen.lang.ModLangGenerator;
import com.gaboj1.tcr.datagen.loot.ModLootTableProvider;
import com.gaboj1.tcr.datagen.sound.SoundGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //Client
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new ModLangGenerator(output));
        generator.addProvider(event.includeClient(), new SoundGenerator(output, helper));

        //Server
        generator.addProvider(event.includeServer(), new ModRecipeGenerator(output));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(output));
        generator.addProvider(event.includeServer(), new ModAdvancementData(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new ModEntityTagGenerator(output, lookupProvider, helper));

        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), helper));

        DatapackBuiltinEntriesProvider datapackProvider = new ModWorldGenProvider(output, lookupProvider);
        CompletableFuture<HolderLookup.Provider> provider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), new ModBiomeTagGenerator(output, provider, helper));
        generator.addProvider(event.includeServer(), datapackProvider);
    }
}
