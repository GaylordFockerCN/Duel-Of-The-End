package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.DOTEConfig;
import com.gaboj1.tcr.capability.DOTECapabilityProvider;
import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.datagen.DOTEAdvancementData;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.archive.DOTEArchiveManager;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import com.gaboj1.tcr.worldgen.dimension.DOTEDimension;
import com.gaboj1.tcr.worldgen.portal.DOTETeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.awt.Point;
import java.util.Objects;

/**
 * 客户端发给服务端，进行传送判断并播放音效
 */
public record PortalBlockTeleportPacket(byte interactionID, boolean isVillage, boolean isFromPortalBed, BlockPos bedPos) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.interactionID());
        buf.writeBoolean(this.isVillage());
        buf.writeBoolean(this.isFromPortalBed());
        buf.writeBlockPos(this.bedPos());
    }

    public static PortalBlockTeleportPacket decode(FriendlyByteBuf buf) {
        return new PortalBlockTeleportPacket(buf.readByte(), buf.readBoolean(), buf.readBoolean(), buf.readBlockPos());
    }

    //TODO 修改不同的高度，修正偏移值。
    @Override
    public void execute(Player playerEntity) {
        Point destination;
        Vec3 offset = Vec3.ZERO;
        boolean unlocked = false;
        int height;
        int id = this.interactionID;
        if(!isVillage){
            id += 4;
        }
        switch (id){
            case 5: destination = BiomeMap.getInstance().getCenter1();height = 210;break;
            case 6: destination = BiomeMap.getInstance().getCenter2();height = 220;break;
            default: destination = BiomeMap.getInstance().getCenter();height = 230;break;
        }

        if(unlocked || playerEntity.isCreative()){
            //BiomeMap直接获取的是群系坐标，所以需要矫正一下。
            destination = BiomeMap.getInstance().getBlockPos(destination);

            if(isFromPortalBed){
                if(DOTEConfig.NO_PLOT_MODE.get()){
                    DOTEArchiveManager.setNoPlotMode();
                }
                if(playerEntity instanceof ServerPlayer serverPlayer){
                    ServerLevel currentLevel = serverPlayer.serverLevel();
                    ServerLevel portalDimension = Objects.requireNonNull(serverPlayer.getServer()).getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY);
                    if(portalDimension != null){
                        playerEntity.changeDimension(portalDimension, new DOTETeleporter(new BlockPos(destination.x, 170, destination.y)));
                        playerEntity.level().playSound(null,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);
                        DOTEAdvancementData.getAdvancement(DuelOfTheEndMod.MOD_ID, serverPlayer);
                        DOTEAdvancementData.getAdvancement("enter_realm_of_the_dream", serverPlayer);
                        //输出首次进入维度的提示
                        DialogueComponentBuilder.displayClientMessages(serverPlayer, 6000, false, ()->{

                                },
                                DuelOfTheEndMod.getInfo("first_enter1"),
                                DuelOfTheEndMod.getInfo("first_enter2"),
                                DuelOfTheEndMod.getInfo("first_enter3")
                                );
                        //记录进入点
                        serverPlayer.getCapability(DOTECapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
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
                playerEntity.teleportTo(destination.x + offset.x,height + offset.y,destination.y + offset.z);
                playerEntity.level().playSound(null,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);
            }
        }else {
            playerEntity.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_lock"));
        }

    }
}
