package com.gaboj1.tcr.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BasicResin extends Item {

    protected int repairValue = 1;

    public BasicResin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        InteractionHand otherHand = pUsedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherHandItem =  pPlayer.getItemInHand(otherHand);
        ItemStack handItem = pPlayer.getItemInHand(pUsedHand);
        int _repairValue = this.repairValue;
        //只能用高级树脂修复法宝，并且会有折扣
        if(otherHandItem.getItem() instanceof TreeSpiritWand){
            if(! (handItem.getItem() instanceof SuperResin)){
                return InteractionResultHolder.fail(handItem);
            }
            _repairValue /= 9;
        }
        //otherHandItem.getItem() != null &&//TODO 测试空手会不会消耗
        if(otherHandItem.isRepairable()){
            otherHandItem.setDamageValue(otherHandItem.getDamageValue()-_repairValue);
            handItem.shrink(1);
        }

        return InteractionResultHolder.success(handItem);
    }
}
