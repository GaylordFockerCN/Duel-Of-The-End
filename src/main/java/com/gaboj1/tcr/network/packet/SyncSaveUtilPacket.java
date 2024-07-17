package com.gaboj1.tcr.network.packet;

import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 发给服务端，同步对话记录，并向附近玩家广播对话
 * 客户端发就是请求同步
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
        //客户端
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            SaveUtil.fromNbt(serverData);
            SaveUtil.setAlreadyInit();
        }
        //服务端
        if(playerEntity instanceof ServerPlayer serverPlayer){
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new SyncSaveUtilPacket(SaveUtil.toNbt()), serverPlayer);
        }
    }
}
