package com.gaboj1.tcr.entity.custom.boss;

import com.gaboj1.tcr.client.BossMusicPlayer;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ShadowableEntity;
import com.gaboj1.tcr.util.SaveUtil;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 方便统一调难度
 */
public abstract class TCRBoss extends PathfinderMob implements NpcDialogue, ShadowableEntity, LevelableEntity {

    @Nullable
    protected Player conversingPlayer;
    protected static final EntityDataAccessor<Boolean> IS_FIGHTING = SynchedEntityData.defineId(TCRBoss.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> IS_SHADER = SynchedEntityData.defineId(TCRBoss.class, EntityDataSerializers.BOOLEAN);
    protected final ServerBossEvent bossInfo;
    protected TCRBoss(EntityType<? extends PathfinderMob> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PURPLE);
    }

    protected TCRBoss(EntityType<? extends PathfinderMob> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        levelUp(SaveUtil.worldLevel);
        bossInfo = new ServerBossEvent(this.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
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
        //强制追，不知道为什么goal的那个追击坏了
        if(getTarget() != null && getEntityData().get(IS_FIGHTING)){
            this.getNavigation().moveTo(getTarget(), 1.0f);
        }

        //播放bgm
        if(level().isClientSide){
            if(getEntityData().get(IS_FIGHTING)){
                BossMusicPlayer.playBossMusic(this, getFightMusic(), 32);
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
            if (!this.level().isClientSide()) {
                this.lookAt(player, 180.0F, 180.0F);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        sendDialoguePacket(serverPlayer);
                        this.setConversingPlayer(serverPlayer);
                    }
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

}
