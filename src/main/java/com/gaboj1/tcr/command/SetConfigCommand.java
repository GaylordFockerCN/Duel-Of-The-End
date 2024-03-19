package com.gaboj1.tcr.command;

import com.gaboj1.tcr.TCRConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class SetConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tcr")
                .then(Commands.literal("set").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                    .then(Commands.literal("enable_better_structure_block_load")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                                .executes((context) -> setData(BoolArgumentType.getBool(context, "value")))
                        )
                    )
                )
        );
    }

    private static int setData(boolean value) {
        TCRConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.set(value);
        return 0;
    }
}
