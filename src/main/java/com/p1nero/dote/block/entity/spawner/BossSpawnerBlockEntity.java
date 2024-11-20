package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.entity.HomePointEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BossSpawnerBlockEntity<T extends Mob & HomePointEntity> extends EntitySpawnerBlockEntity<T> {
	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, entityType, pos, state);
	}

}
