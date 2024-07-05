package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 发给服务端，同步对话记录，并向附近玩家广播对话
 */
public record SyncSaveUtilPacket(CompoundTag serverData) implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(serverData);
    }

    public static SyncSaveUtilPacket decode(FriendlyByteBuf buf) {
        return new SyncSaveUtilPacket(buf.readNbt());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            SaveUtil.fromNbt(serverData);
        }
    }
}
