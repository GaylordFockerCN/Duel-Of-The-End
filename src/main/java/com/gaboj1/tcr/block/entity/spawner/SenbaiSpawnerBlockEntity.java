package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.block.DOTEBlockEntities;
import com.gaboj1.tcr.entity.DOTEEntities;
import com.gaboj1.tcr.entity.custom.SenbaiDevil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class SenbaiSpawnerBlockEntity extends BossSpawnerBlockEntity<SenbaiDevil>{
    public SenbaiSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.SENBAI_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.SENBAI_DEVIL.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }
}
