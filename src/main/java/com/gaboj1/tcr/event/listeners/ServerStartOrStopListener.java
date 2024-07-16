package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.SyncSaveUtilPacket;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class ServerStartOrStopListener {

    /**
     * 获取存档名字，用于二次读取地图时用。
     * 仅限服务器用，如果是单人玩则需要在选择窗口或者创建游戏窗口获取。因为LevelName是可重复的，LevelID才是唯一的...
     * @see com.gaboj1.tcr.mixin.WorldListEntryMixin#injectedLoadWorld(CallbackInfo ci)
     * @see com.gaboj1.tcr.mixin.CreateWorldScreenMixin#injected(CallbackInfoReturnable)
     */
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event){
        if(TCRBiomeProvider.worldName.isEmpty()){
            TCRBiomeProvider.worldName = event.getServer().getWorldData().getLevelName();
        }
        SaveUtil.read();
        PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new SyncSaveUtilPacket(SaveUtil.toNbt()));
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event){
        SaveUtil.save();
    }

    /**
     * 玩家加入服务端时同步服务端和客户端的数据
     */
    @SubscribeEvent
    public static void onPlayerIn(PlayerEvent.PlayerLoggedInEvent event){
        if(event.getEntity() instanceof ServerPlayer player){
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new SyncSaveUtilPacket(SaveUtil.toNbt()), player);
        }
    }

}
