package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class ServerStartListener {

    /**
     * 获取存档名字，用于二次读取地图时用。
     */
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event){
        TCRBiomeProvider.worldName = event.getServer().getWorldData().getLevelName();
    }

}
