package com.gaboj1.tcr.entity.custom.Yggdrasil;

import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;

public class tree_clawEntity extends ThrowableProjectile implements ItemSupplier, GeoAnimatable {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
     public tree_clawEntity(EntityType<? extends tree_clawEntity> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public tree_clawEntity(Level world, LivingEntity thrower){
         super(TCRModEntities.TREE_CLAW.get(),thrower,world);
    }

    @Override
    public void tick() {
        super.tick();
        this.makeTrail();
    }

    private void makeTrail() {
        for (int i = 0; i < 1; i++) {
            double sx = 0.5 * (this.random.nextDouble() - this.random.nextDouble()) + this.getDeltaMovement().x();
            double sy = 0.5 * (this.random.nextDouble() - this.random.nextDouble()) + this.getDeltaMovement().y();
            double sz = 0.5 * (this.random.nextDouble() - this.random.nextDouble()) + this.getDeltaMovement().z();

            double dx = this.getX() + sx;
            double dy = this.getY() + sy;
            double dz = this.getZ() + sz;

            this.level().addParticle(ParticleTypes.FLAME, dx, dy, dz, sx * -0.25, sy * -0.25, sz * -0.25);
        }
    }

    @Override
    protected float getGravity() {
        return 0.0001F;
    }
    @Override
    public float getPickRadius() {
        return 1.0F;
    }
    @Override
    public boolean isOnFire() {
        return false;
    }
    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        super.onHitEntity(p_37259_);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }
}
