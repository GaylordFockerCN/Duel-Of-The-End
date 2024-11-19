package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.ThePyroclasOfPurgatory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class TPPSpawnerBlockEntity extends BossSpawnerBlockEntity<ThePyroclasOfPurgatory>{
    public TPPSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.TPP_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.THE_PYROCLAS_OF_PURGATORY.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }
}
