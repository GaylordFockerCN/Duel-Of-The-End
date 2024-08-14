package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.block.entity.spawner.BossSpawnerBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.YggdrasilSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YggdrasilSpawnerBlock extends BaseEntityBlock {
    public YggdrasilSpawnerBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new YggdrasilSpawnerBlockEntity(blockPos, blockState);
    }

}
