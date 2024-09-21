package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.ClientHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * 同步传送石的id，方便渲染
 */
public record PortalBlockIdSyncPacket(BlockPos pos, int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(id);
    }

    public static PortalBlockIdSyncPacket decode(FriendlyByteBuf buf) {
        return new PortalBlockIdSyncPacket(buf.readBlockPos(), buf.readInt());
    }

    @Override
    public void execute(Player playerEntity) {
        ClientHelper.localPlayerDo((player -> {
            BlockEntity entity = player.level().getBlockEntity(pos);
            if(entity instanceof PortalBlockEntity portalBlockEntity){
                portalBlockEntity.setId(id);
            }
        }));
    }
}