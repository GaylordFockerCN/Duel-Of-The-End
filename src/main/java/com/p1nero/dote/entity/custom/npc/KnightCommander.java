package com.p1nero.dote.entity.custom.npc;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.archive.DataManager;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.p1nero.dote.client.gui.TreeNode;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.gameasset.skill.DOTESkills;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.item.EpicFightItems;

/**
 * 圣殿骑士长
 */
public class KnightCommander extends DOTENpc {
    DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(DOTEEntities.KNIGHT_COMMANDER.get());
    public KnightCommander(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        DataManager.BarunTalked.put(player, true);
        return super.mobInteract(player, hand);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder = new LinkListStreamDialogueScreenBuilder(this);
        if(!senderData.getBoolean("boss2fought")){

            //交易
            if(senderData.getBoolean("boss1fought")) {
                builder.start(0)
                        .addFinalChoice(-2, (byte) 2);
            } else {
                //击败boss前
                switch (DOTEArchiveManager.getWorldLevel()){
                    case 0:
                        builder.start(0)
                                .addChoice(0, 1)
                                .addChoice(1, 2)
                                .addChoice(2, 3)
                                .addFinalChoice(-1, (byte) 4);
                        break;
                    case 1:
                        builder.start(4)
                                .addChoice(3, 5)
                                .addChoice(4, 6)
                                .addFinalChoice(-1, (byte) 3);
                        break;
                    default:
                        builder.start(7)
                                .addChoice(5, 8)
                                .addFinalChoice(-1, (byte) 3);
                        break;
                }
            }
        } else {
            //击败boss2后
            switch (DOTEArchiveManager.getWorldLevel()){
                case 0:
                    builder.setAnswerRoot(new TreeNode(dBuilder.buildDialogueAnswer(0))
                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(9), dBuilder.buildDialogueOption(6))
                                    .addLeaf(dBuilder.buildDialogueOption(7), (byte) 1)//返回 且 完成轮回1
                                    .addLeaf(dBuilder.buildDialogueOption(8), (byte) 3)));//再等等
                    break;
                case 1:
                    builder.setAnswerRoot(new TreeNode(dBuilder.buildDialogueAnswer(0))
                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(10), dBuilder.buildDialogueOption(6))
                                    .addLeaf(dBuilder.buildDialogueOption(7), (byte) 1)//返回 且 完成轮回1
                                    .addLeaf(dBuilder.buildDialogueOption(8), (byte) 3)));//再等等
                    break;
                default:
                    builder.start(0)
                            .addChoice(6, 11)
                            .addFinalChoice(-1, (byte) 1);//强制遣返
                    break;
            }
        }
        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        //传送回去
        if(interactionID == 1){
            if(player instanceof ServerPlayer serverPlayer){
                ServerLevel serverLevel = serverPlayer.serverLevel();
                DOTEAdvancementData.getAdvancement("knight", serverPlayer);
                ItemUtil.clearItem(serverPlayer, DOTEItems.CORE_OF_HELL.get());
                ItemUtil.clearItem(serverPlayer, DOTEItems.HOLY_RADIANCE_SEED.get());
                DOTEArchiveManager.worldLevelUp(serverLevel, true);
            }
        }
        //锻造请求
        if(interactionID == 2){
            if(player instanceof ServerPlayer serverPlayer){
                startTrade(serverPlayer);
            }
        }
        if(interactionID == 4){
            if(!DataManager.BarunGiftGot.get(player)){
                ItemStack dodgeDisplay = new ItemStack(EpicFightItems.SKILLBOOK.get());
                dodgeDisplay.getOrCreateTag().putString("skill", DOTESkills.BETTER_DODGE_DISPLAY.toString());
                ItemUtil.addItem(player, dodgeDisplay);
                DataManager.BarunGiftGot.put(player, true);
            }
        }
        super.handleNpcInteraction(player, interactionID);
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        MerchantOffers offers = new MerchantOffers();
        ItemStack potion = new ItemStack(Items.POTION);
        PotionUtils.setPotion(potion, Potions.STRONG_HEALING);
        offers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_BLOCK, 1),
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 2),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(DOTEItems.P_KEY.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.SADDLE, 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                EpicFightItems.UCHIGATANA.get().getDefaultInstance(),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                potion,
                142857, 0, 0.02f));
        if(DOTEArchiveManager.getWorldLevel() >= 1){
            ItemStack sharp = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.SHARPNESS, 2));
            ItemStack protect = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 2));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADGRAIN.get(), 10),
                    new ItemStack(Items.PAPER, 10),
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                    new ItemStack(Items.LAPIS_LAZULI, 1),
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                    sharp,
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                    protect,
                    142857, 0, 0.02f));
        }
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 24),
                new ItemStack(DOTEItems.WKNIGHT_INGOT.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.WKNIGHT_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(DOTEItems.WKNIGHT_HELMET.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.WKNIGHT_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(DOTEItems.WKNIGHT_CHESTPLATE.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.WKNIGHT_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(DOTEItems.WKNIGHT_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.WKNIGHT_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                new ItemStack(DOTEItems.WKNIGHT_BOOTS.get(), 1),
                142857, 0, 0.02f));

        return offers;
    }

}
