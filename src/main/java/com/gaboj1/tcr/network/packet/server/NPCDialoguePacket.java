package com.gaboj1.tcr.network.packet.server;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * 不带SkinID的Packet
 */
public record NPCDialoguePacket(int id, CompoundTag tag) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
        buf.writeNbt(this.tag());
    }

    public static NPCDialoguePacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        CompoundTag tag = buf.readNbt();
        return new NPCDialoguePacket(id,tag);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.id()) instanceof NpcDialogue npc) {
                npc.openDialogueScreen(this.tag());
            }
        }
    }
}