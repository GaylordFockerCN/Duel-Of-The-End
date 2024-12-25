package com.p1nero.dote;

import com.mojang.logging.LogUtils;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.DOTEBlocks;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.effect.DOTEEffects;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.DOTEVillagers;
import com.p1nero.dote.entity.ai.condition.DOTEConditions;
import com.p1nero.dote.gameasset.DOTELivingMotions;
import com.p1nero.dote.item.DOTEItemTabs;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.worldgen.biome.DOTEBiomeProvider;
import com.p1nero.dote.worldgen.dimension.DOTEChunkGenerator;
import com.p1nero.dote.worldgen.structure.DOTEStructurePlacementTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotion;

import java.io.File;
import java.nio.file.Path;
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
        bus.addListener(DuelOfTheEndMod::addPackFindersEvent);
        LivingMotion.ENUM_MANAGER.registerEnumCls(MOD_ID, DOTELivingMotions.class);
        DOTEConditions.CONDITIONS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DOTEConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DOTEConfig.CLIENT_SPEC);
    }

    public static void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(DuelOfTheEndMod.MOD_ID).getFile().findResource("packs/animation");
            try (PathPackResources pack = new PathPackResources(ModList.get().getModFileById(DuelOfTheEndMod.MOD_ID).getFile().getFileName() + ":" + resourcePath, resourcePath, false)){
                Pack.ResourcesSupplier resourcesSupplier = (string) -> pack;
                Pack.Info info = Pack.readPackInfo("animation", resourcesSupplier);
                if (info != null) {
                    event.addRepositorySource((source) ->
                            source.accept(Pack.create("animation", Component.translatable("pack.dote_animation.title"), false, resourcesSupplier, info, PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN)));
                }
            }
        }
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
