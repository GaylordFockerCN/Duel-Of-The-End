package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.archive.DOTEArchiveManager;
import com.gaboj1.tcr.worldgen.biome.DOTEBiomeProvider;
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
@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
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
            if(DOTEBiomeProvider.worldName.isEmpty()){
                String levelName = event.getServer().getWorldData().getLevelName();
                DOTEBiomeProvider.worldName = levelName;
                DOTEBiomeProvider.updateBiomeMap(levelName);
                DOTEArchiveManager.read(levelName);
            }
        }
    }

    /**
     * stop的时候TCRBiomeProvider.worldName已经初始化了，无需处理
     */
    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event){
        DOTEArchiveManager.save(DOTEBiomeProvider.worldName);
        DOTEArchiveManager.clear();
    }

}
