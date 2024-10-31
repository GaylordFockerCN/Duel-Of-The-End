package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.TCRRarities;
import com.gaboj1.tcr.item.custom.boss_loot.MagicWeapon;
import net.minecraft.network.chat.Component;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class BasicResin extends Item {

    private final int scale;
    public static final int INTERMEDIATE_RESIN = 9;
    public static final int ADVANCED_RESIN = 9 * 9;
    public static final int SUPER_RESIN = 9 * 9 * 9;
    public BasicResin(Properties pProperties, int scale) {
        super(pProperties);
        this.scale = scale;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {

        InteractionHand otherHand = pUsedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherHandItem =  pPlayer.getItemInHand(otherHand);
        ItemStack handItem = pPlayer.getItemInHand(pUsedHand);
        int _repairValue = this.scale * TCRConfig.REPAIR_VALUE.get();
        //只能用高级树脂修复高级材料，并且会有折扣
        if(otherHandItem.getRarity().equals(TCRRarities.XIAN_PIN) || otherHandItem.getRarity().equals(TCRRarities.SHEN_ZHEN) || otherHandItem.getRarity().equals(Rarity.EPIC)){
            if(!(scale == SUPER_RESIN)){
                return InteractionResultHolder.fail(handItem);
            }
            _repairValue /= 9;
        }
        if(otherHandItem.isRepairable() && otherHandItem.getDamageValue() != 0){
            otherHandItem.setDamageValue(otherHandItem.getDamageValue()-_repairValue);
            handItem.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(handItem,pLevel.isClientSide);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(TCRItems.BASIC_RESIN.get().getDescriptionId()+".usage",scale*TCRConfig.REPAIR_VALUE.get()));
    }
}
