package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.TCRRarities;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wallet extends DropItem{
    public Wallet() {
        super(new Item.Properties().setNoRepair().stacksTo(1).rarity(TCRRarities.TE_PIN));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide){
            CompoundTag tag = stack.getOrCreateTag();
            int coinCnt = tag.getInt("coinCnt");
            int coinPlusCnt = tag.getInt("coinPlusCnt");
            int count = player.getInventory().countItem(TCRItems.DREAMSCAPE_COIN.get());
            int count2 = player.getInventory().countItem(TCRItems.DREAMSCAPE_COIN_PLUS.get());

            if(player.isShiftKeyDown()){
                ItemUtil.addItem(player, TCRItems.DREAMSCAPE_COIN.get(), coinCnt);
                ItemUtil.addItem(player, TCRItems.DREAMSCAPE_COIN_PLUS.get(), coinPlusCnt);
                tag.putInt("coinCnt", 0);
                tag.putInt("coinPlusCnt", 0);
                player.displayClientMessage(TheCasketOfReveriesMod.getInfo("wallet_get", coinPlusCnt, coinCnt), true);
            } else {
                ItemUtil.searchAndConsumeItem(player, TCRItems.DREAMSCAPE_COIN.get(), count);
                ItemUtil.searchAndConsumeItem(player, TCRItems.DREAMSCAPE_COIN_PLUS.get(), count2);
                tag.putInt("coinCnt", coinCnt + count);
                tag.putInt("coinPlusCnt", coinPlusCnt + count2);
                player.displayClientMessage(TheCasketOfReveriesMod.getInfo("wallet_put", count2, count), true);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        CompoundTag tag = itemStack.getOrCreateTag();
        int coinCnt = tag.getInt("coinCnt");
        int coinPlusCnt = tag.getInt("coinPlusCnt");
        list.add(TheCasketOfReveriesMod.getInfo("wallet_count", coinCnt));
        list.add(TheCasketOfReveriesMod.getInfo("wallet_plus_count", coinPlusCnt));
    }
}
