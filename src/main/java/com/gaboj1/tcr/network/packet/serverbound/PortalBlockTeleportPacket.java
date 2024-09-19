package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import com.gaboj1.tcr.worldgen.portal.TCRTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.Point;
import java.util.Objects;

/**
 * 客户端发给服务端，进行传送判断并播放音效
 */
public record PortalBlockTeleportPacket(byte interactionID, boolean isVillage, boolean isFromTeleporter, BlockPos bedPos) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.interactionID());
        buf.writeBoolean(this.isVillage());
        buf.writeBoolean(this.isFromTeleporter());
        buf.writeBlockPos(this.bedPos());
    }

    public static PortalBlockTeleportPacket decode(FriendlyByteBuf buf) {
        return new PortalBlockTeleportPacket(buf.readByte(), buf.readBoolean(), buf.readBoolean(), buf.readBlockPos());
    }

    //TODO 修改不同的高度，修正偏移值。
    @Override
    public void execute(Player playerEntity) {
        CompoundTag serverPlayerData = playerEntity.getPersistentData();
        Point destination;
        Vec3 offset = Vec3.ZERO;
        boolean unlocked;
        int height;
        if(isVillage){
            switch (this.interactionID()){
                //第一第二可选
                case 1: destination = BiomeMap.getInstance().getVillage1();height = 150;unlocked = serverPlayerData.getBoolean("village1Unlocked");;break;
                case 2: destination = BiomeMap.getInstance().getVillage2()[0];height = 150;unlocked = serverPlayerData.getBoolean("village2Unlocked");break;
                case 3: destination = BiomeMap.getInstance().getVillage3();height = 150;unlocked = serverPlayerData.getBoolean("village3Unlocked");break;
                case 4: destination = BiomeMap.getInstance().getVillage4();height = 150;unlocked = serverPlayerData.getBoolean("village4Unlocked");break;
                default:destination = BiomeMap.getInstance().getMainCenter();unlocked = SaveUtil.worldLevel >= 1;height = 200;//完成某一个群系的事件后才解锁主城
            }
        } else {
            switch (this.interactionID()){
                case 1: destination = BiomeMap.getInstance().getCenter1();height = 210;unlocked = serverPlayerData.getBoolean("boss1Unlocked");break;
                case 2: destination = BiomeMap.getInstance().getCenter2();height = 220;unlocked = serverPlayerData.getBoolean("boss2Unlocked");break;
                case 3: destination = BiomeMap.getInstance().getCenter3();height = 230;unlocked = serverPlayerData.getBoolean("boss3Unlocked");break;
                case 4: destination = BiomeMap.getInstance().getCenter4();height = 240;unlocked = serverPlayerData.getBoolean("boss4Unlocked");break;
                default:destination = BiomeMap.getInstance().getMainCenter();unlocked = SaveUtil.worldLevel >= 1;height = 200;//完成某一个群系的事件后才解锁主城
            }
        }

        //BiomeMap直接获取的是群系坐标，所以需要矫正一下。
        destination = BiomeMap.getInstance().getBlockPos(destination);

        if(isFromTeleporter){
            if(playerEntity instanceof ServerPlayer serverPlayer){
                ServerLevel currentLevel = serverPlayer.serverLevel();
                ServerLevel portalDimension = Objects.requireNonNull(serverPlayer.getServer()).getLevel(TCRDimension.P_SKY_ISLAND_LEVEL_KEY);
                if(portalDimension != null){
                    playerEntity.changeDimension(portalDimension, new TCRTeleporter(new BlockPos(destination.x, 170, destination.y), true));
                    TCRAdvancementData.getAdvancement(TheCasketOfReveriesMod.MOD_ID, serverPlayer);
                    TCRAdvancementData.getAdvancement("enter_realm_of_the_dream", serverPlayer);
                    if(DataManager.isFirstEnter.getBool(serverPlayer)){
                        serverPlayer.displayClientMessage(Component.translatable("info.the_casket_of_reveries.first_enter"), false);
                        DataManager.isFirstEnter.putBool(serverPlayer, false);
                    }
                    //记录进入点
                    serverPlayer.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                        tcrPlayer.setBedPointBeforeEnter(bedPos);
                    }));
                    //召唤假人
                    TCRFakePlayer fakePlayer = new TCRFakePlayer(serverPlayer, currentLevel, bedPos);
                    fakePlayer.setPos(bedPos.getCenter());
                    currentLevel.addFreshEntity(fakePlayer);
                    fakePlayer.setSleepingPos(bedPos);
                }
            }
        } else {
            if(unlocked || playerEntity.isCreative()){
                Level level = playerEntity.level();
                playerEntity.teleportTo(destination.x + offset.x,height + offset.y,destination.y + offset.z);
                level.playSound(null,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);//播放传送音效
            }else {
                playerEntity.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_lock"));
            }
        }
    }
}
