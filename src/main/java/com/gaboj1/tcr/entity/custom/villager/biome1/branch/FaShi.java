package com.gaboj1.tcr.entity.custom.villager.biome1.branch;

import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FaShi extends MushroomLineNpc {

    private final EntityType<?> entityType = TCREntities.ELIA.get();
    private final DialogueComponentBuilder BUILDER = new DialogueComponentBuilder(entityType);
    public FaShi(EntityType<? extends FaShi> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public String getResourceName() {
        return "fa_shi";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.ELIA.get().getDescriptionId());
    }

    @Override
    public void die(@NotNull DamageSource source) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(0))
                .addLeaf(BUILDER.buildDialogueOption(0), (byte) 1)//交易
                .addLeaf(BUILDER.buildDialogueOption(1), (byte) 0));//离开
        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        if (interactionID == 1) {
            player.displayClientMessage(BUILDER.buildDialogue(this, BUILDER.buildDialogueAnswer(1)), false);
            ItemStack poisonResistance = PotionUtils.setCustomEffects(Items.POTION.getDefaultInstance(), List.of(new MobEffectInstance(TCREffects.POISON_RESISTANCE.get(), 800)));
            ItemStack orichalcum1 = PotionUtils.setCustomEffects(Items.POTION.getDefaultInstance(), List.of(new MobEffectInstance(TCREffects.ORICHALCUM.get(), 800)));
            ItemStack orichalcum2 = PotionUtils.setCustomEffects(Items.POTION.getDefaultInstance(), List.of(new MobEffectInstance(TCREffects.ORICHALCUM.get(), 3200, 1)));
            startCustomTrade(player,
//                    new MerchantOffer(
//                            new ItemStack(TCRItems.TREE_DEMON_FRUIT.get(), 1),
//                            new ItemStack(TCRItems.TREE_DEMON_HORN.get(), 1),
//                            new ItemStack(TCRItems.TREE_SPIRIT_WAND.get(), 1),
//                            142857, 0, 0.02f),// NOTE 改用锻造
                    new MerchantOffer(
                            new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 4),
                            new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 20),
                            new ItemStack(TCRItems.SPRITE_WAND.get(), 1),
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRItems.DREAMSCAPE_COIN_PLUS.get(), 1),
                            new ItemStack(Items.BOW, 1),
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 4),
                            new ItemStack(Items.BOW, 1),
                            new ItemStack(TCRItems.SPRITE_BOW.get(), 1),
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 1),
                            new ItemStack(TCRItems.ORICHALCUM.get(), 1),
                            orichalcum1,
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 1),
                            new ItemStack(TCRItems.GOD_ORICHALCUM.get(), 1),
                            orichalcum2,
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRItems.BLUE_MUSHROOM.get(), 9),
                            poisonResistance,
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRBlocks.LAZY_ROSE.get(), 9),
                            PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SLOWNESS),
                            142857, 0, 0.02f),
                    new MerchantOffer(
                            new ItemStack(TCRBlocks.THIRST_BLOOD_ROSE.get(), 9),
                            PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING),
                            142857, 0, 0.02f)
            );
        }
        setConversingPlayer(null);
    }

}
