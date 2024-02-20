package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
        pPlayer.getItemInHand(pUsedHand).shrink(1);
        pPlayer.addItem(otherHandItem.copyWithCount(1));
        Advancement _adv = pPlayer.getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"mass_production"));
        AdvancementProgress _ap = ((ServerPlayer) pPlayer).getAdvancements().getOrStartProgress(_adv);
        if (!_ap.isDone()) {
            for (String criteria : _ap.getRemainingCriteria())
                ((ServerPlayer) pPlayer).getAdvancements().award(_adv, criteria);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
