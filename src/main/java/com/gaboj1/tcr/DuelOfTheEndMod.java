package com.gaboj1.tcr;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.TCRVillagers;
import com.gaboj1.tcr.item.TCRItemTabs;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.gaboj1.tcr.worldgen.structure.TCRStructurePlacementTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.io.File;
import java.util.Locale;

@Mod(DuelOfTheEndMod.MOD_ID)
public class DuelOfTheEndMod {
    public static final String MOD_ID = "duel_of_the_end";

    public static final String REGISTRY_NAMESPACE = "dote";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DuelOfTheEndMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TCRSounds.REGISTRY.register(bus);
        TCRItems.REGISTRY.register(bus);
        TCRBlocks.REGISTRY.register(bus);
        TCRBlockEntities.REGISTRY.register(bus);
        TCREntities.REGISTRY.register(bus);
        TCRItemTabs.REGISTRY.register(bus);
        TCREffects.REGISTRY.register(bus);
        TCRStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
        TCRVillagers.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerExtraStuff);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TCRConfig.CLIENT_SPEC);
    }

    public static MutableComponent getInfo(String key){
        return Component.translatable("info.the_casket_of_reveries."+key);
    }

    public static MutableComponent getInfo(String key, Object... objects){
        return Component.translatable("info.the_casket_of_reveries."+key, objects);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        TCRPacketHandler.register();
        event.enqueueWork(() -> {
        });
        try{
            File dir = FMLPaths.CONFIGDIR.get().resolve(DuelOfTheEndMod.MOD_ID).toFile();
            if(!dir.exists()){
                DuelOfTheEndMod.LOGGER.info("creating dir : "+dir.mkdirs());
            }
        }catch (Exception e){
            DuelOfTheEndMod.LOGGER.error("Failed to read map！",e);
        }


    }

    public void registerExtraStuff(RegisterEvent evt) {
        if (evt.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
//            Registry.register(BuiltInRegistries.BIOME_SOURCE, TheCasketOfReveriesMod.prefix("tcr_biomes"), ModBiomeProvider.TCR_CODEC);
            Registry.register(BuiltInRegistries.BIOME_SOURCE, DuelOfTheEndMod.prefix("dote_biomes"), TCRBiomeProvider.TCR_CODEC);

        }else if (evt.getRegistryKey().equals(Registries.CHUNK_GENERATOR)) {
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, DuelOfTheEndMod.prefix("structure_locating_wrapper"), TCRChunkGenerator.CODEC);
        }
    }


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return new ResourceLocation(REGISTRY_NAMESPACE, name.toLowerCase(Locale.ROOT));
    }

}
