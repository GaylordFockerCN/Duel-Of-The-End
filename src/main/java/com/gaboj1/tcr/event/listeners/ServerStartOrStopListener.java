package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
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
    }

    @SubscribeEvent
    public static void onServerAboutToStop(ServerStoppedEvent event){
        SaveUtil.save();
    }

}
