package com.p1nero.dote.command;

import com.p1nero.dote.DuelOfTheEndMod;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DOTECommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        SetConfigCommand.register(dispatcher);
        DOTETimeCommand.register(dispatcher);
        DOTESetWorldLevelCommand.register(dispatcher);
    }
}