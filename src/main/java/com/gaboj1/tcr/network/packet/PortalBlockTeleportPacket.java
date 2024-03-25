package com.gaboj1.tcr.network.packet;

import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import java.awt.Point;

/**
 * This packet is sent to the server whenever the player chooses an important action in the NPC dialogue.
 */
public record PortalBlockTeleportPacket(byte interactionID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.interactionID());
    }

    public static PortalBlockTeleportPacket decode(FriendlyByteBuf buf) {
        return new PortalBlockTeleportPacket(buf.readByte());
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        CompoundTag serverPlayerData = playerEntity.getPersistentData();
        Point destination;
        boolean unlocked;
        int height;
        int R = BiomeMap.getInstance().getR();
        switch (this.interactionID()){
            case 1: destination = BiomeMap.getInstance().getCenter1();height = 210;unlocked = serverPlayerData.getBoolean("boss1Unlocked");break;
            case 2: destination = BiomeMap.getInstance().getCenter2();height = 220;unlocked = serverPlayerData.getBoolean("boss2Unlocked");break;
            case 3: destination = BiomeMap.getInstance().getCenter3();height = 230;unlocked = serverPlayerData.getBoolean("boss3Unlocked");break;
            case 4: destination = BiomeMap.getInstance().getCenter4();height = 240;unlocked = serverPlayerData.getBoolean("boss4Unlocked");break;
            default:destination = BiomeMap.getInstance().getMainCenter();unlocked = true;height = 200;//主城欢迎您（
        }

        //BiomeMap直接获取的是群系坐标
        destination = BiomeMap.getInstance().getBlockPos(destination);

        if(unlocked || playerEntity.isCreative()){
            Level level = playerEntity.level();
            playerEntity.teleportTo(destination.x,height,destination.y);
            level.playSound(null,playerEntity.getOnPos(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);//播放传送音效 TODO 有bug
        }else {
            playerEntity.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_lock"));
        }

    }
}
