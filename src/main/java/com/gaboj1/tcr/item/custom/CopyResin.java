package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.datagen.TCRAdvancementData;
import net.minecraft.network.chat.Component;
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

            TCRAdvancementData.getAdvancement("mass_production",(ServerPlayer) pPlayer);

            if(otherHandItem.isEmpty()) {
                TCRAdvancementData.getAdvancement("so_rich", (ServerPlayer) pPlayer);
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
