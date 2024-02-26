package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.util.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
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

        @SubscribeEvent
        public static void initServerPlayerPersistentData(PlayerEvent.PlayerLoggedInEvent event) {
            DataManager.init(event.getEntity());
        }

        /**
         * FIXME 没办法，只有这里可以获取客户端玩家。。。。不知道别的哪里可以初始
         */
        @SubscribeEvent
        public static void initClientPlayerPersistentData(ClientPlayerNetworkEvent event) {
            DataManager.init(event.getPlayer());
        }


    }
}
