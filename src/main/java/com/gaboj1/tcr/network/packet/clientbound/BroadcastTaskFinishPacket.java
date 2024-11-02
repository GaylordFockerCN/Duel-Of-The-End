package com.gaboj1.tcr.network.packet.clientbound;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 发给客户端，广播任务完成
 */
public record BroadcastTaskFinishPacket(Component name) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(name);
    }

    public static BroadcastTaskFinishPacket decode(FriendlyByteBuf buf) {
        return new BroadcastTaskFinishPacket(buf.readComponent());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && TCRConfig.BROADCAST_DIALOG.get()){
            Minecraft.getInstance().player.displayClientMessage(TheCasketOfReveriesMod.getInfo("task_finish0").append(name.copy().withStyle(ChatFormatting.RED)).append(TheCasketOfReveriesMod.getInfo("task_finish1")), false);
        }
    }
}
