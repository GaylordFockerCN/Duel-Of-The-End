package com.p1nero.dote.item.custom;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StarCoreItem extends SimpleKeepableFoilDescriptionItem{
    public StarCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if(player.isShiftKeyDown() && !level.isClientSide){
            boolean old = DOTEArchiveManager.BIOME_PROGRESS_DATA.isGuideSummoned();
            DOTEArchiveManager.clear();
            DOTEArchiveManager.BIOME_PROGRESS_DATA.setGuideSummoned(old);
            player.displayClientMessage(DuelOfTheEndMod.getInfo("tip12"), false);
            if(!player.isCreative()){
                player.getItemInHand(hand).shrink(1);
            }
        }
        return super.use(level, player, hand);
    }
}
