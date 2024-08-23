package com.gaboj1.tcr.item.custom.gun;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GunPlus extends GunCommon{
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }
}
