package com.gaboj1.tcr.entity.custom.biome2;

import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.entity.custom.TCRAggressiveGeoMob;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static com.gaboj1.tcr.entity.custom.boss.TCRBoss.getPlayerAABB;

public class BigHammerEntity extends TCRAggressiveGeoMob {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isRotating;
    private int rotatingTick;
    private int attackTick;
    private int attack2Tick;

    public BigHammerEntity(EntityType<? extends BigHammerEntity> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        setBossInfoColor(BossEvent.BossBarColor.WHITE);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 85)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangeMeleeAttackGoal(this, 1.0D, false, 3, 60){
            @Override
            public boolean canUse() {
                return super.canUse() && !isRotating;
            }
        });
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        int id = getRandom().nextInt(1, 5);
        switch (id){
            case 2:
                triggerAnim("Attack", "attack2");
                attack2Tick =  10;
                break;
            case 1, 4:
                triggerAnim("Attack", "attack" + id);
                attackTick = 20;
                break;
            case 3:
                triggerAnim("Rotate", "rotate");
                isRotating = true;
                break;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide){
            if(attackTick > 0){
                attackTick--;
                //蓄力后在前方触发爆炸
                if(attackTick == 1){
                    Vec3 explodePos = this.position().add(this.getViewVector(1.0F).scale(3));
                    for(Player player : level().getNearbyPlayers(TargetingConditions.DEFAULT, this, getPlayerAABB(getOnPos(), 3))){
                        player.hurt(this.damageSources().mobAttack(this), 10);
                    }
                    level().explode(this, this.damageSources().explosion(this, this), null, explodePos, 3F, false, Level.ExplosionInteraction.NONE);
                }
            }
            //延时攻击
            if(attack2Tick > 0){
                attack2Tick--;
                if(attack2Tick == 1 && getTarget() != null && getTarget().distanceTo(this) < 3){
                    getTarget().hurt(this.damageSources().mobAttack(this), 10);
                }
            }
            //哈利路亚大旋风
            if(isRotating){
                rotatingTick--;
                triggerAnim("Rotate", "rotate");
                for(Player player : level().getNearbyPlayers(TargetingConditions.DEFAULT, this, getPlayerAABB(getOnPos(), 3))){
                    player.hurt(this.damageSources().mobAttack(this), 10);
                }
                if(rotatingTick < 0){
                    rotatingTick = 200;
                    isRotating = false;
                }
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        if(source.getEntity() instanceof BigHammerEntity){
            return false;
        }
        return super.hurt(source, p_21017_);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Attack", 7, state -> PlayState.STOP)
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.model.attack1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.model.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack4", RawAnimation.begin().then("animation.model.attack4", Animation.LoopType.PLAY_ONCE))
        );
        controllers.add(new AnimationController<>(this, "Rotate", 0, state -> PlayState.STOP)
                .triggerableAnim("rotate", RawAnimation.begin().then("animation.model.attack3", Animation.LoopType.PLAY_ONCE))
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
