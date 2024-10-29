package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.BulletEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

/**
 * 树爪继承自Mob，和平模式无法召唤！！
 */
public class TreeClawEntity extends Mob implements GeoEntity, LevelableEntity, MultiPlayerBoostEntity {
    private YggdrasilEntity yggdrasilEntity;
    private Player target;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int catchTimer;
    private final int catchTimerMax = 10;//逃离时间
    private boolean isCatching;
    public TreeClawEntity(EntityType<? extends TreeClawEntity> p_37466_, Level p_37467_) {
         super(p_37466_, p_37467_);
         SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public TreeClawEntity(Level world, YggdrasilEntity thrower, Player target){
        super(TCREntities.TREE_CLAW.get(),world);
        yggdrasilEntity = thrower;
        this.target = target;
        catchTimer = catchTimerMax;
        isCatching = false;
        setNoAi(true);
//        if(world instanceof ServerLevel){
//            catchPlayer();
//        }
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 142857)
                .build();
    }

    public YggdrasilEntity getYggdrasilEntity() {
        return yggdrasilEntity;
    }

    @Override
    public void tick() {
        super.tick();
        catchTimer--;
        if(catchTimer < 0 && !isCatching && this.target != null && !level().isClientSide && checkHit(target.getOnPos(),1)){
            target.hurt(level().damageSources().indirectMagic(this, yggdrasilEntity),10f);
            isCatching = true;
        }
        if(isCatching && this.target != null && !level().isClientSide){
            target.teleportTo(target.getX(),target.getY(),target.getZ());
        }
        if(catchTimer == -catchTimerMax * 14 + 10){
            level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3, false, Level.ExplosionInteraction.NONE);
        }
        if(catchTimer < -catchTimerMax * 14){
            this.discard();//时间够久就自毁
        }
    }
    public boolean checkHit(BlockPos pos1, int offSet){
        BlockPos pos2 = getOnPos();
        return pos1.getX()<=pos2.getX()+offSet && pos1.getX()>=pos2.getX()-offSet
                && pos1.getZ()<=pos2.getZ()+offSet && pos1.getZ()>=pos2.getZ()-offSet;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        if(source.getEntity() instanceof YggdrasilEntity){
            return false;
        }
        if(source.getDirectEntity() instanceof BulletEntity){
            return super.hurt(source, getMaxHealth() / 2 + 1);//打8次才死，机制怪，除非火枪射两次
        }
        return super.hurt(source, getMaxHealth() / 6 + 1);//打8次才死，机制怪，除非火枪
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
