package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.client.BossMusicPlayer;
import com.p1nero.dote.entity.LevelableEntity;
import com.p1nero.dote.entity.MultiPlayerBoostEntity;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.packet.clientbound.SyncUuidPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 方便统一调难度
 */
public abstract class DOTEBoss extends DOTEMonster implements MultiPlayerBoostEntity {
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
        if(level().isClientSide){
            BossMusicPlayer.playBossMusic(this, getFightMusic(), 32);
        }

    }

    @Override
    public void die(@NotNull DamageSource source) {
        level().playSound(null , getX(), getY(), getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS,1,1);
        super.die(source);
    }

    @Nullable
    public abstract SoundEvent getFightMusic();

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

}
