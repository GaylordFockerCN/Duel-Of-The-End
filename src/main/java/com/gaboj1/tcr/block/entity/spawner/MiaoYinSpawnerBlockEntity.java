package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.block.TCRModBlockEntities;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.biome2.TigerEntity;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.MiaoYin;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.TrialMaster;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MiaoYinSpawnerBlockEntity extends EntitySpawnerBlockEntity<MiaoYin>{
    public MiaoYinSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(TCRModBlockEntities.MIAO_YIN_SPAWNER_BLOCK_ENTITY.get(), TCRModEntities.MIAO_YIN.get(), pos, state);
    }

    @Override
    public MiaoYin spawnMyBoss(ServerLevelAccessor accessor) {
        if(!SaveUtil.biome2.trialTalked2){
            return null;
        }
        return super.spawnMyBoss(accessor);
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
