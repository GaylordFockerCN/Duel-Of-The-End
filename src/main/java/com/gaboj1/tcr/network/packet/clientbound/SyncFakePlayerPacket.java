package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.entity.TCRFakePlayer;
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
public record SyncFakePlayerPacket(int realPlayerId, int fakePlayerId) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(realPlayerId);
        buf.writeInt(fakePlayerId);
    }

    public static SyncFakePlayerPacket decode(FriendlyByteBuf buf) {
        return new SyncFakePlayerPacket(buf.readInt(),buf.readInt());
    }

    @Override
    public void execute(@Nullable Player player) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> ClientHelper.localPlayerDo((localPlayer)->{
            Player realPlayer = ((Player) localPlayer.level().getEntity(realPlayerId));
            TCRFakePlayer fakePlayer = ((TCRFakePlayer) localPlayer.level().getEntity(fakePlayerId));
            if(realPlayer == null || fakePlayer == null){
                return;
            }
            fakePlayer.setRealPlayer(realPlayer);
        }));
    }
}