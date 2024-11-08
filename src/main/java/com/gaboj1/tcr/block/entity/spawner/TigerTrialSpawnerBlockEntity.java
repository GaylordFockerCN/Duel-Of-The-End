package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.biome2.TigerEntity;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.TrialMaster;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class TigerTrialSpawnerBlockEntity extends EntitySpawnerBlockEntity<TigerEntity> implements GeoBlockEntity {
    private final List<Integer> tigers = new ArrayList<>();
    private boolean trialed, trialing;
    public TigerTrialSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(TCRBlockEntities.TIGER_TRIAL_SPAWNER_BLOCK_ENTITY.get(), TCREntities.TIGER.get(), pos, state);
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
    public boolean shouldDestroySelf() {
        return false;
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

    /**
     * 实时检测是否挑战成功
     * 挑战成功则召唤试炼主并进行对话
     */
    public static void tick(Level level, BlockPos pos, TigerTrialSpawnerBlockEntity blockEntity) {
        if(level.isClientSide){
            double rx = pos.getX() + level.getRandom().nextFloat();
            double ry = pos.getY() + level.getRandom().nextFloat();
            double rz = pos.getZ() + level.getRandom().nextFloat();
            level.addParticle(blockEntity.getSpawnerParticle(), rx, ry, rz, 0.0D, 0.0D, 0.0D);
            return;
        }
        boolean isEmpty = !blockEntity.getTigers().isEmpty();
        for(int entityId : blockEntity.getTigers()){
            if(level.getEntity(entityId) instanceof TigerEntity){
                isEmpty = false;
            }
        }
        if(blockEntity.isTrialing() && isEmpty){
            TrialMaster trialMaster = TCREntities.TRIAL_MASTER.get().create(level);
            if(trialMaster != null){
                blockEntity.trialing = false;
                blockEntity.setTrialed(true);
                TCRArchiveManager.biome2.afterTrial = true;
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
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }

}
