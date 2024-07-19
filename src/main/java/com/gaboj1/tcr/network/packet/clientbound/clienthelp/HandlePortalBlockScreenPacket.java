package com.gaboj1.tcr.network.packet.clientbound.clienthelp;

import com.gaboj1.tcr.client.gui.screen.custom.PortalBlockScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

public class HandlePortalBlockScreenPacket {
    public static void handle(CompoundTag tag){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().setScreen(new PortalBlockScreen(tag));
        }
    }
}
