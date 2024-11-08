package com.gaboj1.tcr.entity.custom.biome1;

import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.villager.biome1.branch.Elinor;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UnknownEntity extends Monster implements GeoEntity, LevelableEntity, MultiPlayerBoostEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Boolean> CAN_PURIFY = SynchedEntityData.defineId(UnknownEntity.class, EntityDataSerializers.BOOLEAN);
    private int hensinTimer = 0;

    public UnknownEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(CAN_PURIFY, false);
        super.defineSynchedData();
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        getEntityData().set(CAN_PURIFY, tag.getBoolean("can_purify"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("can_purify", getEntityData().get(CAN_PURIFY));
    }

    public void setCanPurify(boolean canPurify){
        getEntityData().set(CAN_PURIFY, canPurify);
    }

    public boolean canPurify(){
        return getEntityData().get(CAN_PURIFY);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.6f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Attack", 10, state -> PlayState.STOP)
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.model.attack1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.model.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack3", RawAnimation.begin().then("animation.model.attack3", Animation.LoopType.PLAY_ONCE)));
        //变身
        controllers.add(new AnimationController<>(this, "Hensin", 10, state -> PlayState.STOP)
                .triggerableAnim("hensin", RawAnimation.begin().then("animation.model.hensin", Animation.LoopType.PLAY_ONCE)));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        triggerAnim("Attack", "attack" + (1 + random.nextInt(3)));
        return super.doHurtTarget(entity);
    }
    
    public void hensin(){
        addEffect(new MobEffectInstance(MobEffects.GLOWING, 1000));
        level().playSound(null, getOnPos(), SoundEvents.ENDERMAN_DEATH, SoundSource.BLOCKS, 1, 1);
        triggerAnim("Hensin", "hensin");
        hensinTimer = ((int) (1.92 * 20));
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if(canPurify() && player.getItemInHand(hand).is(TCRItems.PURIFICATION_TALISMAN.get())){
            hensin();
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if(canPurify()){
            TCRArchiveManager.biome1.killed = true;
        }
        super.die(source);
    }

    @Override
    public boolean removeWhenFarAway(double b) {
        if(canPurify()){
            return false;
        }
        return super.removeWhenFarAway(b);
    }

    /**
     * 变身成人
     */
    @Override
    public void tick() {
        super.tick();
        if(hensinTimer > 0){
            hensinTimer--;
            if(hensinTimer == 1){
                if(level() instanceof ServerLevel serverLevel){
                    level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3F, false, Level.ExplosionInteraction.NONE);
                    Elinor elinor = TCREntities.ELINOR.get().spawn(serverLevel, getOnPos(), MobSpawnType.NATURAL);
                    assert elinor != null;
                    serverLevel.addFreshEntity(elinor);
                    TCRArchiveManager.biome1.heal = true;
                    TCRArchiveManager.biome1.killed= false;//因为一开始killed默认为true，以防玩家逃跑或怪异常不出现等情况。
                }
                this.discard();
            }
        }
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.move", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return TCRSounds.UNKNOWN_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return TCRSounds.UNKNOWN_AMBIENT.get();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
