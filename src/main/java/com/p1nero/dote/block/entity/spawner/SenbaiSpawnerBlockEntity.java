package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.boss.senbai.SenbaiDevil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SenbaiSpawnerBlockEntity extends BossSpawnerBlockEntity<SenbaiDevil>{
    public SenbaiSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(DOTEBlockEntities.SENBAI_SPAWNER_BLOCK_ENTITY.get(), DOTEEntities.SENBAI_DEVIL.get(), pos, state);
    }

    @Override
    public float getArenaRadius() {
        return 30;
    }
}
