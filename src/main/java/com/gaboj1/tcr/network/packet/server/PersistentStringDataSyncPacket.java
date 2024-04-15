package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public record PersistentStringDataSyncPacket(String key, boolean isLocked, String value) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(key));
        buf.writeBoolean(isLocked);
        buf.writeComponent(Component.literal(value));
    }

    public static PersistentStringDataSyncPacket decode(FriendlyByteBuf buf) {
        String key = buf.readComponent().getString();
        boolean isLocked =  buf.readBoolean();
        String value = buf.readComponent().getString();
        return new PersistentStringDataSyncPacket(key, isLocked, value);
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