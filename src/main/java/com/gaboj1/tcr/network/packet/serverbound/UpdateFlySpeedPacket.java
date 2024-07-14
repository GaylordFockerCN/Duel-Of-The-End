package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * 按键操作属于客户端的东西，所以得发包通知服务端改变速度。
 */
public record UpdateFlySpeedPacket (int slotID, double newSpeed) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.slotID());
        buf.writeDouble(this.newSpeed());
    }

    public static UpdateFlySpeedPacket decode(FriendlyByteBuf buf) {
        return new UpdateFlySpeedPacket(buf.readInt(), buf.readDouble());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null) {
            ItemStack sword = playerEntity.getInventory().getItem(slotID);
            sword.getOrCreateTag().putDouble("flySpeedScale", newSpeed);
        }
    }
}
