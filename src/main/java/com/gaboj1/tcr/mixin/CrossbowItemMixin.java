package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 * 防止连弩射的箭可以捡
 */
@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Shadow
    private static AbstractArrow getArrow(Level p_40915_, LivingEntity p_40916_, ItemStack p_40917_, ItemStack p_40918_) {
        ArrowItem arrowitem = (ArrowItem)(p_40918_.getItem() instanceof ArrowItem ? p_40918_.getItem() : Items.ARROW);
        AbstractArrow abstractarrow = arrowitem.createArrow(p_40915_, p_40918_, p_40916_);
        if (p_40916_ instanceof Player) {
            abstractarrow.setCritArrow(true);
        }

        abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrow.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, p_40917_);
        if (i > 0) {
            abstractarrow.setPierceLevel((byte)i);
        }

        return abstractarrow;
    }

    @Inject(method = "shootProjectile", at = @At("HEAD"), cancellable = true)
    private static void inject(Level p_40895_, LivingEntity p_40896_, InteractionHand p_40897_, ItemStack p_40898_, ItemStack p_40899_, float p_40900_, boolean p_40901_, float p_40902_, float p_40903_, float p_40904_, CallbackInfo ci){
        if(p_40898_.is(TCRItems.ORICHALCUM_CROSSBOW.get())){
            if (!p_40895_.isClientSide) {
                boolean flag = p_40899_.is(Items.FIREWORK_ROCKET);
                Projectile projectile;
                if (flag) {
                    projectile = new FireworkRocketEntity(p_40895_, p_40899_, p_40896_, p_40896_.getX(), p_40896_.getEyeY() - 0.15000000596046448, p_40896_.getZ(), true);
                } else {
                    projectile = getArrow(p_40895_, p_40896_, p_40898_, p_40899_);
                    ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                if (p_40896_ instanceof CrossbowAttackMob crossbowattackmob) {
                    crossbowattackmob.shootCrossbowProjectile(Objects.requireNonNull(crossbowattackmob.getTarget()), p_40898_, projectile, p_40904_);
                } else {
                    Vec3 vec31 = p_40896_.getUpVector(1.0F);
                    Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(p_40904_ * 0.017453292F), vec31.x, vec31.y, vec31.z);
                    Vec3 vec3 = p_40896_.getViewVector(1.0F);
                    Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
                    projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), p_40902_, p_40903_);
                }

                p_40898_.hurtAndBreak(flag ? 3 : 1, p_40896_, (p_40858_) -> {
                    p_40858_.broadcastBreakEvent(p_40897_);
                });
                p_40895_.addFreshEntity(projectile);
                p_40895_.playSound(null, p_40896_.getX(), p_40896_.getY(), p_40896_.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, p_40900_);
            }
            ci.cancel();
        }
    }
}
