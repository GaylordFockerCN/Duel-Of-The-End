package com.gaboj1.tcr.network.packet;

import com.gaboj1.tcr.entity.custom.TreeGuardianEntity;
import com.gaboj1.tcr.entity.custom.villager.PastoralPlainVillagerElder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record PastoralPlainVillagerElderDialoguePacket(int queenID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.queenID());
    }

    public static PastoralPlainVillagerElderDialoguePacket decode(FriendlyByteBuf buf) {
        int queenID = buf.readInt();
        return new PastoralPlainVillagerElderDialoguePacket(queenID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.queenID()) instanceof PastoralPlainVillagerElder elder) {
                elder.openDialogueScreen();
            }
            //TODO 以下用来测试，记得删
            if (Minecraft.getInstance().level.getEntity(this.queenID()) instanceof TreeGuardianEntity elder) {
                elder.openDialogueScreen();
            }
        }
    }
}