package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.init.TCRModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.AbstractVillager;
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

import java.util.Random;
import java.util.UUID;

public class MiddleTreeMonsterEntity extends TamableAnimal implements GeoEntity , NeutralMob {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public MiddleTreeMonsterEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16D)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .build();
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
    }

    //中树怪受伤声音
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return TCRModSounds.TREE_MONSTERS_HURT.get();
    }
    //中树怪死亡声音
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return TCRModSounds.TREE_MONSTERS_DEATH.get();
    }

//    @Nullable
//    @Override
//    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
//        return TCRModEntities.TIGER.get().create(level);
//    }

    private PlayState attackPredicate(AnimationState state) {
        if(this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            Random r = new Random();
            int attackSkill = r.nextInt(2);//生成随机数

            switch (attackSkill){//随机关联攻击动画
                case 0:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.m_tree_monster - Converted.attack1", Animation.LoopType.PLAY_ONCE));//攻击动作不循环
                    setAttackDamage(8.0f);//设置伤害
                    break;
                case 1:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.m_tree_monster - Converted.attack2", Animation.LoopType.PLAY_ONCE));
                    setAttackDamage(10.0f);
                    break;
            }
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }

    private void setAttackDamage(float damage) {
        // 设置生物的攻击伤害值
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
        controllers.add(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    //@SubscribeEvent
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.m_treemonster - Converted.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        LivingEntity target = this.getTarget();
        if(target != null){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian - Converted.attack1", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.m_treemonster - Converted.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }
}
