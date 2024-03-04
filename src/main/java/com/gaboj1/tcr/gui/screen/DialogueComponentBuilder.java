package com.gaboj1.tcr.gui.screen;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.util.DataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;


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
    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int i ,boolean newLine) {
        Component component = Component.translatable(entityType+".dialog"+i);

        return Component.literal(newLine?"\n":"").append(component);//换行符有效
    }

    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int i ) {
        Component component = Component.translatable(entityType+".dialog"+i);

        return Component.literal("\n").append(component);//换行符有效
    }

    public MutableComponent buildDialogueDialog(EntityType<?> entityType, int i, String s) {
        Component component = Component.translatable(entityType + ".dialog" + i, s);
        return Component.literal("\n").append(component);
    }

}
