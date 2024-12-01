package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.SenbaiDevil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SenbaiSpawnerBlockEntity extends BossSpawnerBlockEntity<SenbaiDevil>{
    public SenbaiSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.SENBAI_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.SENBAI_DEVIL.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    @Override
    public @Nullable ParticleOptions getBorderParticle() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

}
