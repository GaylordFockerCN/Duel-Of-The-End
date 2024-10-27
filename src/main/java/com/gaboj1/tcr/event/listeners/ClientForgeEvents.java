package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.gui.BossBarHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if(BossBarHandler.renderBossBar(event.getGuiGraphics(), event.getBossEvent(), event.getX(), event.getY())){
            event.setCanceled(true);
        }
    }

}