package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * 加载的时候只会加载服务端的皮肤id，所以需要以此来同步
 * @param id 实体id，用于查找实体
 * @param skinID 皮肤id
 */
public record VillagerChangeIDPacket(int id, int skinID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
        buf.writeInt(this.skinID());
    }

    public static VillagerChangeIDPacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        int skinID = buf.readInt();
        return new VillagerChangeIDPacket(id, skinID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(id);
            if(entity instanceof TCRVillager tcrVillager){
                tcrVillager.setVillagerId(skinID());
            }
        }
    }
}