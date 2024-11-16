package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.DOTEConfig;
import com.gaboj1.tcr.capability.DOTECapabilityProvider;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.entity.custom.DOTEBoss;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.DOTEPacketHandler;
import com.gaboj1.tcr.network.packet.SyncSaveUtilPacket;
import com.gaboj1.tcr.network.packet.clientbound.SyncUuidPacket;
import com.gaboj1.tcr.archive.DOTEArchiveManager;
import com.gaboj1.tcr.worldgen.dimension.DOTEDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
public class PlayerEventListener {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer){

            //动态调整怪物血量
            if(DOTEConfig.BOSS_HEALTH_AND_LOOT_MULTIPLE.get()){
                for(Entity entity : serverPlayer.serverLevel().getEntities().getAll()){
                    if(entity instanceof MultiPlayerBoostEntity multiPlayerBoostEntity){
                        multiPlayerBoostEntity.whenPlayerCountChange();
                    }
                }
            }
            //同步客户端数据
            PacketRelay.sendToPlayer(DOTEPacketHandler.INSTANCE, new SyncSaveUtilPacket(DOTEArchiveManager.toNbt()), serverPlayer);
            //防止重进后boss的uuid不同
            DOTEBoss.SERVER_BOSSES.forEach(((uuid, integer) -> PacketRelay.sendToPlayer(DOTEPacketHandler.INSTANCE, new SyncUuidPacket(uuid, integer), serverPlayer)));
        } else {
            //单机世界的同步数据
            if(DOTEArchiveManager.isAlreadyInit()){
                PacketRelay.sendToServer(DOTEPacketHandler.INSTANCE, new SyncSaveUtilPacket(DOTEArchiveManager.toNbt()));
            }
        }

        //主世界没假身就召唤假身，注意主世界和维度的区别
        Player player = event.getEntity();
        if(player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY && serverPlayer.getServer() != null){
            serverPlayer.getCapability(DOTECapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                ServerLevel overworld = serverPlayer.getServer().overworld();
                if(tcrPlayer.getFakePlayerUuid() == null || !(overworld.getEntity(tcrPlayer.getFakePlayerUuid()) instanceof TCRFakePlayer)){
                    //召唤假人
                    BlockPos bedBlockPos = tcrPlayer.getBedPointBeforeEnter();
                    TCRFakePlayer fakePlayer = new TCRFakePlayer(serverPlayer, overworld, bedBlockPos);
                    overworld.addFreshEntity(fakePlayer);
                    fakePlayer.setSleepingPos(bedBlockPos);
                }
            }));
        }
    }

    /**
     * 玩家退出事件
     * 玩家退出假人跟着退出，防bug
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){
        //主世界没假身就召唤假身，注意主世界和维度的区别
        Player player = event.getEntity();
        if(player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY && serverPlayer.getServer() != null){
            serverPlayer.getCapability(DOTECapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                ServerLevel overworld = serverPlayer.getServer().overworld();
                if(overworld.getEntity(tcrPlayer.getFakePlayerUuid()) instanceof TCRFakePlayer fakePlayer){
                    //召唤假人
                    fakePlayer.discard();
                }
            }));
        }
    }

    /**
     * 前面的区域以后再来探索吧~
     */
    @SubscribeEvent
    public static void enterBiome(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        //禁止进入群系
        if(!player.isCreative()){
            Level level = player.level();
            Holder<Biome> currentBiome = level.getBiome(player.getOnPos());

        }

    }


    @SubscribeEvent
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event){

    }


}
