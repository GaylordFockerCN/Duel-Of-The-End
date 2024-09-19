package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.MiaoYin;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class MiaoYinSpawnerBlockEntity extends EntitySpawnerBlockEntity<MiaoYin>{

    public MiaoYinSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(TCRBlockEntities.MIAO_YIN_SPAWNER_BLOCK_ENTITY.get(), TCREntities.MIAO_YIN.get(), pos, state);
    }

    @Override
    public MiaoYin spawnMyBoss(ServerLevelAccessor accessor) {
        if(SaveUtil.biome2.trialTalked2 || SaveUtil.biome2.chooseEnd3()){
            return super.spawnMyBoss(accessor);
        }
        return null;
    }

    @Override
    protected int getRange() {
        return 64;
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
