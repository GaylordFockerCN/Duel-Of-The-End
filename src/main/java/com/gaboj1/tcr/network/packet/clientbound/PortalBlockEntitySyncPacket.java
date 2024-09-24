package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.ClientHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record PortalBlockEntitySyncPacket(BlockPos pos, boolean isActivated, int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(isActivated);
        buf.writeInt(id);
    }

    public static PortalBlockEntitySyncPacket decode(FriendlyByteBuf buf) {
        return new PortalBlockEntitySyncPacket(buf.readBlockPos(), buf.readBoolean(), buf.readInt());
    }

    @Override
    public void execute(Player playerEntity) {
        ClientHelper.localPlayerDo((player -> {
            if(player.level().getBlockEntity(pos) instanceof PortalBlockEntity entity){
                entity.setId(id);
                entity.setActivated(isActivated);
            }
        }));
    }
}