package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.block.entity.spawner.BigHammerSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BigHammerSpawnerBlock extends EntitySpawnerBlock {

    public BigHammerSpawnerBlock(Properties pProperties) {
        super(pProperties, TCRBlockEntities.BIG_HAMMER_SPAWNER_BLOCK_ENTITY::get);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BigHammerSpawnerBlockEntity(blockPos, blockState);
    }

}
