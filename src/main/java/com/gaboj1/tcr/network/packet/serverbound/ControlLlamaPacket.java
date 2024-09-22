package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/**
 * 发给服务端，同步对话记录，并向附近玩家广播对话
 */
public record ControlLlamaPacket(int llamaId, Vector3f viewVector) implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(llamaId);
        buf.writeVector3f(viewVector);
    }

    public static ControlLlamaPacket decode(FriendlyByteBuf buf) {
        return new ControlLlamaPacket(buf.readInt(), buf.readVector3f());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
       if(playerEntity instanceof ServerPlayer serverPlayer){
           if(serverPlayer.serverLevel().getEntity(llamaId) instanceof Llama llama){
               Vec3 pos = llama.position();
               Vec3 target = pos.add(viewVector.x, viewVector.y, viewVector.z);
               llama.getNavigation().moveTo(target.x, target.y, target.z, 4.0);
           }
       }
    }
}
