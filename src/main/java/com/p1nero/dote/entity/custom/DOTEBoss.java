package com.p1nero.dote.entity.custom;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.block.entity.spawner.BossSpawnerBlockEntity;
import com.p1nero.dote.client.BossMusicPlayer;
import com.p1nero.dote.entity.HomePointEntity;
import com.p1nero.dote.entity.ai.goal.AttemptToGoHomeGoal;
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
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 方便统一调难度
 */
public abstract class DOTEBoss extends DOTEMonster implements HomePointEntity {
    protected static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(DOTEBoss.class, EntityDataSerializers.BLOCK_POS);
    public static final Map<UUID, Integer> SERVER_BOSSES = new HashMap<>();//用于客户端渲染bossBar
    protected final ServerBossEvent bossInfo;
    protected DOTEBoss(EntityType<? extends PathfinderMob> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PURPLE);
    }

    protected DOTEBoss(EntityType<? extends PathfinderMob> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        bossInfo = new ServerBossEvent(this.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
        if(!level.isClientSide){
            PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncUuidPacket(bossInfo.getId(), getId()));
        }
    }

    public float getHealthRatio(){
        return getHealth() / getMaxHealth();
    }

    @Override
    public int getExperienceReward() {
        return 1000;
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

    @Override
    public float getHomeRadius() {
        return DOTEConfig.SPAWNER_BLOCK_PROTECT_RADIUS.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(HOME_POS, getOnPos());
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
        if (!this.level().isClientSide()) {
            bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public void tick() {
        super.tick();

        //播放bgm
        if(level().isClientSide && this.isAlive()){
            BossMusicPlayer.playBossMusic(this, getFightMusic(), 32);
        } else {
            if(level().getBlockEntity(getHomePos()) instanceof BossSpawnerBlockEntity<?> bossSpawnerBlockEntity){
                if(bossSpawnerBlockEntity.getMyEntity() == null || !bossSpawnerBlockEntity.getMyEntity().getType().equals(this.getType())){
                    explodeAndDiscard();
                }
            } else {
                explodeAndDiscard();
            }
        }

    }

    /**
     * 演出效果而已不能炸死玩家qwq
     */
    public void explodeAndDiscard(){
        if(level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 10, 0.0D, 0.1D, 0.0D, 0.01);
            serverLevel.playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1, 1);
        }
        discard();
    }

    /**
     * 免疫远程且仅能被玩家攻击
     */
    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        //为了bvb
        if(source.getEntity() instanceof DOTEBoss){
            return super.hurt(source, p_21017_);
        }
        //防止雷劈火烧等bug，以及免疫所有远程，别想逃课！
        if(!(source.getEntity() instanceof Player) || source.isIndirect()){
            return false;
        }
        return super.hurt(source, p_21017_);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        level().playSound(null , getX(), getY(), getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS,1,1);
        if(level().isClientSide){
            BossMusicPlayer.stopBossMusic(this);
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
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new AttemptToGoHomeGoal<>(this, 1.0));
    }
}
