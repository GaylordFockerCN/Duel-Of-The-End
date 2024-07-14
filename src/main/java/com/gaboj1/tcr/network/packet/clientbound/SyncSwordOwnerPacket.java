package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.entity.custom.sword.SwordEntity;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.ClientHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

/**
 * 同步客户端的剑
 */
public record SyncSwordOwnerPacket(int ownerId, int swordId) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(ownerId);
        buf.writeInt(swordId);
    }

    public static SyncSwordOwnerPacket decode(FriendlyByteBuf buf) {
        return new SyncSwordOwnerPacket(buf.readInt(),buf.readInt());
    }

    @Override
    public void execute(@Nullable Player player) {

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> ClientHelper.localPlayerDo((localPlayer)->{
            Player owner = ((Player) localPlayer.level().getEntity(ownerId));
            SwordEntity sword = ((SwordEntity) localPlayer.level().getEntity(swordId));
            if(owner == null || sword == null){
                return;
            }
            sword.setRider(owner);
        }));

    }
}