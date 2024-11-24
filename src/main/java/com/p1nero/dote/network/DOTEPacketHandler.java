package com.p1nero.dote.network;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.network.packet.*;
import com.p1nero.dote.network.packet.serverbound.*;
import com.p1nero.dote.network.packet.clientbound.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class DOTEPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DuelOfTheEndMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    private static int index;

    public static synchronized void register() {
        // 发给客户端
        register(NPCDialoguePacket.class, NPCDialoguePacket::decode);
        register(PersistentBoolDataSyncPacket.class, PersistentBoolDataSyncPacket::decode);
        register(PersistentDoubleDataSyncPacket.class, PersistentDoubleDataSyncPacket::decode);
        register(PersistentStringDataSyncPacket.class, PersistentStringDataSyncPacket::decode);
        register(SyncUuidPacket.class, SyncUuidPacket::decode);
        register(BroadcastMessagePacket.class, BroadcastMessagePacket::decode);
        register(OpenEndScreenPacket.class, OpenEndScreenPacket::decode);

        // 发给服务端
        register(NpcPlayerInteractPacket.class, NpcPlayerInteractPacket::decode);
        register(AddDialogPacket.class, AddDialogPacket::decode);

        //双端
        register(SyncArchivePacket.class, SyncArchivePacket::decode);

    }

    private static <MSG extends BasePacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
