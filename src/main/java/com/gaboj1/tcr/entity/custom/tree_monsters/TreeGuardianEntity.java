package com.gaboj1.tcr.entity.custom.tree_monsters;

import com.gaboj1.tcr.client.TCRModSounds;
import com.gaboj1.tcr.util.BoundingBoxHelper;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;

public class TreeGuardianEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<Boolean> IS_SUMMONED = SynchedEntityData.defineId(TreeGuardianEntity.class, EntityDataSerializers.BOOLEAN);//本来是记录用于判断回血的，但是发现后面没用了，不过可能有别的用处就先留着

    public TreeGuardianEntity(EntityType<? extends TreeGuardianEntity> entityType, Level level) {
        super(entityType, level);
        setBoundingBox(BoundingBoxHelper.scaleAABB(getBoundingBox(),3.5));//无效
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("is_summoned", this.getEntityData().get(IS_SUMMONED));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(IS_SUMMONED,tag.getBoolean("is_summoned"));
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(IS_SUMMONED, false);
        super.defineSynchedData();
    }

    public void setIsSummonedByBoss(){
        getEntityData().set(IS_SUMMONED, true);
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 6)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.35f)//移速
                .build();
    }

    @Override
    protected void playStepSound(BlockPos p_28864_, BlockState p_28865_) {
        this.playSound(SoundEvents.ZOMBIE_STEP);
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
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
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
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }

    private PlayState attackPredicate(AnimationState state) {//判断是否处于攻击状态
        if(this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            Random r = new Random();
            int attackSkill = r.nextInt(3);//生成随机数

            switch (attackSkill){//随机关联攻击动画
                case 0:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian.attack1", Animation.LoopType.PLAY_ONCE));//攻击动作不循环
                    setAttackDamage(8.0f);//设置伤害
                    break;
                case 1:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian.attack2", Animation.LoopType.PLAY_ONCE));
                    setAttackDamage(10.0f);
                    break;
                case 2:
                    state.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian.attack3", Animation.LoopType.PLAY_ONCE));
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

//森林守护者受伤声音
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return TCRModSounds.TREE_MONSTERS_HURT.get();
    }
//森林守护者死亡声音
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return TCRModSounds.TREE_MONSTERS_DEATH.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //非攻击状态动画
        controllers.add(new AnimationController<>(this, "controller",
                0, this::predicate));

        //攻击状态动画
        controllers.add(new AnimationController<>(this, "attackController",
                0, this::attackPredicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tree_guardian.idle", Animation.LoopType.LOOP));
        return PlayState.STOP;//停
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}