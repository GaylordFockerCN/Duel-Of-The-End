package com.gaboj1.tcr.network.packet;

import com.gaboj1.tcr.entity.NpcDialogue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record NPCDialoguePacket(int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
    }

    public static NPCDialoguePacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new NPCDialoguePacket(id);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.id()) instanceof NpcDialogue npc) {
                npc.openDialogueScreen();
            }
        }
    }
}