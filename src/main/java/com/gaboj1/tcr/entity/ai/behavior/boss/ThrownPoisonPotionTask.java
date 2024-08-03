package com.gaboj1.tcr.entity.ai.behavior.boss;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ThrownPoisonPotionTask extends TCRVillagerRetaliateTask {

    @Override
    protected boolean checkRange(TCRVillager tcrVillager, LivingEntity target) {
        return tcrVillager.distanceTo(target) <= 5;
    }

    @Override
    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level) {
        Vec3 $$2 = target.getDeltaMovement();
        double $$3 = target.getX() + $$2.x - tcrVillager.getX();
        double $$4 = target.getEyeY() - 1.100000023841858 - tcrVillager.getY();
        double $$5 = target.getZ() + $$2.z - tcrVillager.getZ();
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        ThrownPotion potion = new ThrownPotion(level, tcrVillager);
        potion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.POISON));
        potion.setXRot(potion.getXRot() - -20.0F);
        potion.shoot($$3, $$4 + $$6 * 0.2, $$5, 0.75F, 8.0F);
        level.playSound(null, tcrVillager.getX(), tcrVillager.getY(), tcrVillager.getZ(), SoundEvents.WITCH_THROW, SoundSource.BLOCKS, 1.0F, 0.8F + new Random().nextFloat() * 0.4F);
        level.addFreshEntity(potion);
    }

}
