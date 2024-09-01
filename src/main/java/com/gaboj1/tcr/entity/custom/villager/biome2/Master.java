package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

/**
 * 不受彼此伤害
 */
public class Master extends TCRVillager {
    public Master(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 142857);
    }

    public static final EntityDataAccessor<Integer> BOSS_ID = SynchedEntityData.defineId(Master.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        getEntityData().define(BOSS_ID, -1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        getEntityData().set(BOSS_ID, tag.getInt("boss_id"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("boss_id", getEntityData().get(BOSS_ID));
    }

    public void setBossId(int id){
        getEntityData().set(BOSS_ID, id);
    }

    @Override
    public void tick() {
        super.tick();
        //如果没有绑定的boss就紫砂
        if(!level().isClientSide){
            int bossId = getEntityData().get(BOSS_ID);
            if(bossId != -1 && !(level().getEntity(bossId) instanceof SecondBossEntity)){
                this.setHealth(0);
            }
        }

    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightningBolt) {}

    /**
     * 免疫除了来自玩家和第二boss以外的伤害
     */
    @Override
    public boolean hurt(DamageSource source, float v) {
        //TODO 加一个用SaveUtil判断
        if(source.getEntity() instanceof Player || source.getEntity() instanceof YggdrasilEntity){
            return super.hurt(source, v);
        }
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);
        controllers.add(new AnimationController<>(this, "Summon", 10, state -> PlayState.STOP)
                .triggerableAnim("summon", RawAnimation.begin().thenPlay("summon")));
    }

    @Override
    public boolean isFriendly() {
        //TODO 改为用SaveUtil判断
        return false;
    }

    @Override
    public boolean isAngry() {
        //TODO 改为用SaveUtil判断
        return true;
    }
}
