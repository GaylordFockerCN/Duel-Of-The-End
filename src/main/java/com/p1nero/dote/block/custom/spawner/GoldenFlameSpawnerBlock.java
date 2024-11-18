package com.p1nero.dote.block.custom.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.entity.spawner.GoldenFlameSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GoldenFlameSpawnerBlock extends BossSpawnerBlock{
    public GoldenFlameSpawnerBlock(Properties pProperties) {
        super(pProperties, DOTEBlockEntities.GOLDEN_FLAME_SPAWNER_BLOCK_ENTITY::get);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new GoldenFlameSpawnerBlockEntity(blockPos, blockState);
    }
}
