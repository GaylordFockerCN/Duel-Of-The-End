package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import com.p1nero.dote.entity.custom.boss.sand_captain.SandCaptainCoffin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SandCaptainSpawnerBlockEntity<T extends DOTEBoss> extends BossSpawnerBlockEntity<T>{

    public SandCaptainSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
        super(type, entityType, pos, state);
    }

    /**
     * 因为这玩意儿生的boss不一样，所以得改成sand captain
     */
    @Override
    protected void searchBoss() {
        if (this.inBossFight && (this.myBoss == null || this.myBoss.isRemoved()) && level instanceof ServerLevel serverLevel) {
            List<? extends DOTEBoss> entities = serverLevel.getEntities(DOTEEntities.SAND_CAPTAIN.get(), LivingEntity::isAlive);
            if(!entities.isEmpty()) {
                myBoss = entities.get(0);
            } else {
                inBossFight = false;
                myBoss = null;
                syncAndSave();
            }
        }
    }

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
