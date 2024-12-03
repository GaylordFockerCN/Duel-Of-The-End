package com.p1nero.dote.item.custom;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.item.WeaponItem;

import java.util.List;

public class RotGreatswordItem extends WeaponItem {
    public RotGreatswordItem(Tier tier, int damageIn, float speedIn, Properties builder) {
        super(tier, damageIn, speedIn, builder);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(itemStack, level, entity, p_41407_, p_41408_);
        if(entity instanceof Player player && !player.isCreative()){
            player.setHealth(player.getHealth() - 0.15F);
            player.drop(itemStack, false);
            itemStack.setCount(0);
            player.displayClientMessage(DuelOfTheEndMod.getInfo("tip9"), true);
        }
    }
}
