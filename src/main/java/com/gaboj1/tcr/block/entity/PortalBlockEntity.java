package com.gaboj1.tcr.block.entity;

import com.gaboj1.tcr.init.TCRModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class PortalBlockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private int id = 0;
    public static final int maxID = 4;

    private boolean isUnlock = false;

    public boolean isUnlock() {
        return isUnlock;
    }

    public PortalBlockEntity(BlockPos pos, BlockState state) {
        super(TCRModBlockEntities.PORTAL_BLOCK_ENTITY.get(), pos, state);
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

    public void changeId(Player player){
        id++;
        if(id > maxID){
            id = 0;
        }
        player.sendSystemMessage(Component.literal("ID: "+id));
    }

    public void unlock(){
        isUnlock = true;
    }

    public String getID(){
        switch (id){
            case 1:
                return "boss1Unlocked";
            case 2:
                return "boss2Unlocked";
            case 3:
                return "boss3Unlocked";
            case 4:
                return "boss4Unlocked";
            default:
                return "finalUnlocked";
        }
    }

    public void activateAnim(){
        triggerAnim("Activate","activate1");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));

        controllerRegistrar.add(new AnimationController<>(this, "Activate", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("activate1", RawAnimation.begin().thenPlay("activate")));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(isUnlock){
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

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
}