package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.biome2.BigHammerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BigHammerSpawnerBlockEntity extends EliteSpawnerBlockEntity<BigHammerEntity>{
    public BigHammerSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(TCRBlockEntities.BIG_HAMMER_SPAWNER_BLOCK_ENTITY.get(), TCREntities.BIG_HAMMER.get(), pos, state);
    }

}
