package com.gaboj1.tcr.entity.custom.boss.second_boss;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.sword.AbstractSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
/**
 * 本来想继承自RainCutterSwordEntity的，但是区别太多了干脆重复一下。
 */
public class StellarSwordEntity extends AbstractArrow implements AbstractSwordEntity {

    private boolean inEntity;
    private Vec3 movement0;

    public StellarSwordEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(TCREntities.STELLAR_SWORD.get(), world);
    }

    public StellarSwordEntity(EntityType<? extends AbstractArrow> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public StellarSwordEntity(Level level) {
        this(TCREntities.STELLAR_SWORD.get(), level);
    }


    @Override
    public ItemStack getItemStack() {
        return Items.DIAMOND_SWORD.getDefaultInstance();
    }

    @Override
    public void setDeltaMovement(@NotNull Vec3 deltaMovement) {
        super.setDeltaMovement(deltaMovement);
        movement0 = deltaMovement;
    }

    @Override
    public void tick() {
        super.tick();

        //客户端才生效！
        if(tickCount == 0){
            level().addParticle(ParticleTypes.EXPLOSION, this.getX() , this.getY() , this.getZ() , 0,0,0);
            level().playSound(null , getX(), getY(), getZ(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS,1,1);
        }

        if(tickCount == 3){
            setDeltaMovement(movement0.scale(0.01));
        }

    }

    /**
     * 不自毁的版本
     */
    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if(inEntity){
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if(getOwner() instanceof LivingEntity livingEntity){
            entity.hurt(damageSources().mobAttack(livingEntity),20.0F);
        }
        setDeltaMovement(movement0.scale(0.01));
        inEntity = true;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return getItemStack();
    }

    /**
     * 跟RainCutter一样
     */
    @Override
    public void setPose(PoseStack poseStack) {
        double pitchRad = Math.toRadians(-getYRot());
        double yawRad = Math.toRadians(-getXRot());
        float xRot = (float) Math.toDegrees(yawRad * Math.cos(pitchRad));
        float zRot = (float) Math.toDegrees(yawRad * Math.sin(pitchRad));
        if(0 < -getYRot() && -getYRot() <180){
            zRot = -zRot;
        }

        poseStack.mulPose(Axis.XP.rotationDegrees((xRot)));
        poseStack.mulPose(Axis.YP.rotationDegrees(getYRot() - 90 ));
        poseStack.mulPose(Axis.ZP.rotationDegrees(zRot - 135f));
    }

}
