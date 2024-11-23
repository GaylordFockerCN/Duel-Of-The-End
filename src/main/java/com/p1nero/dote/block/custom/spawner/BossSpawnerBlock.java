package com.p1nero.dote.block.custom.spawner;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.entity.spawner.BossSpawnerBlockEntity;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.entity.custom.DOTEMonster;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class BossSpawnerBlock extends BaseEntityBlock {
    Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType;
    protected BossSpawnerBlock(Properties pProperties, Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType) {
        super(pProperties);
        this.blockEntityType = blockEntityType;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return Block.box(0.0, 0.0, 0.0, 16.0, 26.0, 16.0);
    }

    /**
     * 用往生精华右键以召唤boss
     */
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof BossSpawnerBlockEntity<?> bossSpawnerBlockEntity && !pLevel.isClientSide){
            int r = DOTEConfig.SPAWNER_BLOCK_PROTECT_RADIUS.get() - 5;
            //防止小怪打扰
            if(!pLevel.getEntitiesOfClass(DOTEMonster.class, new AABB(pPos.offset(-r, -r, -r), pPos.offset(r, r, r))).isEmpty()){
                pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip3"), true);
                return InteractionResult.sidedSuccess(false);
            }
            //强制1v1
            if(pLevel.getEntitiesOfClass(Player.class, new AABB(pPos.offset(-r, -r, -r), pPos.offset(r, r, r))).size() != 1){
                pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip4"), true);
                return InteractionResult.sidedSuccess(false);
            }
            if(bossSpawnerBlockEntity.getMyEntity() == null && pPlayer.getItemInHand(pHand).is(DOTEItems.IMMORTALESSENCE.get())){
                pPlayer.getItemInHand(pHand).shrink(1);
                if(pLevel instanceof ServerLevel serverLevel) {
                    bossSpawnerBlockEntity.spawnMyBoss(serverLevel);
                    bossSpawnerBlockEntity.setCurrentPlayer(pPlayer);
                    serverLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), DOTESounds.LOTUSHEAL.get(), SoundSource.BLOCKS, 1, 1);
                    for (int i = 0; i < 10; i++) {
                        double rx = pPos.getX() + pLevel.getRandom().nextFloat();
                        double ry = pPos.getY() + pLevel.getRandom().nextFloat();
                        double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
                        serverLevel.sendParticles(ParticleTypes.SOUL, rx, ry + 2.0F, rz, 1, 0.0D, 0.01D, 0.0D, 0.01);
                    }
                }
            } else {
                pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip1").append(bossSpawnerBlockEntity.getEntityType().getDescription()), true);
            }

        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    /**
     * 重置可召唤状态，生成粒子等
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return BossSpawnerBlockEntity::tick;
    }

}
