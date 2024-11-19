package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.TheShadowOfTheEnd;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class TSESpawnerBlockEntity extends BossSpawnerBlockEntity<TheShadowOfTheEnd>{
    public TSESpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.TSE_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.THE_SHADOW_OF_THE_END.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }
}
