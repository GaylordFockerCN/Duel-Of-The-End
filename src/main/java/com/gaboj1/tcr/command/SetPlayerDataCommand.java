package com.gaboj1.tcr.command;

import com.gaboj1.tcr.util.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class SetPlayerDataCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tcr")
                .then(Commands.literal("data").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("set").requires((commandSourceStack) -> commandSourceStack.isPlayer())
                                .then(Commands.literal("is_white")
                                        .then(Commands.argument("value", BoolArgumentType.bool())
                                                .executes((context) -> setData(context.getSource(), DataManager.isWhite.getKey(),BoolArgumentType.getBool(context, "value")))
                                        )
                                )
                                .then(Commands.literal("boss1defeated")
                                        .then(Commands.argument("value", BoolArgumentType.bool())
                                                .executes((context) -> setData(context.getSource(), DataManager.boss1Defeated.getKey(),BoolArgumentType.getBool(context, "value")))
                                        )
                                )
                        ).then(Commands.literal("get")
                                .then(Commands.literal("is_white").executes((context) -> getBoolData(context.getSource(), DataManager.isWhite.getKey())))
                                .then(Commands.literal("is_boss1defeated").executes((context) -> getBoolData(context.getSource(), DataManager.boss1Defeated.getKey())))
                        )
                )
        );
    }

    private static int setData(CommandSourceStack source, String key, boolean data) {
        if(source.isPlayer()){
            Player player = source.getPlayer();
            DataManager.putData(player, key, data);
            //懒得写翻译文件了哈哈顺便学一下怎么判断语言环境
            if(Minecraft.getInstance().getLanguageManager().getSelected() == "zh_cn"){
                player.sendSystemMessage(player.getDisplayName().copy().append(" 的 "+key+" 已经被设置为 ").append(String.valueOf(data)));
            }else {
                player.sendSystemMessage(player.getDisplayName().copy().append("'s "+key+" has been set to ").append(String.valueOf(data)));
            }

            return 0;
        }
        return -1;
    }

    private static int getBoolData(CommandSourceStack source, String key) {
        if(source.isPlayer()){
            Player player = source.getPlayer();

            player.sendSystemMessage(player.getDisplayName().copy().append("'s "+key+" is ").append(String.valueOf(DataManager.getBool(player,key))));
            return 0;
        }
        return -1;
    }

}
