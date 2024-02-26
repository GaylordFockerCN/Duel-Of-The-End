package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.init.TCRModItems;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BasicResin extends Item {

    private int scale = 1;
    public static final int INTERMEDIATE_RESIN = 9;
    public static final int ADVANCED_RESIN = 9*9;
    public static final int SUPER_RESIN = 9*9*9;

    public BasicResin(Properties pProperties, int scale) {
        super(pProperties);
        this.scale = scale;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        InteractionHand otherHand = pUsedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherHandItem =  pPlayer.getItemInHand(otherHand);
        ItemStack handItem = pPlayer.getItemInHand(pUsedHand);
        int _repairValue = this.scale * TCRConfig.REPAIR_VALUE.get();
        //只能用高级树脂修复法宝，并且会有折扣
        if(otherHandItem.getItem() instanceof MagicWeapon){
            if(! (scale == SUPER_RESIN)){
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(TCRModItems.BASIC_RESIN.get().getDescriptionId()+".usage",scale*TCRConfig.REPAIR_VALUE.get()));
    }
}
