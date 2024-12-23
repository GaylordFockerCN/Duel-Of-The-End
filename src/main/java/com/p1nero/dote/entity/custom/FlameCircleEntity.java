package com.p1nero.dote.entity.custom;

import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.util.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 神王追人火圈
 */
public class FlameCircleEntity extends Entity {
    @Nullable
    private LivingEntity target;

    private float radius, speed;

    private int lifeTime;

    public @Nullable LivingEntity getTarget() {
        return target;
    }

    public void setTarget(@Nullable LivingEntity target) {
        this.target = target;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public FlameCircleEntity(EntityType<FlameCircleEntity> type, Level level) {
        super(type, level);
        setNoGravity(true);
        noPhysics = true;
    }

    public FlameCircleEntity(@Nullable LivingEntity target, Level level, float radius, float speed, int lifeTime) {
        super(DOTEEntities.FLAME_CIRCLE.get(), level);
        setNoGravity(true);
        noPhysics = true;
        this.target = target;
        this.radius = radius;
        this.speed = speed;
        this.lifeTime = lifeTime;
    }

    @Override
    public void tick() {
        super.tick();
        if(level() instanceof ServerLevel serverLevel){
            Vec3 myPos = position();
            int cnt = (int) (radius * 10);
            float angleStep = (float) (2 * Math.PI / (radius * 10));
            for (int i = 0; i < cnt; i++) {
                float angle = i * angleStep;
                float x = (float) (myPos.x + radius * Math.cos(angle));
                float z = (float) (myPos.z + radius * Math.sin(angle));
                serverLevel.sendParticles(ParticleTypes.FLAME, x, myPos.y, z, 1, 0, 0, 0, 0);
            }
            for(Entity entity : EntityUtil.getNearByEntities(this, ((int) radius))){
                entity.setSecondsOnFire(5);
            }
            if(tickCount > lifeTime){
                discard();
            }
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {

    }
}
