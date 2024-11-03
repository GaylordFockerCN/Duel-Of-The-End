package com.gaboj1.tcr.network;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.network.packet.*;
import com.gaboj1.tcr.network.packet.serverbound.*;
import com.gaboj1.tcr.network.packet.clientbound.*;
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
        // 发给客户端
        register(NPCDialoguePacket.class, NPCDialoguePacket::decode);
        register(RenderWorldLevelPacket.class, RenderWorldLevelPacket::decode);
        register(PortalBlockScreenPacket.class, PortalBlockScreenPacket::decode);
        register(PersistentBoolDataSyncPacket.class, PersistentBoolDataSyncPacket::decode);
        register(PersistentDoubleDataSyncPacket.class, PersistentDoubleDataSyncPacket::decode);
        register(PersistentStringDataSyncPacket.class, PersistentStringDataSyncPacket::decode);
        register(AddVillagerParticlePacket.class, AddVillagerParticlePacket::decode);
        register(SyncSwordOwnerPacket.class, SyncSwordOwnerPacket::decode);
        register(SyncFakePlayerPacket.class, SyncFakePlayerPacket::decode);
        register(PortalBlockEntitySyncPacket.class, PortalBlockEntitySyncPacket::decode);
        register(SyncUuidPacket.class, SyncUuidPacket::decode);
        register(BroadcastMessagePacket.class, BroadcastMessagePacket::decode);

        // 发给服务端
        register(NpcPlayerInteractPacket.class, NpcPlayerInteractPacket::decode);
        register(PortalBlockTeleportPacket.class, PortalBlockTeleportPacket::decode);
        register(UpdateFlySpeedPacket.class, UpdateFlySpeedPacket::decode);
        register(AddDialogPacket.class, AddDialogPacket::decode);
        register(GunReloadPacket.class, GunReloadPacket::decode);
        register(ControlLlamaPacket.class, ControlLlamaPacket::decode);

        //双端
        register(SyncSaveUtilPacket.class, SyncSaveUtilPacket::decode);

    }

    private static <MSG extends BasePacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
