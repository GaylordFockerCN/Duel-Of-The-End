package com.p1nero.dote.item.custom;

import com.p1nero.dote.item.DOTERarities;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class TeleportKeyItem extends SimpleDescriptionFoilItem{
    private final Supplier<BlockPos> destination;
    public TeleportKeyItem(Supplier<BlockPos> destination){
        super(new Item.Properties().setNoRepair().fireResistant().stacksTo(1).rarity(DOTERarities.TE_PIN));
        this.destination = destination;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(level instanceof ServerLevel serverLevel && player.isShiftKeyDown()){
            boolean inDim = serverLevel.dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY;
            if(inDim){
                player.changeDimension(Objects.requireNonNull(serverLevel.getServer().overworld()),
                        new DOTETeleporter(destination.get()));
            } else {
                player.changeDimension(Objects.requireNonNull(serverLevel.getServer().getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY)),
                        new DOTETeleporter(destination.get()));

            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);

        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BOW;
    }

}
