package com.gaboj1.tcr.command;

import com.gaboj1.tcr.DOTEConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

public class SetConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

    }

    private static <T extends ForgeConfigSpec.ConfigValue<E>, E> int setData(T key, E value, CommandContext<CommandSourceStack> context) {
        CommandSourceStack stack = context.getSource();
        key.set(value);
        if(stack.getPlayer() != null){
            stack.getPlayer().displayClientMessage(Component.literal( context.getInput() + " : SUCCESS"), false);
            if(key == DOTEConfig.TEST_Y || key == DOTEConfig.TEST_X || key == DOTEConfig.TEST_Z){
                stack.getPlayer().displayClientMessage(Component.literal(DOTEConfig.TEST_X.get()+ ", " + DOTEConfig.TEST_Y.get() + ", " + DOTEConfig.TEST_Z.get()), false);
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
