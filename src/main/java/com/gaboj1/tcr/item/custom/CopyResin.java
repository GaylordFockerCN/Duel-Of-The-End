package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopyResin extends Item {
    public CopyResin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        InteractionHand otherHand = pUsedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherHandItem =  pPlayer.getItemInHand(otherHand);
        ItemStack handItem = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide){
            pPlayer.getItemInHand(pUsedHand).shrink(1);
            pPlayer.addItem(otherHandItem.copyWithCount(1));

            Advancement _adv = ((ServerPlayer) pPlayer).getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"mass_production"));
            AdvancementProgress _ap = ((ServerPlayer) pPlayer).getAdvancements().getOrStartProgress(_adv);

            if (!_ap.isDone()) {
                for (String criteria : _ap.getRemainingCriteria())
                    ((ServerPlayer) pPlayer).getAdvancements().award(_adv, criteria);
            }

            if(otherHandItem.isEmpty()) {
                Advancement _adv2 = ((ServerPlayer) pPlayer).getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "so_rich"));
                AdvancementProgress _ap2 = ((ServerPlayer) pPlayer).getAdvancements().getOrStartProgress(_adv2);
                if (!_ap2.isDone()) {
                    for (String criteria : _ap2.getRemainingCriteria())
                        ((ServerPlayer) pPlayer).getAdvancements().award(_adv2, criteria);
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(handItem, pLevel.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage"));
    }
}
