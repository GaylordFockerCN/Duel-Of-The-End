package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.SyncSaveUtilPacket;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 控制服务端SaveUtil的读写
 */
@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class ServerEvents {

    /**
     * 获取存档名字，用于二次读取地图时用。
     * 仅限服务器用，如果是单人玩则需要在选择窗口或者创建游戏窗口获取。因为LevelName是可重复的，LevelID才是唯一的...
     * @see com.gaboj1.tcr.mixin.WorldListEntryMixin#injectedLoadWorld(CallbackInfo ci)
     * @see com.gaboj1.tcr.mixin.CreateWorldScreenMixin#injected(CallbackInfoReturnable)
     */
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event){
        //服务端读取，客户端从Mixin读
        if(FMLEnvironment.dist.isDedicatedServer()){
            if(TCRBiomeProvider.worldName.isEmpty()){
                String levelName = event.getServer().getWorldData().getLevelName();
                TCRBiomeProvider.worldName = levelName;
                TCRBiomeProvider.updateBiomeMap(levelName);
                SaveUtil.read(levelName);
            }
        }
    }

    /**
     * stop的时候TCRBiomeProvider.worldName已经初始化了，无需处理
     */
    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event){
        SaveUtil.save(TCRBiomeProvider.worldName);
        SaveUtil.clear();
    }

}
