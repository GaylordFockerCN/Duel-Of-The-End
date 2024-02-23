package com.gaboj1.tcr.network;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.network.packet.PastoralPlainVillagerElderDialoguePacket;
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
         register(PastoralPlainVillagerElderDialoguePacket.class, PastoralPlainVillagerElderDialoguePacket::decode);


        // SERVERBOUND


        // BOTH

    }

    private static <MSG extends BasePacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
