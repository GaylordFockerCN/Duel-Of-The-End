package com.gaboj1.tcr.entity.custom.projectile;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.util.FireworkUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SpriteBowArrow extends SpectralArrow {
    private boolean fired;
    public SpriteBowArrow(Level level, LivingEntity owner) {
        super(TCREntities.SPRITE_BOW_ARROW.get(), level);
        setOwner(owner);
    }

    public SpriteBowArrow(EntityType<? extends SpriteBowArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if(tickCount == 60 && !fired){
            level().createFireworks(position().x, position().y, position().z, 1,1,1, FireworkUtil.RANDOM[random.nextInt(FireworkUtil.RANDOM.length)]);
            level().playLocalSound(position().x, position().y, position().z, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.BLOCKS, 4.0F, (1.0F + (level().random.nextFloat() - level().random.nextFloat()) * 0.2F) * 0.7F, false);
            fired = true;
        }
        if(fired && onGround()){
            discard();
        }
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        Vec3 hitPos = hitResult.getLocation();
        if(!fired){
            level().createFireworks(hitPos.x, hitPos.y, hitPos.z, 1,1,1, FireworkUtil.RANDOM[random.nextInt(FireworkUtil.RANDOM.length)]);
            level().playLocalSound(hitPos.x, hitPos.y, hitPos.z, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.BLOCKS, 4.0F, (1.0F + (level().random.nextFloat() - level().random.nextFloat()) * 0.2F) * 0.7F, false);
            fired = true;
        }
        super.onHit(hitResult);
    }
}
