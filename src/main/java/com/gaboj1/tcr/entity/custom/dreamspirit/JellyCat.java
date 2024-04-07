package com.gaboj1.tcr.entity.custom.dreamspirit;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.datagen.ModAdvancementData;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;

/**
 * 大部分代码copy form Slime.java，实现猫猫跳动。没办法史莱姆不是可驯养的，只能这么做了awa
 * @see net.minecraft.world.entity.monster.Slime
 * @author LZY
 */
public class JellyCat extends TamableAnimal implements GeoEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    //0 代表 正常，1代表闭眼，2代表跑动、受攻击 > <
    private FaceID faceID = FaceID.IDLE;
    private int skinID = 0;
    private final int maxSkinID = 7;
    private final int winkIntervalMax = 5;
    private int winkInterval;
    private final int useTimeMax = 10;
    private int useTime;
    private boolean isWinking = false;

    /**
     * Goal太难用了，直接tick屎山。
     * 后面感觉随机眨眼太奇怪了，改成跳起来的时候眨眼
     */
    @Override
    public void tick() {
//        if(this.hurtMarked){
//            setFaceID(FaceID.HURT);
//        }else {
//            if(!isWinking && isAlive() && !hurtMarked && !(this.winkInterval --> 0)){
//                isWinking = true;
//                this.winkInterval = winkIntervalMax+random.nextInt(100);
//                this.useTime = useTimeMax;
//            }
//            if(isWinking){
//                if(useTime-->0){
//                    setFaceID(FaceID.WINK);
//                }else {
//                    useTime = useTimeMax;
//                    isWinking = false;
//                    setFaceID(FaceID.IDLE);
//                }
//            }
//        }
        if(!isAlive()){
            setFaceID(FaceID.DIE);
        } else if(this.hurtMarked){
            setFaceID(FaceID.HURT);
        } else if(!onGround()){
            setFaceID(FaceID.WINK);
        } else {
            setFaceID(FaceID.IDLE);
        }
        super.tick();
    }

    @Override
    public void tame(Player player) {
        if(player instanceof ServerPlayer serverPlayer){
            serverPlayer.getPersistentData().putBoolean("tamed_jelly_cat"+skinID,true);
            boolean isAllTamed = true;
            for(int i = 0;i < maxSkinID;i++){
                if(!serverPlayer.getPersistentData().getBoolean("tamed_jelly_cat"+i)){
                    isAllTamed = false;
                    break;
                }
            }
            if(isAllTamed){
                ModAdvancementData.getAdvancement("cats_friend",serverPlayer);
            }
        }
        super.tame(player);
    }

    public JellyCat(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new JellyCatMoveControl(this);
    }

    public FaceID getFaceID() {
        return faceID;
    }

    public void setFaceID(FaceID faceID) {
        this.faceID = faceID;
    }

    public int getSkinID() {
        return skinID;
    }

    public void setSkinID(int skinID) {
        this.skinID = skinID;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .build();
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new JellyCatFloatGoal(this));
        this.goalSelector.addGoal(2, new JellyCatAttackGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
//        this.goalSelector.addGoal(2, new JellyCatWinkGoal(this));
        this.goalSelector.addGoal(3, new JellyCatRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new JellyCatKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        setFaceID(FaceID.DIE);
        return super.getDeathSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return super.getHurtSound(damageSource);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                0, this::predicate));
    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
        }else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public enum FaceID{
        IDLE,
        WINK,
        HURT,
        DIE

    }

    public static class JellyCatWinkGoal extends Goal {
        private final JellyCat jellyCat;
        private final int winkIntervalMax = 25;
        private int winkInterval;
        private boolean isWinking = false;

        public JellyCatWinkGoal(JellyCat jellyCat) {
            this.jellyCat = jellyCat;
            winkInterval = winkIntervalMax;
        }

        @Override
        public boolean canUse() {
            return this.jellyCat.isAlive() && !this.jellyCat.hurtMarked && !(this.winkInterval --> 0);
        }

        @Override
        public void start() {
            if(isWinking){
                return;
            }
            this.jellyCat.setFaceID(FaceID.WINK);
            isWinking = true;
            new Thread(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.jellyCat.setFaceID(FaceID.IDLE);
                isWinking = false;
                this.winkInterval = winkIntervalMax;
            }).start();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    static class JellyCatMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final JellyCat jellyCat;
        private boolean isAggressive;

        public JellyCatMoveControl(JellyCat jellyCat) {
            super(jellyCat);
            this.jellyCat = jellyCat;
            this.yRot = 180.0F * jellyCat.getYRot() / (float)Math.PI;
        }

        public void setDirection(float p_33673_, boolean p_33674_) {
            this.yRot = p_33673_;
            this.isAggressive = p_33674_;
        }

        public void setWantedMovement(double v) {
            this.speedModifier = v;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        //copy form Slime.java
        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.jellyCat.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }
                        this.jellyCat.getJumpControl().jump();
                        this.jellyCat.playSound(SoundEvents.SLIME_JUMP, this.jellyCat.getSoundVolume() / 2, 1.0f);
                    } else {
                        this.jellyCat.xxa = 0.0F;
                        this.jellyCat.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }
    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    /**
     * 友好生物
     */
    @Override
    public boolean canAttack(LivingEntity entity) {
        if(entity instanceof Player){
            return false;
        }
        return super.canAttack(entity);
    }

    static class JellyCatRandomDirectionGoal extends Goal {
        private final JellyCat JellyCat;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public JellyCatRandomDirectionGoal(JellyCat p_33679_) {
            this.JellyCat = p_33679_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return this.JellyCat.getTarget() == null && (this.JellyCat.onGround() || this.JellyCat.isInWater() || this.JellyCat.isInLava() || this.JellyCat.hasEffect(MobEffects.LEVITATION)) && this.JellyCat.getMoveControl() instanceof JellyCatMoveControl;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.JellyCat.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.JellyCat.getRandom().nextInt(360);
            }

            MoveControl movecontrol = this.JellyCat.getMoveControl();
            if (movecontrol instanceof JellyCatMoveControl jellyCatMoveControl) {
                jellyCatMoveControl.setDirection(this.chosenDegrees, false);
            }

        }
    }

    static class JellyCatAttackGoal extends Goal {
        private final JellyCat JellyCat;
        private int growTiredTimer;

        public JellyCatAttackGoal(JellyCat JellyCat) {
            this.JellyCat = JellyCat;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.JellyCat.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return this.JellyCat.canAttack(livingentity) && this.JellyCat.getMoveControl() instanceof JellyCatMoveControl;
            }
        }

        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.JellyCat.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!this.JellyCat.canAttack(livingentity)) {
                return false;
            } else {
                return --this.growTiredTimer > 0;
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.JellyCat.getTarget();
            if (livingentity != null) {
                this.JellyCat.lookAt(livingentity, 10.0F, 10.0F);
            }

            MoveControl movecontrol = this.JellyCat.getMoveControl();
            if (movecontrol instanceof JellyCatMoveControl jellyCatMoveControl) {
                jellyCatMoveControl.setDirection(this.JellyCat.getYRot(), true);
            }

        }
    }

    static class JellyCatFloatGoal extends Goal {
        private final JellyCat JellyCat;

        public JellyCatFloatGoal(JellyCat p_33655_) {
            this.JellyCat = p_33655_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            p_33655_.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return (this.JellyCat.isInWater() || this.JellyCat.isInLava()) && this.JellyCat.getMoveControl() instanceof JellyCatMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.JellyCat.getRandom().nextFloat() < 0.8F) {
                this.JellyCat.getJumpControl().jump();
            }

            MoveControl movecontrol = this.JellyCat.getMoveControl();
            if (movecontrol instanceof JellyCatMoveControl jellyCatMoveControl) {
                jellyCatMoveControl.setWantedMovement(1.2D);
            }

        }
    }

    static class JellyCatKeepOnJumpingGoal extends Goal {
        private final JellyCat JellyCat;

        public JellyCatKeepOnJumpingGoal(JellyCat p_33660_) {
            this.JellyCat = p_33660_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !this.JellyCat.isPassenger();
        }

        public void tick() {
            MoveControl movecontrol = this.JellyCat.getMoveControl();
            if (movecontrol instanceof JellyCatMoveControl jellyCatMoveControl) {
                jellyCatMoveControl.setWantedMovement(1.0D);
            }

        }
    }

}
