package com.gaboj1.tcr.command;

import com.gaboj1.tcr.TCRConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public class SetConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

    }

    private static <T extends ForgeConfigSpec.ConfigValue<E>, E> int setData(T key, E value, CommandContext<CommandSourceStack> context) {
        CommandSourceStack stack = context.getSource();
        key.set(value);
        if(stack.getPlayer() != null){
            stack.getPlayer().displayClientMessage(Component.literal( context.getInput() + " : SUCCESS"), false);
            if(key == TCRConfig.TEST_Y || key == TCRConfig.TEST_X || key == TCRConfig.TEST_Z){
                stack.getPlayer().displayClientMessage(Component.literal(TCRConfig.TEST_X.get()+ ", " + TCRConfig.TEST_Y.get() + ", " + TCRConfig.TEST_Z.get()), false);
            }
        }
        return 0;
    }

    private static <T extends ForgeConfigSpec.ConfigValue<E>, E> int resetData(T key, CommandContext<CommandSourceStack> context) {
        CommandSourceStack stack = context.getSource();
        key.set(key.getDefault());
        if(stack.getPlayer() != null){
            stack.getPlayer().displayClientMessage(Component.literal( context.getInput() + " : reset to " + key.getDefault()), false);
        }
        return 0;
    }
}
