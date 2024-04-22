package com.gaboj1.tcr.command;

import com.gaboj1.tcr.TCRConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SetConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tcr")
                .then(Commands.literal("set").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                    .then(Commands.literal("enable_better_structure_block_load")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                                .executes((context) -> setData(BoolArgumentType.getBool(context, "value"), context.getSource().getPlayer()))
                        )
                    )
                )
        );
    }

    private static int setData(boolean value, Player player) {
        TCRConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.set(value);
        player.displayClientMessage(Component.literal("enable_better_structure_block_load : "+value), false);
        return 0;
    }
}
