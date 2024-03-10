package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeTags;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerEventListener {

    static Random r = new Random();
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new PlayerEventListener();
    }
    @Mod.EventBusSubscriber
    private static class ForgeBusEvents {

        //初始化玩家数据
        @SubscribeEvent
        public static void initServerPlayerPersistentData(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
            DataManager.init(event.getEntity());
        }

        //前面的区域以后再来探索吧~
        @SubscribeEvent
        public static void enterBiome(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if(player.isCreative()){
                return;
            }
            Level level = player.level();
            Holder<Biome> currentBiome = level.getBiome(player.getOnPos());
            if (currentBiome.is(TCRBiomeTags.FORBIDDEN_BIOME)) {

                if (!player.level().isClientSide() && player.getServer() != null) {

                    String commands = "/title "+player.getName().getString()+" title {\"text\":\""+ I18n.get("info.the_casket_of_reveries.enter_forbidden_biome") +"\"}";
                    System.out.println(commands);
                    player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 4,
                            player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), commands);
                }

//                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.enter_forbidden_biome"), true);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 1, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1, false, true));
            }
        }

    }
}
