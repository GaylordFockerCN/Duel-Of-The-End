package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.entity.custom.TCRAggressiveGeoMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class EliteSpawnerBlockEntity<T extends TCRAggressiveGeoMob> extends EntitySpawnerBlockEntity<T>{
    protected EliteSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
        super(type, entityType, pos, state);
    }

    @Override
    public T spawnMyBoss(ServerLevelAccessor accessor) {
        T mob = super.spawnMyBoss(accessor);
        mob.setElite(this.getBlockPos());
        return mob;
    }

    @Override
    public boolean shouldDestroySelf() {
        return true;
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }

}
