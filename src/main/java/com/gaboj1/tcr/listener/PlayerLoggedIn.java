package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.util.DataManager;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerLoggedIn {

    static Random r = new Random();
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new PlayerLoggedIn();
    }
    @Mod.EventBusSubscriber
    private static class ForgeBusEvents {

        //初始化玩家数据
        @SubscribeEvent
        public static void initServerPlayerPersistentData(PlayerEvent.PlayerLoggedInEvent event) {
            DataManager.init(event.getEntity());
        }

    }
}
