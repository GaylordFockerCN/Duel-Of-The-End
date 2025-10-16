package com.p1nero.dote.entity.custom.boss.sand_captain;

import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * 其实不算boss，但是为了祭坛能直接生，所以就当boss了
 */
public class SandCaptainCoffin extends DOTEBoss implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SUMMON = RawAnimation.begin().thenLoop("summon");

    public SandCaptainCoffin(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float value) {
        return false;
    }

    /**
     * 什么都不干，播个动画就去世
     */
    @Override
    protected void registerGoals() {

    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return null;
    }

    @Override
    protected void bossTick() {
        //生成真实体然后紫砂
        if(tickCount == 60) {
            if(level() instanceof ServerLevel serverLevel) {
                SandCaptainEntity sandCaptainEntity = DOTEEntities.SAND_CAPTAIN.get().spawn(serverLevel, this.getHomePos(), MobSpawnType.SPAWNER);
                if(sandCaptainEntity != null) {
                    sandCaptainEntity.setHomePos(this.getHomePos());
                    this.getBossSpawnerBlockEntity().ifPresent(bossSpawnerBlockEntity -> {
                        bossSpawnerBlockEntity.setMyBoss(sandCaptainEntity);
                    });
                    this.explodeAndDiscard();
                }
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::deployAnimController));
        controllers.add(new AnimationController<>(this, "Summon", 5, state -> PlayState.CONTINUE)
                .triggerableAnim("summon", SUMMON));
    }

    public void playStartAnimation() {
        triggerAnim("Summon", "summon");
    }

    protected <E extends SandCaptainCoffin> PlayState deployAnimController(final AnimationState<E> state) {
        return state.setAndContinue(IDLE);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
