package com.gaboj1.tcr.entity.custom.Yggdrasil;

import com.gaboj1.tcr.entity.ai.goal.LaunchTreeClawGoal;
import com.gaboj1.tcr.init.TCRModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Objects;


public class YggdrasilEntity extends PathfinderMob implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS);

    public YggdrasilEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    public ServerBossEvent getBossBar() {
        return this.bossInfo;
    }
    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.getBossBar().setName(this.getDisplayName());
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide()) {
            this.getBossBar().setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.getBossBar().addPlayer(player);
    }


    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.getBossBar().removePlayer(player);
    }

    @Override
    public void die(DamageSource p_21014_) {
        super.die(p_21014_);
        if (this.level() instanceof ServerLevel server){
        this.getBossBar().setProgress(0.0F);
        }
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }


    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 6.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速
                .build();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //非攻击状态动画
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));

        //攻击状态动画
        controllers.add(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    public void launchProjectileAt(ThrowableProjectile projectile) {
        //计算朝向
        float bodyFacingAngle = ((this.yBodyRot * Mth.PI) / 180F);

        //计算发射位置
        double sx = this.getX() + (Mth.cos(bodyFacingAngle) * 0.65D);
        double sy = this.getY() + (this.getBbHeight() * 0.82D);
        double sz = this.getZ() + (Mth.sin(bodyFacingAngle) * 0.65D);

        //目标位置与发射位置的偏移量
        double tx = Objects.requireNonNull(this.getTarget()).getX() - sx;
        double ty = (this.getTarget().getBoundingBox().minY + this.getTarget().getBbHeight() / 2.0F) - (this.getY() + this.getBbHeight() / 2.0F);
        double tz = this.getTarget().getZ() - sz;
//      声音，有声音了再加
//        this.playSound(TCRSounds.LICH_SHOOT.get(), this.getSoundVolume(), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);

        projectile.moveTo(sx, sy, sz, this.getYRot(), this.getXRot());
        projectile.shoot(tx, ty, tz, 1.5F, 1.0F);

        this.level().addFreshEntity(projectile);
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("wander", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(isDeadOrDying()){//播放死亡动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("death", Animation.LoopType.LOOP));
            return PlayState.STOP;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.STOP;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return TCRModSounds.YGGDRASIL_AMBIENT_SOUND.get();
    }


    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying();
    }

    private PlayState attackPredicate(AnimationState state) {
        if(state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    protected void registerGoals() {//设置生物行为
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2,new LaunchTreeClawGoal(this));
       this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));


        /*
         * 下面设置攻击目标：（按需修改）
         * 首先寻找攻击源
         * 如果是玩家，且树怪被玩家激怒，则优先攻击玩家
         * 如果是村民，不处于被激怒状态则也被攻击，优先级低于玩家（按需修改）
         * 如果是Creeper，则与村民逻辑相同，但优先级低于村民（按需修改）
         * */
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this,AbstractVillager.class,true));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
