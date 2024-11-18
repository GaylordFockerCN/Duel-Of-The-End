package com.p1nero.dote.network.packet;

import com.p1nero.dote.archive.DOTEArchiveManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 同步数据
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
        //服务端
        if(playerEntity instanceof ServerPlayer){
            DOTEArchiveManager.fromNbt(serverData);
            return;
        }
        //客户端
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DOTEArchiveManager.fromNbt(serverData);
        }
    }
}
