package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.item.custom.weapon.GunCommon;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record GunReloadPacket() implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    public static GunReloadPacket decode(FriendlyByteBuf buf) {
        return new GunReloadPacket();
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if(playerEntity != null){
            GunCommon.reload(playerEntity);
        }
    }
}
