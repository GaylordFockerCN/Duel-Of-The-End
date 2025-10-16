package com.p1nero.dote.entity.custom.boss;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.block.entity.spawner.BossSpawnerBlockEntity;
import com.p1nero.dote.client.BossMusicPlayer;
import com.p1nero.dote.entity.ai.goal.AttemptToGoBackGoal;
import com.p1nero.dote.entity.api.HomePointEntity;
import com.p1nero.dote.entity.api.IWanderableEntity;
import com.p1nero.dote.entity.custom.DOTEMonster;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.clientbound.SyncUuidPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * 方便统一调难度
 */
public abstract class DOTEBoss extends DOTEMonster implements HomePointEntity, IWanderableEntity {
    public static final EntityDataAccessor<Float> ATTACK_SPEED = SynchedEntityData.defineId(DOTEBoss.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(DOTEBoss.class, EntityDataSerializers.BLOCK_POS);
    protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(DOTEBoss.class, EntityDataSerializers.INT);//Boss的阶段，备用
    protected static final EntityDataAccessor<Integer> INACTION_TIME = SynchedEntityData.defineId(DOTEBoss.class, EntityDataSerializers.INT);
    public static final Map<UUID, Integer> SERVER_BOSSES = new HashMap<>();//用于客户端渲染bossBar
    protected ServerBossEvent bossInfo;
    private int strafingTime;
    private float strafingForward;
    private float strafingClockwise;
    protected DOTEBoss(EntityType<? extends PathfinderMob> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PURPLE);
    }

    protected DOTEBoss(EntityType<? extends PathfinderMob> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        if(hasBossBar()){
            bossInfo = new ServerBossEvent(this.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
            if(!level.isClientSide){
                PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncUuidPacket(bossInfo.getId(), getId()));
            }
        }
    }

    public boolean hasBossBar(){
        return false;
    }

    public float getHealthRatio(){
        return getHealth() / getMaxHealth();
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public void setRemainingFireTicks(int p_20269_) {
    }

    @Override
    public void setHomePos(BlockPos homePos) {
        getEntityData().set(HOME_POS, homePos);
    }

    @Override
    public BlockPos getHomePos() {
        return getEntityData().get(HOME_POS);
    }

    public Optional<BossSpawnerBlockEntity<?>> getBossSpawnerBlockEntity() {
        return level().getBlockEntity(this.getHomePos()) instanceof BossSpawnerBlockEntity<?> bossSpawnerBlockEntity ? Optional.of(bossSpawnerBlockEntity) : Optional.empty();
    }

    @Override
    public float getHomeRadius() {
        return DOTEConfig.SPAWNER_BLOCK_PROTECT_RADIUS.get();
    }

    public void setPhase(int phase){
        getEntityData().set(PHASE, phase);
    }

    public int getPhase(){
        return getEntityData().get(PHASE);
    }

    public void setInactionTime(int inactionTime) {
        getEntityData().set(INACTION_TIME, inactionTime);
    }

    public int getInactionTime() {
        return getEntityData().get(INACTION_TIME);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(HOME_POS, getOnPos());
        getEntityData().define(PHASE, 0);
        getEntityData().define(INACTION_TIME, 0);
        getEntityData().define(ATTACK_SPEED, 1.0F);
    }

    public int getMaxNeutralizeCount(){
        return 12;
    };

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(HOME_POS, new BlockPos(tag.getInt("home_pos_x"), tag.getInt("home_pos_y"), tag.getInt("home_pos_z")));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("block_cnt", this.getEntityData().get(BLOCK_COUNT));
        tag.putInt("home_pos_x", this.getEntityData().get(HOME_POS).getX());
        tag.putInt("home_pos_y", this.getEntityData().get(HOME_POS).getY());
        tag.putInt("home_pos_z", this.getEntityData().get(HOME_POS).getZ());
    }

    public boolean shouldRenderBossBar(){
        return true;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide() && hasBossBar()) {
            bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        if(hasBossBar()){
            bossInfo.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        if(hasBossBar()){
            bossInfo.removePlayer(player);
        }
    }

    @Override
    public void tick() {
        super.tick();
        bossTick();
    }

    protected void bossTick() {

        if (getInactionTime() > 0) {
            setInactionTime(getInactionTime() - 1);
        }

        //播放bgm
        if(level().isClientSide && this.isAlive()){
            BossMusicPlayer.playBossMusic(this, getFightMusic(), 32);
        }
    }

    /**
     * 演出效果而已不能炸死玩家
     */
    public void explodeAndDiscard(){
        if(level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 10, 0.0D, 0.1D, 0.0D, 0.01);
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 10, 0.0D, 0.1D, 0.0D, 0.01);
            serverLevel.playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1, 1);
        }
        discard();
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if(level().isClientSide){
            BossMusicPlayer.stopBossMusic(this);
        } else {
            this.getBossSpawnerBlockEntity().ifPresent(BossSpawnerBlockEntity::endBossFight);
        }
        super.die(source);
    }

    @Nullable
    public abstract SoundEvent getFightMusic();

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected float getEquipmentDropChance(@NotNull EquipmentSlot slot) {
        return 0;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
//        this.goalSelector.addGoal(0, new AttemptToGoHomeGoal<>(this, 1.0));
        this.goalSelector.addGoal(0, new AttemptToGoBackGoal<>(this, 1.0));
    }

    protected void registerCommonGoals() {
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public int getStrafingTime() {
        return strafingTime;
    }

    @Override
    public void setStrafingTime(int strafingTime) {
        this.strafingTime = strafingTime;
        setInactionTime(strafingTime);
    }

    @Override
    public float getStrafingForward() {
        return strafingForward;
    }

    @Override
    public void setStrafingForward(float strafingForward) {
        this.strafingForward = strafingForward;
    }

    @Override
    public float getStrafingClockwise() {
        return strafingClockwise;
    }

    @Override
    public void setStrafingClockwise(float strafingClockwise) {
        this.strafingClockwise = strafingClockwise;
    }

}
