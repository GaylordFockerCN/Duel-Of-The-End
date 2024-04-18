package com.gaboj1.tcr.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;


public class DialogueComponentBuilder {

    public static final DialogueComponentBuilder BUILDER = new DialogueComponentBuilder();
    public MutableComponent buildDialogue(Entity entity, Component content){
        return Component.literal("[").append(entity.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append("]").append(content);
    }

    public MutableComponent buildDialogue(Entity entity, Component content ,ChatFormatting... nameChatFormatting){
        return Component.literal("[").append(entity.getDisplayName().copy().withStyle(nameChatFormatting)).append("]").append(content).withStyle();
    }

    public MutableComponent buildDialogueChoice(EntityType<?> entityType, String key) {
        return Component.translatable(entityType+".choice." + key);
    }
    public MutableComponent buildDialogueChoice(EntityType<?> entityType, int i) {
        return Component.translatable(entityType+".choice" + i);
    }

    public MutableComponent buildDialogueChoice(EntityType<?> entityType,int skinID, int i) {
        return Component.translatable(entityType+".choice"+skinID+"_"+ i);
    }
    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int i ,boolean newLine) {
        Component component = Component.translatable(entityType+".dialog"+i);

        return Component.literal(newLine?"\n":"").append(component);//换行符有效
    }

    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int i ) {
        Component component = Component.translatable(entityType+".dialog"+i);

        return Component.literal("\n").append(component);//换行符有效
    }

    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int skinID , int i ) {
        Component component = Component.translatable(entityType+".dialog"+skinID+"_"+i);

        return Component.literal("\n").append(component);//换行符有效
    }


    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int skinID , int i ,boolean newLine) {
        Component component = Component.translatable(entityType+".dialog"+skinID+"_"+i);

        return Component.literal(newLine?"\n":"").append(component);//换行符有效
    }

    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int i, String s) {
        Component component = Component.translatable(entityType + ".dialog" + i, s);
        return Component.literal("\n").append(component);
    }

}
