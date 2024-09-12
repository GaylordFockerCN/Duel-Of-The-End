package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.block.TCRModBlockEntities;
import com.gaboj1.tcr.block.entity.spawner.MiaoYinSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiaoYinSpawnerBlock extends EntitySpawnerBlock {
    public MiaoYinSpawnerBlock(Properties properties) {
        super(properties, TCRModBlockEntities.MIAO_YIN_SPAWNER_BLOCK_ENTITY::get);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new MiaoYinSpawnerBlockEntity(blockPos, blockState);
    }

}
