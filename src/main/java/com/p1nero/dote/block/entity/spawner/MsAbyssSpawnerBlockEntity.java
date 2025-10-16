package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.entity.custom.boss.ms_abyss.MsAbyssEntity;
import com.p1nero.dote.entity.custom.boss.sand_captain.SandCaptainEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MsAbyssSpawnerBlockEntity extends BossSpawnerBlockEntity<MsAbyssEntity>{


    public MsAbyssSpawnerBlockEntity(BlockEntityType<?> type, EntityType<MsAbyssEntity> entityType, BlockPos pos, BlockState state) {
        super(type, entityType, pos, state);
    }

    @Override
    public float getArenaRadius() {
        return 30;
    }
}
