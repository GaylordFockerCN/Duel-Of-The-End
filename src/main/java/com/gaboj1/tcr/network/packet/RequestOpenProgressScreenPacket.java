package com.gaboj1.tcr.network.packet;

import com.gaboj1.tcr.gui.screen.custom.GameProgressScreen;
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
 * 如果是客户端发的话就是请求
 */
public record RequestOpenProgressScreenPacket(CompoundTag serverData) implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(serverData);
    }

    public static RequestOpenProgressScreenPacket decode(FriendlyByteBuf buf) {
        return new RequestOpenProgressScreenPacket(buf.readNbt());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        //是客户端就用服务端的数据打开窗口
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            SaveUtil.fromNbt(serverData);
            Minecraft.getInstance().setScreen(new GameProgressScreen());
        }
        //是服务端就通知客户端打开窗口并把数据传过去
        if(playerEntity instanceof ServerPlayer serverPlayer){
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new RequestOpenProgressScreenPacket(SaveUtil.toNbt()), serverPlayer);
        }
    }
}
