package com.p1nero.dote.block.custom.spawner;

import com.p1nero.dote.block.DOTEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class MsAbyssSpawnerBlock extends BossSpawnerBlock{

    public MsAbyssSpawnerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return DOTEBlocks.MS_ABYSS_SPAWNER_ENTITY.create(blockPos, blockState);
    }


    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
//        blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()
        return Block.box(0, 0.0, 0, 16.0, 12, 16.0);
    }
}
