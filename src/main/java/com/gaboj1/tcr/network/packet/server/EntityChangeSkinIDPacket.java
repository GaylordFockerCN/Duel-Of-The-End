package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.entity.ManySkinEntity;
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
public record EntityChangeSkinIDPacket(int id, int skinID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
        buf.writeInt(this.skinID());
    }

    public static EntityChangeSkinIDPacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        int skinID = buf.readInt();
        return new EntityChangeSkinIDPacket(id, skinID);
    }

    @Override
    public void execute(Player playerEntity) {
//        new Thread(()->{
//            while (Minecraft.getInstance().level == null || Minecraft.getInstance().player == null) {
//                try {
//                    Thread.sleep(200);//等两端实体数据互通完才能进行同步操作
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            Entity entity = Minecraft.getInstance().player.level().getEntity(id);
//            if(entity instanceof TCRVillager tcrVillager){
//                tcrVillager.setVillagerId(skinID());
//            }
//        }).start();
        if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null){
            Entity entity = Minecraft.getInstance().player.level().getEntity(id);
            if(entity instanceof ManySkinEntity manySkinEntity){
                manySkinEntity.setSkinID(skinID());
            }
        }

    }
}