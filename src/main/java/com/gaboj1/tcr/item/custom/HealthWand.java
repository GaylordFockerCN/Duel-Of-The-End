package com.gaboj1.tcr.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HealthWand extends Item {
    public HealthWand() {
        super(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.UNCOMMON));
    }

    /**
     * 简单无脑粗暴加血hh
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        pPlayer.setHealth(pPlayer.getHealth() + 8);
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        setDamage(itemStack, getDamage(itemStack) + 1);
        if(getDamage(itemStack) == getMaxDamage(itemStack)){
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }


}
