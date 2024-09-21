package com.gaboj1.tcr.block.entity;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.util.DataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PortalBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int id = 0;
    public static final int maxID = 8;// 0~3: village ; 4~7: boss; 8: final

    private boolean isUnlock = false;

    public boolean isUnlock() {
        return isUnlock;
    }

    public void unlock(){
        isUnlock = true;
    }

    public boolean isPlayerUnlock(Player player){
        return DataManager.portalPointUnlockData.get(id).get(player);
    }

    public void setPlayerUnlock(Player player){
        DataManager.portalPointUnlockData.get(id).put(player, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PortalBlockEntity(BlockPos pos, BlockState state) {
        super(TCRBlockEntities.PORTAL_BLOCK_ENTITY.get(), pos, state);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("TeleportId",id);
        tag.putBoolean("isUnlock",isUnlock);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        id = tag.getInt("TeleportId");
        isUnlock = tag.getBoolean("isUnlock");
        super.load(tag);
    }

    public void changeId(){
        id++;
        if(id > maxID){
            id = 0;
        }
        TheCasketOfReveriesMod.LOGGER.info(this + " id has changed to: "+id);
    }

    public void activateAnim(){
        triggerAnim("Activate","activate1");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));

        controllerRegistrar.add(new AnimationController<>(this, "Activate", 0, state -> PlayState.STOP)
                .triggerableAnim("activate1", RawAnimation.begin().thenPlay("activate")));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(isUnlock()){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("unlock", Animation.LoopType.LOOP));
        }else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}