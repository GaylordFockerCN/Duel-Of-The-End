package com.gaboj1.tcr.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
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
import software.bernie.geckolib.core.object.PlayState;

import java.util.Random;
import java.util.UUID;

/*
* 守卫，继承铁傀儡
* 接口NeutralMob用于调用激怒方法
* */
public class TreeGuardianEntity extends IronGolem implements GeoEntity , NeutralMob {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public TreeGuardianEntity(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);
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
    protected void registerGoals() {//设置生物行为
        /*
        * 如果在水中，则优先漂浮FloatGoal
        * 追踪攻击目标MeleeAttackGoal
        * 避开水体随机移动WaterAvoidingRandomStrollGoal
        * 随意查看四周RandomLookAroundGoal
        */
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Creeper.class, true));
    }

    private PlayState attackPredicate(AnimationState state) {//判断是否处于攻击状态
        if(this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            Random r = new Random();
            int attackSkill = r.nextInt(3);//生成随机数

            switch (attackSkill){//随机关联攻击动画
                case 0:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian - Converted.attack1", Animation.LoopType.PLAY_ONCE));//攻击动作不循环
                    setAttackDamage(8.0f);//设置伤害
                    break;
                case 1:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian - Converted.attack2", Animation.LoopType.PLAY_ONCE));
                    setAttackDamage(10.0f);
                    break;
                case 2:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian - Converted.attack3", Animation.LoopType.PLAY_ONCE));
                    setAttackDamage(12.0f);
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
        //非攻击状态动画
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));

        //攻击状态动画
        controllers.add(new AnimationController(this, "attackController",
                0, this::attackPredicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian - Converted.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian - Converted.move", Animation.LoopType.LOOP));
        return PlayState.STOP;//停
    }


    //下面的这些没有用
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
