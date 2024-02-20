package com.gaboj1.tcr.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CopyResin extends Item {
    public CopyResin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        InteractionHand otherHand = pUsedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherHandItem =  pPlayer.getItemInHand(otherHand);
        if(otherHandItem.getItem()!=null){
            pPlayer.getItemInHand(pUsedHand).shrink(1);
            pPlayer.addItem(otherHandItem.copyWithCount(1));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
