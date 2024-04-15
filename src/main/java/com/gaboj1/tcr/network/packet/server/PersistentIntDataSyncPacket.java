package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public record PersistentIntDataSyncPacket(String key, boolean isLocked, int value) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(key));
        buf.writeBoolean(isLocked);
        buf.writeInt(value);
    }

    public static PersistentIntDataSyncPacket decode(FriendlyByteBuf buf) {
        String key = buf.readComponent().getString();
        boolean isLocked =  buf.readBoolean();
        int value = buf.readInt();
        return new PersistentIntDataSyncPacket(key, isLocked, value);
    }

    @Override
    public void execute(Player playerEntity) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && Minecraft.getInstance().level != null) {
            if(isLocked){
                DataManager.putData(player, key+"isLocked", true);
                return;
            }
            DataManager.putData(player, key, value);
            DataManager.putData(player, key+"isLocked", false);
        }
    }
}