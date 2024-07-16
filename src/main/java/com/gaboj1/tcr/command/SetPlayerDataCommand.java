package com.gaboj1.tcr.command;

import com.gaboj1.tcr.util.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SetPlayerDataCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tcr")
            .then(Commands.argument("player", EntityArgument.player())
                    .then(Commands.literal("data").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                            
                    )
            )
        );
    }

    private static int setData(CommandSourceStack source, ServerPlayer player, String key, boolean data) {
            DataManager.putData(player, key, data);
            //懒得写翻译文件了哈哈顺便学一下怎么判断语言环境
            if(Minecraft.getInstance().getLanguageManager().getSelected() == "zh_cn"){

//                source.sendSuccess(() -> Component.literal(""), false);
                
                player.sendSystemMessage(player.getDisplayName().copy().append(" 的 "+key+" 已经被设置为 ").append(String.valueOf(data)));
            }else {
                player.sendSystemMessage(player.getDisplayName().copy().append("'s "+key+" has been set to ").append(String.valueOf(data)));
            }

            return 0;
    }

    private static int getBoolData(CommandSourceStack source, ServerPlayer player, String key) {
            player.sendSystemMessage(player.getDisplayName().copy().append("'s "+key+" is ").append(String.valueOf(DataManager.getBool(player,key))));
            return 0;
    }

}
