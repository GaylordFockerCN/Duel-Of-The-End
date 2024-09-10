package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        super.tick();
        getNavigation().stop();//盲人行动不便很合理吧
        if(!level().isClientSide){
            //泄密后把乐师换成流浪者
            if(SaveUtil.biome2.talkToMaster){
                Wanderer wanderer = TCRModEntities.WANDERER.get().create(level());
                assert wanderer != null;
                wanderer.setPos(this.position());
                level().addFreshEntity(wanderer);
                this.discard();
            }
        }
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);

        TreeNode sameNode1 = new TreeNode(BUILDER.buildDialogueAnswer(entityType,12),BUILDER.buildDialogueOption(entityType,14))
                .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,13),BUILDER.buildDialogueOption(entityType,15))
                        .addLeaf(BUILDER.buildDialogueOption(entityType,16), (byte) 5)//接取任务
                );

        if(!senderData.getBoolean("miaoYinTalked1")){
            //初次对话
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,0))
                            .addLeaf(BUILDER.buildDialogueOption(entityType,1), (byte) 1)//施舍
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,2),BUILDER.buildDialogueOption(entityType,0))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,2))
                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4),BUILDER.buildDialogueOption(entityType,3))
                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,4), (byte) 2)//偷钱
                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,5), BUILDER.buildDialogueOption(entityType,5))
                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,6),BUILDER.buildDialogueOption(entityType,6))
                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,7),BUILDER.buildDialogueOption(entityType,7))
                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,8),BUILDER.buildDialogueOption(entityType,8))
                                                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,9), (byte) 3)//离去
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,9),BUILDER.buildDialogueOption(entityType,10))
                                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,11), (byte) 4)//我喜欢你
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,11),BUILDER.buildDialogueOption(entityType,12))
                                                                                                    .addChild(sameNode1)
                                                                                            )
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,11),BUILDER.buildDialogueOption(entityType,13))
                                                                                                    .addChild(sameNode1)
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        } else if(!senderData.getBoolean("shangRenTalked") && !senderData.getBoolean("afterTrial")){
            //接任务后再次对话，但还没和商人对话也没通过试炼
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,11))
                            .addChild(sameNode1)
            );
        } else if(senderData.getBoolean("shangRenTalked")){
            //和商人对话后
            builder.start(16)
                    .addChoice(17, 17)
                    .addChoice(18, 18)
                    .addFinalChoice(19, (byte) 6);
        } else if(senderData.getBoolean("afterTrial") && !senderData.getBoolean("talkToMaster")){
            //取得夜明珠后

            return;
        }

        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case 1:
                chat(BUILDER.buildDialogueAnswer(entityType, 1));
                break;
            case 2, 3:
                chat(BUILDER.buildDialogueAnswer(entityType, 0));
                break;
            case 4:
                chat(BUILDER.buildDialogueAnswer(entityType, 10));
                break;
            case 5:
                chat(BUILDER.buildDialogueAnswer(entityType, 14));
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 15), false);//任务提示
                SaveUtil.biome2.miaoYinTalked1 = true;
                break;
            case 6:
                chat(BUILDER.buildDialogueAnswer(entityType, 19));
                if(!DataManager.isMiaoYinGifted.getBool(player)){
                    DataManager.isMiaoYinGifted.putBool(player, true);
                    player.addItem(TCRModItems.GOLDEN_WIND_AND_DEW.get().getDefaultInstance());
                }
                break;

        }
        setConversingPlayer(null);
    }

}
