package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import com.p1nero.dote.entity.custom.boss.sand_captain.SandCaptainCoffin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SandCaptainSpawnerBlockEntity<T extends DOTEBoss> extends BossSpawnerBlockEntity<T>{

    public SandCaptainSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
        super(type, entityType, pos, state);
    }

    @Override
    public EntityType<? extends DOTEBoss> getEntityType() {
        return DOTEEntities.SAND_CAPTAIN.get();
    }

    /**
     * 因为这玩意儿生的boss不一样，所以得改成sand captain
     */


    @Override
    public void startBossFight() {
        super.startBossFight();
        if(this.myBoss instanceof SandCaptainCoffin captainCoffin) {
            captainCoffin.playStartAnimation();
        }
    }

    @Override
    public BlockPos getSpawnPos(ServerLevelAccessor accessor) {
        return this.getBlockPos();
    }

    @Override
    public float getArenaRadius() {
        return 30;
    }

}
