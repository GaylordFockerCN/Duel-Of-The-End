package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record AddVillagerParticlePacket (int id) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
    }

    public static AddVillagerParticlePacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new AddVillagerParticlePacket(id);
    }

    @Override
    public void execute(Player playerEntity) {

        if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null){
            Entity entity = Minecraft.getInstance().player.level().getEntity(id);
            if(entity instanceof TCRVillager tcrVillager){
                tcrVillager.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
            }
        }

    }
}