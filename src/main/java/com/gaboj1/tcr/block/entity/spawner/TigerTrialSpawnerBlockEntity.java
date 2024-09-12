package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.block.TCRModBlockEntities;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.biome2.TigerEntity;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.TrialMaster;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TigerTrialSpawnerBlockEntity extends EntitySpawnerBlockEntity<TigerEntity>{
    private final List<Integer> tigers = new ArrayList<>();
    private boolean trialed, trialing;
    public TigerTrialSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(TCRModBlockEntities.TIGER_TRIAL_SPAWNER_BLOCK_ENTITY.get(), TCRModEntities.TIGER.get(), pos, state);
    }

    public void setTrialed(boolean trialed) {
        this.trialed = trialed;
    }

    public boolean isTrialed() {
        return trialed;
    }

    public boolean isTrialing() {
        return trialing;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("trialed", trialed);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        trialed = tag.getBoolean("trialed");
    }

    public void addTiger(TigerEntity entity){
        tigers.add(entity.getId());
        trialing = true;
    }

    public List<Integer> getTigers() {
        return tigers;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TigerTrialSpawnerBlockEntity blockEntity) {
        if(level.isClientSide){
            return;
        }
        boolean isEmpty = !blockEntity.getTigers().isEmpty();
        for(int entityId : blockEntity.getTigers()){
            if(level.getEntity(entityId) instanceof TigerEntity){
                isEmpty = false;
            }
        }
        if(blockEntity.isTrialing() && isEmpty){
            TrialMaster trialMaster = TCRModEntities.TRIAL_MASTER.get().create(level);
            if(trialMaster != null){
                blockEntity.trialing = false;
                blockEntity.setTrialed(true);
                SaveUtil.biome2.afterTrial = true;
                Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32, false);
                assert player != null;
                trialMaster.setPos(pos.getCenter().add(player.position()).scale(0.5));
                level.addFreshEntity(trialMaster);
                trialMaster.mobInteract(player, InteractionHand.MAIN_HAND);//发起对话
            }
        }
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return null;
    }

}
