package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.client.gui.BossBarHandler;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * 同步客户端的uuid
 */
public record SyncUuidPacket(UUID serverUuid, int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(serverUuid);
        buf.writeInt(id);
    }

    public static SyncUuidPacket decode(FriendlyByteBuf buf) {
        return new SyncUuidPacket(buf.readUUID(), buf.readInt());
    }

    @Override
    public void execute(@Nullable Player player) {
        BossBarHandler.BOSSES.put(serverUuid, id);
    }
}