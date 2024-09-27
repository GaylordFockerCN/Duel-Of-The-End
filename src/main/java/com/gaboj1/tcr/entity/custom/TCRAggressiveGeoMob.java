package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;

/**
 * 因为geckolib没有时间戳事件，所以尝试写了一个，在{@link com.gaboj1.tcr.entity.custom.biome2.SnowSwordmanEntity}首次尝试
 */
public abstract class TCRAggressiveGeoMob extends Monster implements GeoEntity, LevelableEntity, MultiPlayerBoostEntity {
    private final Queue<TimeStamp> queue = new ArrayDeque<>();
    protected static final EntityDataAccessor<Boolean> IS_ELITE = SynchedEntityData.defineId(TCRAggressiveGeoMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<BlockPos> ELITE_SPAWN_POS = SynchedEntityData.defineId(TCRAggressiveGeoMob.class, EntityDataSerializers.BLOCK_POS);
    protected final ServerBossEvent INFO = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
    protected TCRAggressiveGeoMob(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(IS_ELITE, false);
        getEntityData().define(ELITE_SPAWN_POS, BlockPos.ZERO);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(IS_ELITE,tag.getBoolean("is_elite"));
        this.getEntityData().set(ELITE_SPAWN_POS, new BlockPos(tag.getInt("elite_spawn_posX"), tag.getInt("elite_spawn_posY"), tag.getInt("elite_spawn_posZ")));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("is_elite", this.getEntityData().get(IS_ELITE));
        BlockPos pos = getEntityData().get(ELITE_SPAWN_POS);
        tag.putInt("elite_spawn_posX", pos.getX());
        tag.putInt("elite_spawn_posY", pos.getY());
        tag.putInt("elite_spawn_posZ", pos.getZ());
    }
    public void setElite(BlockPos spawnPos){
        AttributeInstance instance1 = this.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance instance2 = this.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier healthModifier = new AttributeModifier(UUID.fromString("d0d000cc-f10f-00ed-a05b-0000bb114514"), "Elite Mob", TCRConfig.ELITE_MOB_HEALTH_MULTIPLIER.get(), AttributeModifier.Operation.MULTIPLY_TOTAL);
        AttributeModifier damageModifier = new AttributeModifier(UUID.fromString("d0d000cc-f20f-00ed-a05b-0000bb114514"), "Elite Mob", TCRConfig.ELITE_MOB_DAMAGE_MULTIPLIER.get(), AttributeModifier.Operation.MULTIPLY_TOTAL);
        if(instance1 != null && !instance1.hasModifier(healthModifier)){
            instance1.addPermanentModifier(healthModifier);
            setHealth(getMaxHealth());
        }
        if(instance2 != null && !instance2.hasModifier(healthModifier)){
            instance2.addPermanentModifier(damageModifier);
        }
        getEntityData().set(IS_ELITE, true);
        getEntityData().set(ELITE_SPAWN_POS, spawnPos);
    }
    protected void setBossInfoColor(BossEvent.BossBarColor color){
        INFO.setColor(color);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide()) {
            INFO.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        //精英怪才亮血条
        if(getEntityData().get(IS_ELITE)){
            INFO.addPlayer(player);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        if(source.getEntity() instanceof ServerPlayer player && getEntityData().get(IS_ELITE)){
            INFO.addPlayer(player);
        }
        return super.hurt(source, p_21017_);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if(getEntityData().get(IS_ELITE)){
            onEliteDie();
        }
        super.die(source);
    }

    /**
     * 精英怪死亡事件，方便破墙什么的
     */
    public void onEliteDie(){};

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        INFO.removePlayer(player);
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        for(TimeStamp event : queue){
            event.tick();
        }
    }

    public void tryHurtTarget(float attackDis, float damage, boolean isDirectDamage){
        if(getTarget() != null && getTarget().distanceTo(this) < attackDis){
            if(isDirectDamage){
                getTarget().setHealth(getTarget().getHealth() - damage);
            } else {
                getTarget().hurt(this.damageSources().mobAttack(this), damage);
            }
        }
    }

    protected void addTask(int timer, TickRunnable runnable){
        queue.add(new TimeStamp(timer, runnable, this));
    }

    /**
     * 普攻，时间到就攻击
     */
    protected void addBasicAttackTask(int delay, float damage, float dis){
        queue.add(new TimeStamp(delay, (tick -> {
            if(tick == 1){
                tryHurtTarget(dis, damage, false);
            }
        }), this));
    }

    /**
     * 多次普攻，每经过指定时间就攻击
     */
    protected void addMultiBasicAttackTask(int delay, float damage, float dis, int... timeStamps){
        queue.add(new TimeStamp(delay, (tick -> {
            for(int i : timeStamps){
                if(tick == i){
                    tryHurtTarget(dis, damage, true);
                }
            }

        }), this));
    }

    public static class TimeStamp {
        private int timer;
        private final TickRunnable runnable;
        private final TCRAggressiveGeoMob mob;
        public TimeStamp(int timer, TickRunnable runnable, TCRAggressiveGeoMob mob){
            this.timer = timer;
            this.runnable = runnable;
            this.mob = mob;
        }

        protected void tick(){
            timer--;
            runnable.run(timer);
            if(timer == 0){
                mob.queue.remove(this);
            }
        }
    }

    public interface TickRunnable{
        void run(int tick);
    }

}
