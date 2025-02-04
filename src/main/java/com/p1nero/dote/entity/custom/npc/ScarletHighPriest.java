package com.p1nero.dote.entity.custom.npc;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.p1nero.dote.client.gui.TreeNode;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.List;

/**
 * 猩红大祭司
 */
public class ScarletHighPriest extends DOTENpc{
    public static final DialogueComponentBuilder D_BUILDER = new DialogueComponentBuilder(DOTEEntities.SCARLET_HIGH_PRIEST.get());
    public ScarletHighPriest(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {

        LinkListStreamDialogueScreenBuilder builder = new LinkListStreamDialogueScreenBuilder(this);
        switch (DOTEArchiveManager.getWorldLevel()){
            case 0:
                builder.setAnswerRoot(new TreeNode(D_BUILDER.buildDialogueAnswer(0))
                        .addChild(new TreeNode(D_BUILDER.buildDialogueAnswer(1), D_BUILDER.buildDialogueOption(0))
                                .addChild(new TreeNode(D_BUILDER.buildDialogueAnswer(2), D_BUILDER.buildDialogueOption(1))
                                        .addChild(new TreeNode(D_BUILDER.buildDialogueAnswer(3), D_BUILDER.buildDialogueOption(2))
                                                .addLeaf(D_BUILDER.buildDialogueOption(-1), (byte) 2))))
                        .addLeaf(D_BUILDER.buildDialogueOption(-2), (byte) 1));//骸骨交易
                break;
            case 1:
                builder.setAnswerRoot(new TreeNode(D_BUILDER.buildDialogueAnswer(4))
                        .addChild(new TreeNode(D_BUILDER.buildDialogueAnswer(5), D_BUILDER.buildDialogueOption(3))
                                .addChild(new TreeNode(D_BUILDER.buildDialogueAnswer(6), D_BUILDER.buildDialogueOption(4))
                                                .addLeaf(D_BUILDER.buildDialogueOption(-1), (byte) 2)))
                        .addLeaf(D_BUILDER.buildDialogueOption(-2), (byte) 1));//骸骨交易
                break;
            default:
                builder.setAnswerRoot(new TreeNode(D_BUILDER.buildDialogueAnswer(7))
                        .addChild(new TreeNode(D_BUILDER.buildDialogueAnswer(8), D_BUILDER.buildDialogueOption(5))
                                                .addLeaf(D_BUILDER.buildDialogueOption(-1), (byte) 2))
                        .addLeaf(D_BUILDER.buildDialogueOption(-2), (byte) 1));//骸骨交易
                break;
        }
        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        if(interactionID == 1){
            if(player instanceof ServerPlayer serverPlayer){
                if(DOTEArchiveManager.BIOME_PROGRESS_DATA.isBoss2fought()){
                    startTrade(serverPlayer);
                } else {
                    player.displayClientMessage(D_BUILDER.buildDialogue(this, D_BUILDER.buildDialogueAnswer(-1)), false);
                }
            }
        }
        super.handleNpcInteraction(player, interactionID);
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        MerchantOffers offers = new MerchantOffers();

        if(DOTEArchiveManager.getWorldLevel() >= 1){
            ItemStack sharp = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.SHARPNESS, 2));
            ItemStack protect = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 2));
            ItemStack stunImmunity = PotionUtils.setCustomEffects(Items.POTION.getDefaultInstance(), List.of(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 300, 0)));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADGRAIN.get(), 10),
                    new ItemStack(Items.PAPER, 10),
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                    new ItemStack(Items.LAPIS_LAZULI, 1),
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                    sharp,
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                    protect,
                    142857, 0, 0.02f));
            offers.add(new MerchantOffer(
                    new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                    stunImmunity,
                    142857, 0, 0.02f));
        }

        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                new ItemStack(DOTEItems.NETHERROT_INGOT.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 10),
                new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 4),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.NETHERROT_INGOT.get(), 1),
                new ItemStack(Items.NETHERITE_HELMET, 1),
                new ItemStack(DOTEItems.NETHERITEROT_HELMET.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.NETHERROT_INGOT.get(), 1),
                new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
                new ItemStack(DOTEItems.NETHERITEROT_CHESTPLATE.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.NETHERROT_INGOT.get(), 1),
                new ItemStack(Items.NETHERITE_LEGGINGS, 1),
                new ItemStack(DOTEItems.NETHERITEROT_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offers.add(new MerchantOffer(
                new ItemStack(DOTEItems.NETHERROT_INGOT.get(), 1),
                new ItemStack(Items.NETHERITE_BOOTS, 1),
                new ItemStack(DOTEItems.NETHERITEROT_BOOTS.get(), 1),
                142857, 0, 0.02f));

        return offers;
    }
}
