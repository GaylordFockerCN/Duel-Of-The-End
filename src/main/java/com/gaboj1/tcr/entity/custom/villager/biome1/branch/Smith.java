package com.gaboj1.tcr.entity.custom.villager.biome1.branch;

import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class Smith extends MushroomLineNpc {

    private final EntityType<?> entityType = TCREntities.SMITH.get();
    private final DialogueComponentBuilder BUILDER = new DialogueComponentBuilder(entityType);
    public Smith(EntityType<? extends Smith> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public String getResourceName() {
        return "talkable/pastoral_plain_villager1";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.SMITH.get().getDescriptionId());
    }

    @Override
    public void die(@NotNull DamageSource source) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(senderData.getBoolean("isBranchFinish")){
            //事件结束后的对话
            builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(5))
                    .addLeaf(BUILDER.buildDialogueOption(1), (byte) 2)//锻造请求
                    .addLeaf(BUILDER.buildDialogueOption(2), (byte) 3));//离开
        } else if(!senderData.getBoolean("smithTalked")){
            //初次对话
            builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(0))
                    .addLeaf(BUILDER.buildDialogueOption(0), (byte) 1)//接任务
                    .addLeaf(BUILDER.buildDialogueOption(1), (byte) 2)//锻造请求
                    .addLeaf(BUILDER.buildDialogueOption(2), (byte) 3));//离开
        } else if(senderData.getBoolean("smithTalked")){
            //交付任务
            if(senderData.getBoolean("killed")){
                builder.start(3)
                        .addFinalChoice(3, (byte) 4);//领取普通奖励
            } else if(senderData.getBoolean("heal")){
                builder.start(4)
                        .addFinalChoice(3, (byte) 5);//领取隐藏奖励
            } else {
                //领了但未完成
                builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(0))
                        .addLeaf(BUILDER.buildDialogueOption(0), (byte) 1)//接任务
                        .addLeaf(BUILDER.buildDialogueOption(1), (byte) 2)//锻造请求
                        .addLeaf(BUILDER.buildDialogueOption(2), (byte) 3));//离开
            }
        }
        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            //接任务
            case 1:
                SaveUtil.biome1.smithTalked = true;
                player.displayClientMessage(BUILDER.buildDialogue(this, BUILDER.buildDialogueAnswer(1)), false);
                break;
            case 2:
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.GUN_COMMON.get(), 1),
                                new ItemStack(TCRItems.ORICHALCUM.get(), 16),
                                new ItemStack(TCRItems.GUN_PLUS.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.TREE_DEMON_FRUIT.get(), 1),
                                new ItemStack(TCRItems.TREE_DEMON_HORN.get(), 1),
                                new ItemStack(TCRItems.TREE_SPIRIT_WAND.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRItems.AMMO.get(), 2),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 4),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 20),
                                new ItemStack(TCRItems.SPIRIT_WAND.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 5),
                                new ItemStack(TCRItems.ORICHALCUM_HELMET.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 8),
                                new ItemStack(TCRItems.ORICHALCUM_CHESTPLATE.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 7),
                                new ItemStack(TCRItems.ORICHALCUM_LEGGINGS.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 4),
                                new ItemStack(TCRItems.ORICHALCUM_BOOTS.get(), 1),
                                142857, 0, 0.02f)
                );
                break;
            case 3:
                break;
            case 4:
                ItemUtil.addItem(player,TCRItems.GUN_COMMON.get(),1);
                DataManager.gunGot.put(player,true);//存入得用玩家
                SaveUtil.biome1.isBranchFinish = true;
                break;
            case 5:
                ItemUtil.addItem(player,TCRItems.GUN_PLUS.get(),1);
                ItemStack ammo = TCRItems.AMMO.get().getDefaultInstance();
                ItemUtil.addItem(player,ammo.getItem(),64);
                DataManager.gunGot.put(player,true);//存入得用玩家
                SaveUtil.biome1.isBranchFinish = true;
                break;
        }
        setConversingPlayer(null);
    }

}
