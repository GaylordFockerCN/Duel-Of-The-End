package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RecallScroll extends DropItem{
    public RecallScroll(){
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if(!level.isClientSide && level.dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY){
            player.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                if(tcrPlayer.getLastPortalBlockPos().getCenter().equals(BlockPos.ZERO.getCenter())){
                    return;
                }
                BlockPos last = tcrPlayer.getLastPortalBlockPos().offset(player.getRandom().nextInt(7) - 3, 2, player.getRandom().nextInt(7) - 3);
                player.teleportTo(last.getX(), last.getY(), last.getZ());
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 1, 1);
            }));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }
}
