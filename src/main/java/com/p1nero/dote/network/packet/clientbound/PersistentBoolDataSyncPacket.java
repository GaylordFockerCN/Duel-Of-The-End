package com.p1nero.dote.network.packet.clientbound;

import com.p1nero.dote.network.packet.BasePacket;
import com.p1nero.dote.util.ClientHelper;
import com.p1nero.dote.archive.DataManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public record PersistentBoolDataSyncPacket(String key, boolean isLocked, boolean value) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(key));
        buf.writeBoolean(isLocked);
        buf.writeBoolean(value);
    }

    public static PersistentBoolDataSyncPacket decode(FriendlyByteBuf buf) {
        String key = buf.readComponent().getString();
        boolean isLocked =  buf.readBoolean();
        boolean value = buf.readBoolean();
        return new PersistentBoolDataSyncPacket(key, isLocked, value);
    }

    @Override
    public void execute(Player playerEntity) {
        ClientHelper.localPlayerDo((player -> {
            if(isLocked){
                DataManager.putData(player, key+"isLocked", true);
                return;
            }
            DataManager.putData(player, key, value);
            DataManager.putData(player, key+"isLocked", false);
        }));
    }
}