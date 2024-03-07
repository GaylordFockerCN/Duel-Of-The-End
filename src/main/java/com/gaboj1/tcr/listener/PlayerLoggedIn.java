package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeTags;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
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

        @SubscribeEvent
        public static void enterBiome(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            Level level = player.level();
            Holder<Biome> currentBiome = level.getBiome(player.getOnPos());
            if(currentBiome.is(TCRBiomeTags.FORBIDDEN_BIOME)){
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.enter_forbidden_biome"),true);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20,1,false,true));
                player.addEffect(new MobEffectInstance(MobEffects.HARM,1,1,false,true));
            }

        }


    }
}
