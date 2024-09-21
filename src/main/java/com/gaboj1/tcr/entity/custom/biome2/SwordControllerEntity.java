package com.gaboj1.tcr.entity.custom.biome2;

import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.entity.custom.TCRAggressiveGeoMob;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SwordControllerEntity extends TCRAggressiveGeoMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean summoned;

    public SwordControllerEntity(EntityType<? extends SwordControllerEntity> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SaveUtil.getMobMultiplier(50))
                .add(Attributes.ATTACK_DAMAGE, SaveUtil.getMobMultiplier(3))
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangeMeleeAttackGoal(this, 1.0D, false, 5, 60));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void thunderHit(@NotNull ServerLevel p_19927_, @NotNull LightningBolt p_19928_) {}

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        int id = getRandom().nextInt(1, 4);
        triggerAnim("Attack", "attack" + id);
        switch (id){
            case 1, 2:
                addBasicAttackTask(10 + 14, 5, 3);
                break;
            case 3:
                addMultiBasicAttackTask(10 + 20, 1F, 3, 10, 6, 4, 2);
                break;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(level() instanceof ServerLevel serverLevel){
            if(!summoned && getHealth() < getMaxHealth() / 3 && getTarget() != null){
                summoned = true;
                triggerAnim("Attack", "attack4");
                addTask(20, (tick -> {
                    if(tick == 1 || tick == 4 || tick == 7){
                        EntityType.LIGHTNING_BOLT.spawn(serverLevel, getTarget().getOnPos().offset(random.nextInt(-2, 2), 0, random.nextInt(-2, 2)), MobSpawnType.TRIGGERED);
                    }
                }));
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
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
