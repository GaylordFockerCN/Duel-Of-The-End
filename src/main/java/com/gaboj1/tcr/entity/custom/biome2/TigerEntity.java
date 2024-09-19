package com.gaboj1.tcr.entity.custom.biome2;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.entity.custom.IceThornEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class TigerEntity extends Monster implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final UUID HAS_TARGET_MODIFY = UUID.fromString("d2d057cc-f30f-11ed-a05b-1919bb114514");
    //是否需要跳跃
    public static final EntityDataAccessor<Boolean> NEED_JUMP = SynchedEntityData.defineId(TigerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(TigerEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean shot;
    private int attackTimer1;
    private int attackTimer2;

    public TigerEntity(EntityType<? extends TigerEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(NEED_JUMP, false);
        getEntityData().define(HAS_TARGET, false);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 110)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ATTACK_SPEED, 0.3f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangeMeleeAttackGoal(this, 1.0, false, 3F, 40));
        this.goalSelector.addGoal(3, new TigerChaseGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide){
            //有目标的时候加速
            AttributeModifier modifier = new AttributeModifier(HAS_TARGET_MODIFY, "hasTarget", 1.5, AttributeModifier.Operation.MULTIPLY_BASE);
            AttributeInstance instance = getAttribute(Attributes.MOVEMENT_SPEED);
            if(instance != null){
                if(getTarget() != null){
                    if(!instance.hasModifier(modifier)){
                        instance.addTransientModifier(modifier);
                    }
                } else {
                    instance.removeModifier(modifier);
                }
            }

            //半血射刺
            if(!shot && getHealth() < getMaxHealth() / 2 && getTarget() != null){
                shot = true;
                triggerAnim("Attack", "attack4");
                shoot(getTarget());
            }

            //同步Target用于渲染，不知道为什么客户端没检测到Target
            getEntityData().set(HAS_TARGET, getTarget() != null);

            //延时攻击
            if(attackTimer1 > 0){
                attackTimer1--;
                if(attackTimer1 == 1 && getTarget() != null && getTarget().distanceTo(this) < 3){
                    getTarget().hurt(this.damageSources().mobAttack(this), 10);
                }
            }
            //扇两巴掌
            if(attackTimer2 > 0){
                attackTimer2--;
                if(attackTimer2 == 13 && getTarget() != null && getTarget().distanceTo(this) < 3){
                    getTarget().setHealth(getTarget().getHealth() - 7);//打真伤，不然攻击间隔太短会霸体
                }
                if(attackTimer2 == 4 && getTarget() != null && getTarget().distanceTo(this) < 3){
                    getTarget().setHealth(getTarget().getHealth() - 10);//打真伤，不然攻击间隔太短会霸体
                }
            }

        } else {
            //追目标
            if(getEntityData().get(NEED_JUMP)){
                getEntityData().set(NEED_JUMP, false);
                if(getTarget() != null){
                    setDeltaMovement(getTarget().position().subtract(this.position()).scale(0.15));
                }
            }
        }

    }

    private void shoot(LivingEntity entity) {
        if(level() instanceof ServerLevel serverLevel){
            for(int i = 0; i < 10; i++){
                IceThornEntity thornEntity = new IceThornEntity(TCREntities.ICE_THORN_ENTITY.get(), serverLevel);
                thornEntity.setOwner(this);
                thornEntity.setNoGravity(true);
                thornEntity.setBaseDamage(10);
                thornEntity.setKnockback(1);
                thornEntity.setSilent(false);
                thornEntity.setPierceLevel((byte) 1);
                thornEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                thornEntity.setPos(getX() + i - 5, getY() + 3, getZ() + i - 5);
                Vec3 dir = entity.position().subtract(this.position());
                thornEntity.shoot(dir.x, dir.y,dir.z, 5, 1.5F);
                serverLevel.addFreshEntity(thornEntity);
                serverLevel.playSound(null, getX(), getY(), getZ(), SoundEvents.SNOW_GOLEM_SHOOT, SoundSource.BLOCKS, 1, 1);
            }
        }

    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        int attackId = getRandom().nextInt(1,5);
        if(entity.distanceTo(this) > TigerChaseGoal.CHASE_DIS){
            triggerAnim("Attack", "attack2");
            getEntityData().set(NEED_JUMP, true);
        }
        switch (attackId){
            case 2, 3:
                triggerAnim("Attack", "attack3");
                attackTimer1 = 17;
                break;
            default:
                triggerAnim("Attack", "attack1");
                attackTimer2 = 30;
        }
        return false;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(getDeltaMovement().length() > 0.01) {
            if(getEntityData().get(HAS_TARGET)){
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.run", Animation.LoopType.LOOP));
            } else {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walk", Animation.LoopType.LOOP));
            }
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
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

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private static class TigerChaseGoal extends Goal {

        private final TigerEntity entity;
        public static final int CHASE_DIS = 7;
        public TigerChaseGoal(TigerEntity entity){
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return entity.getTarget() != null && entity.getTarget().distanceTo(entity) > CHASE_DIS;
        }

        @Override
        public void start() {
            entity.triggerAnim("Attack", "attack2");
            entity.getEntityData().set(NEED_JUMP, true);//需要在客户端setDeltaMovement
        }
    }

}
