package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.item.custom.DesertEagleItem;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record DesertEagleReloadPacket() implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    public static DesertEagleReloadPacket decode(FriendlyByteBuf buf) {
        return new DesertEagleReloadPacket();
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if(playerEntity != null){
            DesertEagleItem.reload(playerEntity);
        }
    }
}
