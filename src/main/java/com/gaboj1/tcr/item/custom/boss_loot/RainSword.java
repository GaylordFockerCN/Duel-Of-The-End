package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.entity.custom.sword.RainCutterSwordEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 右键射三把剑
 */
public class RainSword extends MagicWeapon{

    public static final int CD = 40;

    public RainSword(Properties pProperties) {
        super(pProperties, 7.0);
    }

    /**
     * 实现右键召唤三把剑射向前方
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {

        if (p_41433_ instanceof ServerPlayer player) {
            for (int i = 0; i < 3; i++) {
                RainCutterSwordEntity sword = new RainCutterSwordEntity(player.getMainHandItem(), player.level(), i);
                sword.setOwner(player);
                sword.setNoGravity(true);
                sword.setBaseDamage(0.01);
                sword.setSilent(true);
                sword.pickup = AbstractArrow.Pickup.DISALLOWED;
                sword.setKnockback(1);//击退
                sword.setPierceLevel((byte) 5);//穿透
                sword.setPos(player.getPosition(1.0f).add(sword.getOffset()));
                sword.initDirection();
                player.level().playSound(null, sword.getOnPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 0.3f, 1);
                player.serverLevel().addFreshEntity(sword);
            }
            player.getCooldowns().addCooldown(player.getItemInHand(p_41434_).getItem(), CD);
        }

        return InteractionResultHolder.sidedSuccess(p_41433_.getItemInHand(p_41434_), p_41432_.isClientSide);
    }
}
