package com.p1nero.dote.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.item.WeaponItem;

import java.util.List;

public class BalmungItem extends WeaponItem {
    public BalmungItem(Tier tier, int damageIn, float speedIn, Properties builder) {
        super(tier, damageIn, speedIn, builder);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }

}
