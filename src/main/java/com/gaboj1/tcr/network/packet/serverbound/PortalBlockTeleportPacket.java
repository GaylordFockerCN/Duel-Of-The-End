package com.gaboj1.tcr.network.packet.serverbound;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.network.packet.BasePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import com.gaboj1.tcr.worldgen.portal.TCRTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
            case 1: destination = BiomeMap.getInstance().getVillage1();height = 150;break;
            case 2: destination = BiomeMap.getInstance().getVillage2()[0];height = 150;break;
            case 3: destination = BiomeMap.getInstance().getVillage3();height = 150;break;
            case 4: destination = BiomeMap.getInstance().getVillage4();height = 150;break;
            case 5: destination = BiomeMap.getInstance().getCenter1();height = 210;break;
            case 6: destination = BiomeMap.getInstance().getCenter2();height = 220;break;
            case 7: destination = BiomeMap.getInstance().getCenter3();height = 230;break;
            case 8: destination = BiomeMap.getInstance().getCenter4();height = 240;break;
            default:destination = BiomeMap.getInstance().getMainCenter();unlocked = SaveUtil.worldLevel >= 1;height = 200;//完成某一个群系的事件后才解锁主城
        }
        if(id < 9){
            if(!DataManager.isSecondEnter.get(playerEntity)){
                unlocked = true;
            } else {
                unlocked = DataManager.portalPointUnlockData.get(id - 1).get(playerEntity);
            }
        }

        if(unlocked || playerEntity.isCreative()){
            //BiomeMap直接获取的是群系坐标，所以需要矫正一下。
            destination = BiomeMap.getInstance().getBlockPos(destination);

            if(isFromPortalBed){
                if(playerEntity instanceof ServerPlayer serverPlayer){
                    ServerLevel currentLevel = serverPlayer.serverLevel();
                    ServerLevel portalDimension = Objects.requireNonNull(serverPlayer.getServer()).getLevel(TCRDimension.P_SKY_ISLAND_LEVEL_KEY);
                    if(portalDimension != null){
                        playerEntity.changeDimension(portalDimension, new TCRTeleporter(new BlockPos(destination.x, 170, destination.y), true));
                        playerEntity.level().playSound(null,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);
                        if(!DataManager.isSecondEnter.get(serverPlayer)){
                            TCRAdvancementData.getAdvancement(TheCasketOfReveriesMod.MOD_ID, serverPlayer);
                            TCRAdvancementData.getAdvancement("enter_realm_of_the_dream", serverPlayer);
                            //输出首次进入维度的提示
                            DialogueComponentBuilder.displayClientMessages(serverPlayer, 6000, false, ()->{},
                                    TheCasketOfReveriesMod.getInfo("first_enter1"),
                                    TheCasketOfReveriesMod.getInfo("first_enter2"),
                                    TheCasketOfReveriesMod.getInfo("first_enter3")
                                    );
                            DataManager.portalPointUnlockData.get(id - 1).put(playerEntity, true);//解锁传送点
                            DataManager.isSecondEnter.put(serverPlayer, true);
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
                playerEntity.teleportTo(destination.x + offset.x,height + offset.y,destination.y + offset.z);
                playerEntity.level().playSound(null,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);
            }
        }else {
            playerEntity.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_lock"));
        }

    }
}
