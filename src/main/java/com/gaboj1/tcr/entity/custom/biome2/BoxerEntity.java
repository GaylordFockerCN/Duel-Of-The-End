package com.gaboj1.tcr.entity.custom.biome2;

import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

import static com.gaboj1.tcr.entity.custom.boss.TCRBoss.getPlayerAABB;

public class BoxerEntity extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    //是否需要追
    public static final EntityDataAccessor<Boolean> NEED_CHASE = SynchedEntityData.defineId(BoxerEntity.class, EntityDataSerializers.BOOLEAN);
    private int attackTimer1;//五连击
    private int attackTimer2;//升龙拳
    private int attackTimer3;//瞬移后攻击
    private int attackTimer4;//冲刺的时候攻击

    public BoxerEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(NEED_CHASE, false);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangeMeleeAttackGoal(this, 1.0D, false, 2, 60));
        this.goalSelector.addGoal(3, new BoxerChaseGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if(getRandom().nextBoolean()){
            triggerAnim("Attack", "attack1");
            attackTimer1 = 10 + 16;
        } else {
            triggerAnim("Attack", "attack2");
            attackTimer2 = 5;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide){
            if(getEntityData().get(NEED_CHASE)){
                getEntityData().set(NEED_CHASE, false);
                if(getTarget() != null){
                    setDeltaMovement(getTarget().position().subtract(this.position()).scale(0.15));//在服务端的话头转不过来
                }
            }
        } else {
            if(attackTimer1 > 0){
                attackTimer1--;
                if(attackTimer1 == 20){
                    if(getTarget() != null && getTarget().distanceTo(this) < 1.5){
                        getTarget().setHealth(getTarget().getHealth() - 1);
                    }
                }
                if(attackTimer1 == 16){
                    if(getTarget() != null && getTarget().distanceTo(this) < 1.5){
                        getTarget().setHealth(getTarget().getHealth() - 1);
                    }
                }
                if(attackTimer1 == 12){
                    if(getTarget() != null && getTarget().distanceTo(this) < 1.5){
                        getTarget().setHealth(getTarget().getHealth() - 1);
                    }
                }
                if(attackTimer1 == 8){
                    if(getTarget() != null && getTarget().distanceTo(this) < 1.5){
                        getTarget().setHealth(getTarget().getHealth() - 1);
                    }
                }
                if(attackTimer1 == 1){
                    if(getTarget() != null && getTarget().distanceTo(this) < 1.5){
                        getTarget().setHealth(getTarget().getHealth() - 3);
                    }
                }
            }

            if(attackTimer2 > 0){
                attackTimer2--;
                if(attackTimer2 == 1){
                    if(getTarget() != null && getTarget().distanceTo(this) < 2){
                        getTarget().hurt(this.damageSources().mobAttack(this), 10);
                        getTarget().setDeltaMovement(new Vec3(0, 1, 0));
                    }
                }
            }

            if(attackTimer3 > 0){
                attackTimer3--;
                if(attackTimer3 == 1){
                    if(getTarget() != null && getTarget().distanceTo(this) < 2){
                        getTarget().hurt(this.damageSources().mobAttack(this), 10);
                    }
                }
            }

            if(attackTimer4 > 0){
                attackTimer4--;
                for(Player player : level().getNearbyPlayers(TargetingConditions.DEFAULT, this, getPlayerAABB(getOnPos(), 1))){
                    player.hurt(this.damageSources().mobAttack(this), 10);
                }
            }
        }

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Attack", 10, state -> PlayState.STOP)
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.model.attack1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.model.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack3", RawAnimation.begin().then("animation.model.attack3", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack4", RawAnimation.begin().then("animation.model.attack4", Animation.LoopType.PLAY_ONCE))
        );
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.move", Animation.LoopType.LOOP));
        }else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static class BoxerChaseGoal extends Goal {

        private final BoxerEntity entity;
        public static final int CHASE_DIS = 7;
        public BoxerChaseGoal(BoxerEntity entity){
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return entity.getTarget() != null && entity.getTarget().distanceTo(entity) > CHASE_DIS;
        }

        @Override
        public void start() {
            if(entity.getRandom().nextBoolean()){
                entity.triggerAnim("Attack", "attack4");
                entity.getEntityData().set(NEED_CHASE, true);
                entity.attackTimer4 = 10+10;
            } else {
                entity.triggerAnim("Attack", "attack3");
                entity.setPos(Objects.requireNonNull(entity.getTarget()).position());
                entity.attackTimer3 = 10+15;
            }
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SNOW_GOLEM_SHOOT, SoundSource.BLOCKS, 1, 1);
        }
    }

}
