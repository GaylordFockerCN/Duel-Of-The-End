package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.entity.TCREntities;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class SpriteBowArrow extends Arrow {
    public SpriteBowArrow(Level level, LivingEntity owner) {
        super(TCREntities.SPRITE_BOW_ARROW.get(), level);
        setOwner(owner);
    }

    public SpriteBowArrow(EntityType<? extends SpriteBowArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(entity instanceof LivingEntity livingEntity){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
        }
    }
}
