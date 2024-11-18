package com.p1nero.dote.event.listeners;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.client.gui.BossBarHandler;
import com.p1nero.dote.client.gui.CustomGuiHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, value = Dist.CLIENT)
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
        if(DOTEConfig.RENDER_CUSTOM_GUI.get()){
            CustomGuiHandler.renderCustomGui(guiGraphics);
        }
    }

}