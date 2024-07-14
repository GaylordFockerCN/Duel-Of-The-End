package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.network.packet.clientbound.clienthelp.HandlePortalBlockScreenPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
/**
 * 算是魔改了，为了让对话框能获取游戏进度数据（boss是否被击败），不得不把nbt标签传过去。因为客户端是无法使用ServerPlayer的，调用ServerPlayer对象会出错（试过了）
 */
public record PortalBlockScreenPacket(CompoundTag tag) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(this.tag());
    }

    public static PortalBlockScreenPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        return new PortalBlockScreenPacket(tag);
    }

    @Override
    public void execute(Player playerEntity) {
        HandlePortalBlockScreenPacket.handle(tag);
    }
}