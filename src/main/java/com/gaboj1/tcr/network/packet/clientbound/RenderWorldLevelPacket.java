package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.network.packet.clientbound.clienthelp.HandlePackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record RenderWorldLevelPacket(int worldLevel) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf var1) {
        var1.writeInt(worldLevel);
    }

    public static RenderWorldLevelPacket decode(FriendlyByteBuf buf) {
        return new RenderWorldLevelPacket(buf.readInt());
    }

    @Override
    public void execute(Player var1) {
        HandlePackets.handleRenderWorldLevelPacket(worldLevel);
    }
}
