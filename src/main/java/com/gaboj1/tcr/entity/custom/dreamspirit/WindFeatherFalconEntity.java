package com.gaboj1.tcr.entity.custom.dreamspirit;

import com.gaboj1.tcr.entity.ai.goal.BirdFlyGoal;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WindFeatherFalconEntity extends TamableAnimal implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WindFeatherFalconEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 70)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.FLYING_SPEED, 0.2f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(4, new BirdFlyGoal(this, 1.0));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    /**
     * 防止坠机
     */
    @Override
    public boolean hurt(@NotNull DamageSource source, float p_27568_) {
        if(source.is(DamageTypes.FALL)){
            return false;
        }
        return super.hurt(source, p_27568_);
    }

    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (!this.isVehicle() && this.isTame() && this.getOwner() == player) {
            player.startRiding(this);
            return super.mobInteract(player, hand);
        } else {
            if(player.getItemInHand(hand).is(TCRItems.MAO_DAI.get())){
                player.getItemInHand(hand).shrink(1);
                level().addParticle(ParticleTypes.HEART, getX(), getY(), getZ(), 0, 0, 0);
                this.tame(player);
            }
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return super.isControlledByLocalInstance();
    }

    @Override
    public void travel(@NotNull Vec3 pos) {
        if (isAlive()) {
            if (this.isVehicle() && this.getControllingPassenger() != null) {
                LivingEntity pilot = this.getControllingPassenger();
                pilot.setYBodyRot(pilot.getYHeadRot());
                this.setYRot(pilot.getYRot());
                this.setXRot(pilot.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                // xxa and zza half the xxa, zza is forward
                float f = pilot.xxa * 0.5F;
                float f1 = pilot.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                this.setYya(pilot.yya);
                float xxa = pilot.xxa * 0.5F;
                float zza = pilot.zza;

                // facing straight up is -10 xRot, up is - and + is down
                double xHeadRot;
                if (this.getXRot() > 0) {
                    xHeadRot = this.getXRot() / 2;
                } else {
                    xHeadRot = this.getXRot() / 3.6;
                }

                double xHeadRotABS = Math.abs(this.getXRot()) / 450;
                double y = xHeadRot * -0.005;
                if (zza <= 0.0F) {
                    zza *= 0.25F;
                }

                // slow down movement by half
                Vec3 delta = this.getDeltaMovement();
                this.setDeltaMovement(delta.x / 2, delta.y / 2, delta.z / 2);

                if (zza > 0.0F) {
                    float f2 = Mth.sin(this.getYRot() * 0.017453292F);
                    float f3 = Mth.cos(this.getYRot() * 0.017453292F);


                    double speed = this.getAttributeValue(onGround() ? Attributes.MOVEMENT_SPEED : Attributes.FLYING_SPEED) * (isInWater() ? 0.2F : 1.02F);
                    this.setDeltaMovement(delta.add(
                            -speed * f2,
                            y, speed * f3));
                }

                if (pilot instanceof LocalPlayer localPlayer) {
                    if(localPlayer.input.jumping){
                        this.setDeltaMovement(this.getDeltaMovement().add(0, zza > 0 ? 0.1 : 0.3, 0));
                    }
                    if(localPlayer.input.down){
                        this.setDeltaMovement(this.getDeltaMovement().add(0, zza > 0 ? -0.2 : -0.4, 0));
                    }
                }

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.FLYING_SPEED));
                    super.travel(new Vec3(xxa, y, zza)); // pTravelVector.y
                } else if (pilot instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }
                this.tryCheckInsideBlocks();
            } else {
                super.travel(pos);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getControllingPassenger() != null){
            this.getControllingPassenger().resetFallDistance();
        }
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity var2 = this.getFirstPassenger();
        LivingEntity var10000;
        if (var2 instanceof LivingEntity entity) {
            var10000 = entity;
        } else {
            var10000 = null;
        }

        return var10000;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Jump", 10, state -> PlayState.STOP)
                .triggerableAnim("jump", RawAnimation.begin().then("jump", Animation.LoopType.PLAY_ONCE)));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(!onGround()){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
        } else if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }
}
