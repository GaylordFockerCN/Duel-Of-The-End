package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.DOTEConfig;
import com.gaboj1.tcr.block.entity.spawner.BossSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class BossSpawnerBlock extends BaseEntityBlock {
    Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType;
    protected BossSpawnerBlock(Properties pProperties, Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType) {
        super(pProperties);
        this.blockEntityType = blockEntityType;
    }

    /**
     * 右键两次召唤历战版boss，只负责刷材料，与剧情无关
     */
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(DOTEConfig.ENABLE_BOSS_SPAWN_BLOCK_LOAD.get()){
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof BossSpawnerBlockEntity<?> bossSpawnerBlockEntity && pPlayer instanceof ServerPlayer serverPlayer){
                bossSpawnerBlockEntity.tryToSpawnShadow(serverPlayer);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, blockEntityType.get(), BossSpawnerBlockEntity::tick);
    }

}
