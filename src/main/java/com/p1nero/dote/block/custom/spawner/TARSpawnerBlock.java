package com.p1nero.dote.block.custom.spawner;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.archive.DataManager;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.entity.spawner.TARSpawnerBlockEntity;
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

public class TARSpawnerBlock extends BossSpawnerBlock{
    public TARSpawnerBlock(Properties pProperties) {
        super(pProperties, DOTEBlockEntities.SENBAI_SPAWNER_BLOCK_ENTITY::get);
    }

    /**
     * 和巴伦对话过才能召唤
     */
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(!pLevel.isClientSide){
            if(!pPlayer.isCreative() && DataManager.BarunTalked.get(pPlayer)){
                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            } else {
                pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip13"), true);
                return InteractionResult.sidedSuccess(false);
            }
        }
        return InteractionResult.sidedSuccess(true);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new TARSpawnerBlockEntity(blockPos, blockState);
    }
}
