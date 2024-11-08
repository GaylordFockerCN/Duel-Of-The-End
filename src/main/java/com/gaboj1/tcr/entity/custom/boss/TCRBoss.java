package com.gaboj1.tcr.entity.custom.boss;

import com.gaboj1.tcr.client.BossMusicPlayer;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ShadowableEntity;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.SyncUuidPacket;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 方便统一调难度
 */
public abstract class TCRBoss extends PathfinderMob implements NpcDialogue, ShadowableEntity, LevelableEntity, MultiPlayerBoostEntity {
    public static final Map<UUID, Integer> SERVER_BOSSES = new HashMap<>();//用于客户端渲染bossBar
    @Nullable
    protected Player conversingPlayer;
    protected static final EntityDataAccessor<Boolean> IS_FIGHTING = SynchedEntityData.defineId(TCRBoss.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> IS_SHADER = SynchedEntityData.defineId(TCRBoss.class, EntityDataSerializers.BOOLEAN);
    protected final ServerBossEvent bossInfo;
    private int turningLockTicks = 0, movementLockTicks = 0;
    private float lockYRot0 = 0;
    private Vec3 pos0 = Vec3.ZERO;
    protected TCRBoss(EntityType<? extends PathfinderMob> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PURPLE);
    }

    protected TCRBoss(EntityType<? extends PathfinderMob> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        levelUp(TCRArchiveManager.getWorldLevel());
        bossInfo = new ServerBossEvent(this.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
        if(!level.isClientSide){
            PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new SyncUuidPacket(bossInfo.getId(), getId()));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(IS_FIGHTING, false);
        getEntityData().define(IS_SHADER, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(IS_FIGHTING,tag.getBoolean("is_fighting"));
        this.getEntityData().set(IS_SHADER,tag.getBoolean("is_shader"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("is_fighting", this.getEntityData().get(IS_FIGHTING));
        tag.putBoolean("is_shader", this.getEntityData().get(IS_SHADER));
    }

    @Override
    public void setConversingPlayer(@Nullable Player player) {
        conversingPlayer = player;
    }

    @Nullable
    @Override
    public Player getConversingPlayer() {
        return conversingPlayer;
    }

    @Override
    public void setShadow() {
        getEntityData().set(IS_SHADER, true);
    }

    @Override
    public boolean isShadow() {
        return getEntityData().get(IS_SHADER);
    }

    public boolean isFighting() {
        return getEntityData().get(IS_FIGHTING);
    }

    public void setIsFighting(boolean isFighting){
        getEntityData().set(IS_FIGHTING, isFighting);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float v) {
        //防止超模
        if(v > getMaxHealth() / 50){
            v = getMaxHealth() / 50;
        }
        return super.hurt(source, v);
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

    /**
     * 锁转向
     */
    public void turningLock(int tick){
        turningLockTicks = tick;
        lockYRot0 = getYRot();
    }

    /**
     * 锁移动
     */
    public void movementLock(int tick){
        movementLockTicks = tick;
        pos0 = position();
    }

    @Override
    public void tick() {
        super.tick();

        if(turningLockTicks > 0 && lockYRot0 != 0){
            turningLockTicks--;
            setYBodyRot(lockYRot0);
            setYHeadRot(lockYRot0);
            setYRot(lockYRot0);
        }

        if(movementLockTicks > 0 && !pos0.equals(Vec3.ZERO)){
            movementLockTicks--;
            setPos(pos0);
        } else if(getTarget() != null && getEntityData().get(IS_FIGHTING)){
            //强制追，不知道为什么goal的那个追击坏了
            this.getNavigation().moveTo(getTarget(), 1.0f);
        }

        //播放bgm
        if(level().isClientSide){
            if(getEntityData().get(IS_FIGHTING)){
                BossMusicPlayer.playBossMusic(this, getEntityData().get(IS_FIGHTING)? getFightMusic() : null, 32);
            }
        }

        //保险，防止追玩家
        if(!getEntityData().get(IS_FIGHTING)){
            setTarget(null);
        }

    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (player instanceof ServerPlayer serverPlayer) {
                this.lookAt(player, 180.0F, 180.0F);
                if(TCRArchiveManager.isNoPlotMode()){
                    getEntityData().set(IS_FIGHTING, true);
                } else if (this.getConversingPlayer() == null) {
                    sendDialoguePacket(serverPlayer);
                    this.setConversingPlayer(serverPlayer);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    protected abstract void sendDialoguePacket(ServerPlayer serverPlayer);

    /**
     * 返回一个范围
     * @param pos 中心位置
     * @param offset 半径
     * @return 以pos为中心offset的两倍为边长的一个正方体
     */
    public static AABB getPlayerAABB(BlockPos pos, int offset){
        return new AABB(pos.offset(offset,offset,offset),pos.offset(-offset,-offset,-offset));
    }

    @Nullable
    public abstract SoundEvent getFightMusic();

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

}
