package com.gaboj1.tcr.entity.custom.biome1;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.MagicProjectile;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
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

public class SpriteEntity extends Monster implements GeoEntity, LevelableEntity, MultiPlayerBoostEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int timer = 0;
    private int nextAttack;
    private boolean buffed;
    public SpriteEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Attack", 10, state -> PlayState.STOP)
                .triggerableAnim("attack1", RawAnimation.begin().then("animation.model.attack1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("attack2", RawAnimation.begin().then("animation.model.attack2", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("buff", RawAnimation.begin().then("animation.model.buff", Animation.LoopType.PLAY_ONCE)));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.move", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangeMeleeAttackGoal(this, 1.0, false, 20));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        if(timer == 0){
            if(getRandom().nextBoolean()){
                timer = 41;
                nextAttack = 2;
                triggerAnim("Attack", "attack2");
            }else {
                timer = 21;
                nextAttack = 2;
                triggerAnim("Attack", "attack1");
            }
            return true;
        }
        return false;
    }

    /**
     * 如果受到伤害过大则取消施法
     */
    @Override
    public boolean hurt(@NotNull DamageSource source, float v) {
        if(v > 10){
            buff();
        }
        return super.hurt(source, v);
    }

    public void buff(){
        timer = 0;
        triggerAnim("Attack", "buff");
        AttributeInstance instance = getAttribute(Attributes.ARMOR);
        if(instance != null){
            instance.addPermanentModifier(new AttributeModifier("buff modify", 5, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void tick() {
        if(!level().isClientSide){
            if(nextAttack == 2){
                if(timer == 11){
                    shoot(5);
                }
                if(timer == 5){
                    shoot(12);
                }
            }
            if(timer == 1){
                shoot(18);
            }
            if(timer > 0){
                timer--;
            }

            if(!buffed && getHealth() < getMaxHealth() / 2){
                buff();
                buffed = true;
            }
        }

        super.tick();
    }

    public void shoot(int damage){
        MagicProjectile projectile = new MagicProjectile(level(), this);
        projectile.setGlowingTag(true);
        projectile.setDamage(damage);
        Vec3 view = this.getViewVector(1.0F);
        projectile.shoot(view.x, view.y, view.z, 5.0F, 10.0F);
        level().addFreshEntity(projectile);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .add(Attributes.ARMOR, 1)
                .build();
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
