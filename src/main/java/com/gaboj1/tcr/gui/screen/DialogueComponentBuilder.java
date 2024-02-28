package com.gaboj1.tcr.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;


public class DialogueComponentBuilder {
    public static MutableComponent buildDialogue(Entity entity, Component content){
        return Component.literal("[").append(entity.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append("]").append(content);
    }

    public static MutableComponent buildDialogue(Entity entity, Component content ,ChatFormatting... nameChatFormatting){
        return Component.literal("[").append(entity.getDisplayName().copy().withStyle(nameChatFormatting)).append("]").append(content).withStyle();
    }

}
