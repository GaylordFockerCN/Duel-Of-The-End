package com.gaboj1.tcr;

import com.gaboj1.tcr.block.DOTEBlockEntities;
import com.gaboj1.tcr.block.DOTEBlocks;
import com.gaboj1.tcr.client.DOTESounds;
import com.gaboj1.tcr.effect.DOTEEffects;
import com.gaboj1.tcr.entity.DOTEEntities;
import com.gaboj1.tcr.entity.DOTEVillagers;
import com.gaboj1.tcr.item.DOTEItemTabs;
import com.gaboj1.tcr.item.DOTEItems;
import com.gaboj1.tcr.network.DOTEPacketHandler;
import com.gaboj1.tcr.worldgen.biome.DOTEBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.DOTEChunkGenerator;
import com.gaboj1.tcr.worldgen.structure.DOTEStructurePlacementTypes;
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

import java.io.File;
import java.util.Locale;

@Mod(DuelOfTheEndMod.MOD_ID)
public class DuelOfTheEndMod {
    public static final String MOD_ID = "duel_of_the_end";

    public static final String REGISTRY_NAMESPACE = "dote";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DuelOfTheEndMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        DOTESounds.REGISTRY.register(bus);
        DOTEItems.REGISTRY.register(bus);
        DOTEBlocks.REGISTRY.register(bus);
        DOTEBlockEntities.REGISTRY.register(bus);
        DOTEEntities.REGISTRY.register(bus);
        DOTEItemTabs.REGISTRY.register(bus);
        DOTEEffects.REGISTRY.register(bus);
        DOTEStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
        DOTEVillagers.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerExtraStuff);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DOTEConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DOTEConfig.CLIENT_SPEC);
    }

    public static MutableComponent getInfo(String key){
        return Component.translatable("info.the_casket_of_reveries."+key);
    }

    public static MutableComponent getInfo(String key, Object... objects){
        return Component.translatable("info.the_casket_of_reveries."+key, objects);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        DOTEPacketHandler.register();
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
            Registry.register(BuiltInRegistries.BIOME_SOURCE, DuelOfTheEndMod.prefix("dote_biomes"), DOTEBiomeProvider.TCR_CODEC);

        }else if (evt.getRegistryKey().equals(Registries.CHUNK_GENERATOR)) {
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, DuelOfTheEndMod.prefix("structure_locating_wrapper"), DOTEChunkGenerator.CODEC);
        }
    }


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return new ResourceLocation(REGISTRY_NAMESPACE, name.toLowerCase(Locale.ROOT));
    }

}
