package com.gaboj1.tcr.network.packet;

import com.gaboj1.tcr.entity.NpcDialogue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record PastoralPlainVillagerElderDialoguePacket(int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
    }

    public static PastoralPlainVillagerElderDialoguePacket decode(FriendlyByteBuf buf) {
        int queenID = buf.readInt();
        return new PastoralPlainVillagerElderDialoguePacket(queenID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.id()) instanceof NpcDialogue elder) {
                elder.openDialogueScreen();
            }
        }
    }
}