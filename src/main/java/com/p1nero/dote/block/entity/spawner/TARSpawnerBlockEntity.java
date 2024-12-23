package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.TheArbiterOfRadiance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TARSpawnerBlockEntity extends BossSpawnerBlockEntity<TheArbiterOfRadiance>{
    public TARSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.TAR_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.THE_ARBITER_OF_RADIANCE.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }

    @Nullable
    public ParticleOptions getBorderParticle(){
        return ParticleTypes.END_ROD;
    };

}