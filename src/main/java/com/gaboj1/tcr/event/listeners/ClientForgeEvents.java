package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.gui.BossBarHandler;
import com.gaboj1.tcr.client.gui.CustomGuiHandler;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
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

    /**
     * 画任务的UI
     */
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if(TCRConfig.RENDER_CUSTOM_GUI.get()){
            CustomGuiHandler.renderCustomGui(guiGraphics);
        }
    }

}