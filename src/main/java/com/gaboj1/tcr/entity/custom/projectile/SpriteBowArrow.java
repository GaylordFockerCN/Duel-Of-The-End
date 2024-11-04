package com.gaboj1.tcr.entity.custom.projectile;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.util.FireworkUtil;
import net.minecraft.core.particles.ParticleTypes;
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
        setPos(owner.getEyePosition().add(owner.getViewVector(1.0F).normalize().scale(2)));//从眼前发射
        setGlowingTag(true);
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
        if(fired && (onGround() || inGround)){
            discard();
        }
        Vec3 vec3 = this.getDeltaMovement();
        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;
        if (level().isClientSide && !(onGround()  || inGround)) {
            for(int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.FIREWORK, this.getX() + d5 * (double)i / 4.0, this.getY() + d6 * (double)i / 4.0, this.getZ() + d1 * (double)i / 4.0, -d5, -d6 + 0.2, -d1);
            }
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
