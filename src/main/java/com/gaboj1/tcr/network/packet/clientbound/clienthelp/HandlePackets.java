package com.gaboj1.tcr.network.packet.clientbound.clienthelp;

import com.gaboj1.tcr.client.gui.screen.custom.PortalBlockScreen;
import com.gaboj1.tcr.event.listeners.GUIListener;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

public class HandlePackets {
    public static void handlePortalBlockScreenPacket(CompoundTag tag){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().setScreen(new PortalBlockScreen(tag));
        }
    }

    public static void handleRenderWorldLevelPacket(int worldLevel){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            GUIListener.drawLevel(worldLevel);
        }
    }

}
