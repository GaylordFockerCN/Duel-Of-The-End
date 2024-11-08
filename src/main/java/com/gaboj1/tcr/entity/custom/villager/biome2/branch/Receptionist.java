package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class Receptionist extends YueShiLineNpc {
    public Receptionist(EntityType<? extends Receptionist> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public String getResourceName() {
        return "receptionist";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.RECEPTIONIST.get().getDescriptionId());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(senderData.getBoolean("trialTalked2") || (senderData.getBoolean("miaoYinTalked2") && !senderData.getBoolean("chooseEnd2"))){
            builder.start(2)
                    .addFinalChoice(1, (byte) -1);
        } else if(senderData.getBoolean("afterTrial") && !senderData.getBoolean("talkToMaster")){
            builder.start(3)
                    .addFinalChoice(1, (byte) -1);
        } else if(!senderData.getBoolean("talkToMaster")){
            //正常对话
            builder.start(0)
                    .addChoice(0, 1)
                    .addFinalChoice(1, (byte) -1);
        } else {
            //乐师被抓后的对话
            builder.start(4)
                    .addChoice(2, 5)
                    .addFinalChoice(3, (byte) 1);
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        if (interactionID == 1) {
            chat(BUILDER.buildDialogueAnswer(entityType, 6));
        }
        setConversingPlayer(null);
    }

    /**
     * 如果坏事暴露则可以打
     */
    @Override
    public boolean hurt(DamageSource source, float v) {
        if(TCRArchiveManager.biome2.talkToMaster){
            return super.hurt(source, v);
        }
        return false;
    }

}
