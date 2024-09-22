package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.item.custom.DropItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HealthWand extends DropItem {
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
        setDamage(itemStack, getDamage(itemStack) + pPlayer.getRandom().nextInt(16));
        pLevel.playSound(null , pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS,1,1);
        if(getDamage(itemStack) >= getMaxDamage(itemStack)){
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

}
