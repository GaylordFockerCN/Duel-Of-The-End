package com.p1nero.dote;

import com.mojang.logging.LogUtils;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.DOTEBlocks;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.effect.DOTEEffects;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.DOTEVillagers;
import com.p1nero.dote.item.DOTEItemTabs;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.network.DOTEPacketHandler;
import dev.xkmc.l2library.base.L2Registrate;
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
import org.slf4j.Logger;

import java.io.File;
import java.util.Locale;

@Mod(DuelOfTheEndMod.MOD_ID)
public class DuelOfTheEndMod {
    public static final String MOD_ID = "duel_of_the_end";

    public static final String REGISTRY_NAMESPACE = "dote";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final L2Registrate REGISTRATE = new L2Registrate(MOD_ID);

    public DuelOfTheEndMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        DOTESounds.REGISTRY.register(bus);
        DOTEItems.REGISTRY.register(bus);
        DOTEBlocks.REGISTRY.register(bus);
        DOTEBlockEntities.REGISTRY.register(bus);
        DOTEEntities.REGISTRY.register(bus);
        DOTEItemTabs.REGISTRY.register(bus);
        DOTEEffects.REGISTRY.register(bus);
        DOTEVillagers.register(bus);
        bus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        DOTEBlocks.register();

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


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return new ResourceLocation(REGISTRY_NAMESPACE, name.toLowerCase(Locale.ROOT));
    }

}
