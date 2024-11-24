package com.p1nero.dote.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 带有物品说明
 */
public class SimpleDescriptionItem extends Item implements DOTEKeepableItem{

    public SimpleDescriptionItem() {
        super(new Item.Properties().setNoRepair().rarity(Rarity.COMMON));
    }

    public SimpleDescriptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldKeep() {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }
}
