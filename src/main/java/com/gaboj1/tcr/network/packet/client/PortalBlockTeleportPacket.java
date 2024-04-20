package com.gaboj1.tcr.network.packet.client;

import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import java.awt.Point;

/**
 * 客户端发给服务端，进行传送判断并播放音效
 */
public record PortalBlockTeleportPacket(byte interactionID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.interactionID());
    }

    public static PortalBlockTeleportPacket decode(FriendlyByteBuf buf) {
        return new PortalBlockTeleportPacket(buf.readByte());
    }

    //TODO 修改不同的高度，修正偏移值。
    @Override
    public void execute(Player playerEntity) {
        CompoundTag serverPlayerData = playerEntity.getPersistentData();
        Point destination;
        Vec3 offset = Vec3.ZERO;
        boolean unlocked;
        int height;
        switch (this.interactionID()){
            case 1: destination = BiomeMap.getInstance().getCenter1();height = 210;unlocked = serverPlayerData.getBoolean("boss1Unlocked");break;
            case 2: destination = BiomeMap.getInstance().getCenter2();height = 220;unlocked = serverPlayerData.getBoolean("boss2Unlocked");break;
            case 3: destination = BiomeMap.getInstance().getCenter3();height = 230;unlocked = serverPlayerData.getBoolean("boss3Unlocked");break;
            case 4: destination = BiomeMap.getInstance().getCenter4();height = 240;unlocked = serverPlayerData.getBoolean("boss4Unlocked");break;
            default:destination = BiomeMap.getInstance().getMainCenter();unlocked = true;height = 200;//主城欢迎您（
        }

        //BiomeMap直接获取的是群系坐标，所以需要矫正一下。
        destination = BiomeMap.getInstance().getBlockPos(destination);

        if(unlocked || playerEntity.isCreative()){
            Level level = playerEntity.level();
            playerEntity.teleportTo(destination.x + offset.x,height + offset.y,destination.y + offset.z);
            level.playSound(null,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);//播放传送音效
        }else {
            playerEntity.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_lock"));
        }

    }
}
