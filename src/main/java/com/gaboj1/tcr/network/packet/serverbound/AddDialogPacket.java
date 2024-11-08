package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 发给服务端，同步对话记录，并向附近玩家广播对话
 */
public record AddDialogPacket(Component name, Component content, boolean broadcast) implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(name);
        buf.writeComponent(content);
        buf.writeBoolean(broadcast);
    }

    public static AddDialogPacket decode(FriendlyByteBuf buf) {
        return new AddDialogPacket(buf.readComponent(), buf.readComponent(), buf.readBoolean());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        TCRArchiveManager.addDialog(name, content);
        if(playerEntity != null && broadcast && TCRConfig.BROADCAST_DIALOG.get()){
            for(Player player : playerEntity.level().players()){
                if(player != playerEntity && player.getPosition(1.0f).distanceTo(playerEntity.getPosition(1.0f)) < TCRConfig.BROADCAST_DISTANCE.get()){
                    player.displayClientMessage(Component.literal("[").append(name.copy().withStyle(ChatFormatting.YELLOW)).append(Component.literal("]:")), false);
                    player.displayClientMessage(content, false);
                }
            }
        }
    }
}
