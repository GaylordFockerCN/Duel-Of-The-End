package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class MiaoYin extends YueShiLineNpc {
    public MiaoYin(EntityType<? extends MiaoYin> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, -1);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public String getResourceName() {
        return "miao_yin";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCRModEntities.MIAO_YIN.get().getDescriptionId());
    }

    @Override
    public void tick() {
        if(!level().isClientSide){
            if(SaveUtil.biome2.talkToMaster){
                Wanderer wanderer;
//                wanderer.setPos(this.position());
//                level().addFreshEntity(wanderer);
                this.discard();
            }
        }
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        builder.setAnswerRoot(
                new TreeNode(BUILDER.buildDialogueAnswer(entityType,0))
                        .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,1))
                                .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4),BUILDER.buildDialogueOption(entityType,2))
                                        .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,5),BUILDER.buildDialogueOption(entityType,-1))
                                                .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,6),BUILDER.buildDialogueOption(entityType,-1))
                                                        .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,7),BUILDER.buildDialogueOption(entityType,3))
                                                                .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,8),BUILDER.buildDialogueOption(entityType,4))
                                                                        .addLeaf(BUILDER.buildDialogueOption(entityType,5),(byte) 1)//处决
                                                                        .addLeaf(BUILDER.buildDialogueOption(entityType,6),(byte) 2)//接任务
                                                                )//领任务
                                                        )
                                                )
                                        )
                                )
                        )
        );

        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public boolean hurt(DamageSource source, float v) {
        return false;
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){

        }
        setConversingPlayer(null);
    }

}
