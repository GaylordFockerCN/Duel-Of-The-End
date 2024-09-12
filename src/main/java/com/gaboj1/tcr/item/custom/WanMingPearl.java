package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 没用的掉落物，带有物品说明
 */
public class WanMingPearl extends Item {

    public WanMingPearl() {
        super(new Properties().setNoRepair().rarity(Rarity.EPIC));
    }

    public WanMingPearl(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack p_41461_) {
        return SaveUtil.biome2.isBranchEnd ? Rarity.COMMON : Rarity.EPIC;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> components, @NotNull TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
        components.add(Component.translatable(this.getDescriptionId()+".usage" + (SaveUtil.biome2.isBranchEnd?"1":"")));
    }

}
