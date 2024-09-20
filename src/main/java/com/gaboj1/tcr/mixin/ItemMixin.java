package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;

@Mixin(Item.class)
public class ItemMixin{

    /**
     * 在维度里右键下界之星以返回。监听物品使用事件无效，因为下界之星无法右键。。
     */
    @Inject(method = "use", at = @At("HEAD"))
    private void netherStarPortal(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        if(!level.isClientSide && player.getItemInHand(hand).is(Items.NETHER_STAR) && level.dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY && player.getServer() != null){
            player.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                ServerLevel overworld = player.getServer().overworld();
                Vec3 pos = tcrPlayer.getBedPointBeforeEnter().getCenter();
                player.teleportTo(overworld, pos.x, pos.y, pos.z, new HashSet<>(), player.getXRot(), player.getYRot());
            }));
        }
    }
}
