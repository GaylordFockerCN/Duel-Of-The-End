package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 发给客户端，广播任务完成，用于一些没有玩家对象的地方。
 */
public record BroadcastMessagePacket(Component message, boolean actionBar) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(message);
        buf.writeBoolean(actionBar);
    }

    public static BroadcastMessagePacket decode(FriendlyByteBuf buf) {
        return new BroadcastMessagePacket(buf.readComponent(), buf.readBoolean());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        Player player = Minecraft.getInstance().player;
        if(player != null && Minecraft.getInstance().level != null && TCRConfig.BROADCAST_DIALOG.get()){
            player.displayClientMessage(message, false);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1, 1);
        }
    }
}
