package com.p1nero.dote.block.custom.spawner;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.entity.spawner.TSESpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TSESpawnerBlock extends BossSpawnerBlock{
    public TSESpawnerBlock(Properties pProperties) {
        super(pProperties, DOTEBlockEntities.SENBAI_SPAWNER_BLOCK_ENTITY::get);
    }

    /**
     * 打过森白和神王和炎魔才能挑战
     */
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(DOTEArchiveManager.BIOME_PROGRESS_DATA.isSenbaiFought() && DOTEArchiveManager.BIOME_PROGRESS_DATA.isGoldenFlameFought() && DOTEArchiveManager.BIOME_PROGRESS_DATA.isBoss2fought()){
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        } else {
            pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip10"), true);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new TSESpawnerBlockEntity(blockPos, blockState);
    }
}
