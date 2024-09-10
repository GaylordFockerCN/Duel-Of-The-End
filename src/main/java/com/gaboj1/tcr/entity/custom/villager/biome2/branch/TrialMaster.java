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

public class TrialMaster extends YueShiLineNpc {
    public TrialMaster(EntityType<? extends TrialMaster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, -1);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public String getResourceName() {
        return "trial_master";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCRModEntities.TRIAL_MASTER.get().getDescriptionId());
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(!senderData.getBoolean("trialTalked1")){
            //初次对话
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,0))
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,1),BUILDER.buildDialogueOption(entityType,0))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,2),BUILDER.buildDialogueOption(entityType,1))
                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,2))
                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4), BUILDER.buildDialogueOption(entityType,3))
                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,5),BUILDER.buildDialogueOption(entityType,4))
                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,6),BUILDER.buildDialogueOption(entityType,5))
                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,7),BUILDER.buildDialogueOption(entityType,6))
                                                                                    .execute((byte) -1)
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,8),BUILDER.buildDialogueOption(entityType,7))
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,9),BUILDER.buildDialogueOption(entityType,10))
                                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,10),BUILDER.buildDialogueOption(entityType,11))
                                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,11),BUILDER.buildDialogueOption(entityType,12))
                                                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,12),BUILDER.buildDialogueOption(entityType,13))
                                                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,13),BUILDER.buildDialogueOption(entityType,14))
                                                                                                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,15), (byte) 1)
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,9),BUILDER.buildDialogueOption(entityType,8))

                                                                                    )
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,9),BUILDER.buildDialogueOption(entityType,9))

                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        }

        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case -1:
                //给予夜明珠 TODO
                
                return;
            case 1:
                //选择告诉试炼主盲女的位置
                SaveUtil.biome2.talkToMaster = true;
                break;
            case 2:

                break;
        }
        setConversingPlayer(null);
    }

    @Override
    public boolean hurt(DamageSource source, float v) {
        if(SaveUtil.biome2.talkToMaster){
            return super.hurt(source, v);
        }
        return false;
    }

}
