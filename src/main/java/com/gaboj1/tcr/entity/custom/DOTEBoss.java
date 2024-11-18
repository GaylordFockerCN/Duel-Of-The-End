package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.archive.DOTEArchiveManager;
import com.gaboj1.tcr.client.BossMusicPlayer;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.ModifyAttackSpeedEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.DOTEPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.SyncUuidPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
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
public abstract class DOTEBoss extends DOTEMonster implements LevelableEntity, MultiPlayerBoostEntity {
    public static final Map<UUID, Integer> SERVER_BOSSES = new HashMap<>();//用于客户端渲染bossBar
    protected final ServerBossEvent bossInfo;
    protected DOTEBoss(EntityType<? extends PathfinderMob> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PURPLE);
    }

    protected DOTEBoss(EntityType<? extends PathfinderMob> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        levelUp(DOTEArchiveManager.getWorldLevel());
        bossInfo = new ServerBossEvent(this.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
        if(!level.isClientSide){
            PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncUuidPacket(bossInfo.getId(), getId()));
        }
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

    @Nullable
    public abstract SoundEvent getFightMusic();

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

}
