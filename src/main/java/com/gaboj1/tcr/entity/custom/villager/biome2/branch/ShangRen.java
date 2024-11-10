package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.util.BookManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

/**
 * 乐师支线中的商人
 */
public class ShangRen extends YueShiLineNpc {
    public ShangRen(EntityType<? extends ShangRen> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public String getResourceName() {
        return "talkable/villager2-2";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.SHANG_REN.get().getDescriptionId());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);

        if(senderData.getBoolean("miaoYinTalked1")){
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,0))
                            .addLeaf(BUILDER.buildDialogueOption(entityType,-1), (byte) 1)//普通交易
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,2), BUILDER.buildDialogueOption(entityType,0))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3), BUILDER.buildDialogueOption(entityType,1))
                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4), BUILDER.buildDialogueOption(entityType,2))
                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,5), BUILDER.buildDialogueOption(entityType,3))
                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,6), BUILDER.buildDialogueOption(entityType,4))
                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,7), BUILDER.buildDialogueOption(entityType,5))
                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,8), BUILDER.buildDialogueOption(entityType,6))
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,9), BUILDER.buildDialogueOption(entityType,7))
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,10),BUILDER.buildDialogueOption(entityType,8))
                                                                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,9), (byte) 2)
                                                                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,10), (byte) 3)
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
        } else {
            builder.start(0)
                    .addFinalChoice(0, (byte) 1);
        }

        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case 1:
                //普通交易
                chat(BUILDER.buildDialogueAnswer(entityType, 1));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN_PLUS.get(), 1),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 9),
                                16, 0, 0),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 9),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN_PLUS.get(), 1),
                                16, 0, 0)
                );
                break;
            case 2:
                TCRArchiveManager.biome2.shangRenTalked = true;
                //正常给
                chat(BUILDER.buildDialogueAnswer(entityType, 11, "(" + BiomeMap.getInstance().getVillage2()[1].x + ", " + BiomeMap.getInstance().getVillage2()[1].y + ")"));
                ItemUtil.addItem(player,BookManager.BU_GAO.get().getItem(), 1);
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 13), false);
                break;
            case 3:
                TCRArchiveManager.biome2.shangRenTalked = true;
                //杀了
                chat(BUILDER.buildDialogueAnswer(entityType, 12));
                ItemUtil.addItem(player,BookManager.BU_GAO.get().getItem(),1);
                this.setHealth(0);
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 13), false);
                break;

        }
        setConversingPlayer(null);
    }

}
