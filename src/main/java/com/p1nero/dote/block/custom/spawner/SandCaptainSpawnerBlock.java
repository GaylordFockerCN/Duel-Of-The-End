package com.p1nero.dote.block.custom.spawner;

import com.p1nero.dote.block.DOTEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class SandCaptainSpawnerBlock extends BossSpawnerBlock{

    public SandCaptainSpawnerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return DOTEBlocks.SAND_CAPTAIN_SPAWNER_ENTITY.create(blockPos, blockState);
    }

}
