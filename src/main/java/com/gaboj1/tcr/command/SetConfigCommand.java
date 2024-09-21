package com.gaboj1.tcr.command;

import com.gaboj1.tcr.TCRConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public class SetConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tcr")
                .then(Commands.literal("set").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                    .then(Commands.literal("enable_better_structure_block_load")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                                .executes((context) -> setData(TCRConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD, BoolArgumentType.getBool(context, "value"), context))
                        )
                    )
                    .then(Commands.literal("enable_boss_spawn_block_load")
                            .then(Commands.argument("value", BoolArgumentType.bool())
                                    .executes((context) -> setData(TCRConfig.ENABLE_BOSS_SPAWN_BLOCK_LOAD, BoolArgumentType.getBool(context, "value"), context))
                            )
                    )
                    .then(Commands.literal("test_x")
                            .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                    .executes((context) -> setData(TCRConfig.TEST_X, DoubleArgumentType.getDouble(context, "value"), context))
                            )
                    )
                    .then(Commands.literal("test_y")
                            .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                    .executes((context) -> setData(TCRConfig.TEST_Y, DoubleArgumentType.getDouble(context, "value"), context))
                            )
                    )
                    .then(Commands.literal("test_z")
                            .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                    .executes((context) -> setData(TCRConfig.TEST_Z, DoubleArgumentType.getDouble(context, "value"), context))
                            )
                    )
                )
        );
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
}
