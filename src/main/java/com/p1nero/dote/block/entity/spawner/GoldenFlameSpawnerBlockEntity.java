package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.GoldenFlame;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class GoldenFlameSpawnerBlockEntity extends BossSpawnerBlockEntity<GoldenFlame>{
    public GoldenFlameSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.GOLDEN_FLAME_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.GOLDEN_FLAME.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }
}
