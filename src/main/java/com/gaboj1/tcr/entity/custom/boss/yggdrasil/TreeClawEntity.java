package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

public class TreeClawEntity extends Mob implements GeoEntity {
    private YggdrasilEntity yggdrasilEntity;
    private Player target;
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int catchTimer;
    private final int catchTimerMax = 10;
    private boolean isCatching;
    public TreeClawEntity(EntityType<? extends TreeClawEntity> p_37466_, Level p_37467_) {
         super(p_37466_, p_37467_);
         SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public TreeClawEntity(Level world, YggdrasilEntity thrower, Player target){
        super(TCRModEntities.TREE_CLAW.get(),world);
        yggdrasilEntity = thrower;
        this.target = target;
        catchTimer = catchTimerMax;
        isCatching = false;
        setNoGravity(true);
        catchPlayer();
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .build();
    }

    @Override
    public void tick() {
        super.tick();
        catchTimer--;
        if(catchTimer < 0 && !isCatching && this.target != null && !level().isClientSide && checkHit(target.getOnPos(),1)){
            target.hurt(level().damageSources().magic(),10f);
            target.setSpeed(0);// TODO 禁锢
            isCatching = true;
        }
        if(catchTimer < -catchTimerMax * 10){
            this.discard();
        }
    }
    public boolean checkHit(BlockPos pos1, int offSet){
        BlockPos pos2 = getOnPos();
        return pos1.getX()<=pos2.getX()+offSet && pos1.getX()>=pos2.getX()-offSet
                && pos1.getZ()<=pos2.getZ()+offSet && pos1.getZ()>=pos2.getZ()-offSet;
    }

    @Override
    public void die(DamageSource p_21014_) {
        if(p_21014_.getEntity() instanceof Player){
            this.yggdrasilEntity.setCanBeHurt();
        }
        super.die(p_21014_);
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

    public void catchPlayer(){
        triggerAnim("Catch","catch");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Catch", 0, state -> PlayState.STOP)
                .triggerableAnim("catch", RawAnimation.begin().thenPlay("tree_claw_animation")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return  RenderUtils.getCurrentTick();
    }
}
