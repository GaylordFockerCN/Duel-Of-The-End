package com.gaboj1.tcr.command;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TCRCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        SetPlayerDataCommand.register(dispatcher);
        SetConfigCommand.register(dispatcher);
        TCRTimeCommand.register(dispatcher);
    }
}