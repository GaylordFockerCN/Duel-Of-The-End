package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.DOTEConfig;
import com.gaboj1.tcr.archive.DataManager;
import com.gaboj1.tcr.capability.DOTECapabilityProvider;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.custom.DOTEBoss;
import com.gaboj1.tcr.item.DOTEItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.DOTEPacketHandler;
import com.gaboj1.tcr.network.packet.SyncSaveUtilPacket;
import com.gaboj1.tcr.network.packet.clientbound.SyncUuidPacket;
import com.gaboj1.tcr.archive.DOTEArchiveManager;
import com.gaboj1.tcr.util.ItemUtil;
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
            //人手送一个
            if(DOTEConfig.GIVE_M_KEY.get() && !DataManager.keyGot.get(serverPlayer)){
                ItemUtil.addItem(serverPlayer, DOTEItems.M_KEY.get(), 1);
                DataManager.keyGot.put(serverPlayer, true);
            }
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

    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){

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
