package com.gaboj1.tcr.network;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.network.packet.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class TCRPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    private static int index;

    public static synchronized void register() {
        // CLIENTBOUND
        register(NPCDialoguePacket.class, NPCDialoguePacket::decode);
        register(PortalBlockScreenPacket.class, PortalBlockScreenPacket::decode);


        // SERVERBOUND
        register(NpcPlayerInteractPacket.class, NpcPlayerInteractPacket::decode);
        register(PortalBlockTeleportPacket.class, PortalBlockTeleportPacket::decode);

        // BOTH

    }

    private static <MSG extends BasePacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
