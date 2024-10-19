package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.effect.TCREffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PurificationTalisman extends DropItem{
    public PurificationTalisman(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        if(entity instanceof LivingEntity livingEntity){
            if(!livingEntity.hasEffect(TCREffects.POISON_RESISTANCE.get())){
                livingEntity.addEffect(new MobEffectInstance(TCREffects.POISON_RESISTANCE.get(), 200));
            }
        }
        super.inventoryTick(itemStack, level, entity, p_41407_, p_41408_);
    }
}
