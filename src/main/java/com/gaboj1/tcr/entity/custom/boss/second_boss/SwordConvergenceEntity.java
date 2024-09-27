package com.gaboj1.tcr.entity.custom.boss.second_boss;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.sword.AbstractSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 万剑归宗
 */
public class SwordConvergenceEntity extends AbstractArrow implements AbstractSwordEntity {
    private Vec3 target0 = Vec3.ZERO;//从土里钻出来要聚集的地方
    private int shootingTick = 0, shootingCountDown = Integer.MAX_VALUE;
    private final double dis = 3 + random.nextInt(7), height = 3 + random.nextDouble();
    private boolean isFinalTargetPosPassed = false;

    public SwordConvergenceEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(TCREntities.CONVERGENCE_SWORD.get(), world);
    }

    public SwordConvergenceEntity(EntityType<? extends AbstractArrow> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public SwordConvergenceEntity(Level level, Vec3 target0) {
        this(TCREntities.CONVERGENCE_SWORD.get(), level);
        this.target0 = target0;
    }

    @NotNull
    @Override
    public SecondBossEntity getOwner() {
        if(super.getOwner() instanceof SecondBossEntity entity){
            return entity;
        }
        discard();
        return new SecondBossEntity(TCREntities.SECOND_BOSS.get(), level());
    }

    @Override
    public ItemStack getItemStack() {
        return Items.DIAMOND_SWORD.getDefaultInstance();
    }

    @Override
    public void tick() {
        super.tick();

        double offsetY = 0.05 * Math.sin(tickCount * 0.1);
//        double offsetXZ = 0.5 * Math.sin(Math.toRadians(tickCount));
        double offsetXZ = 0.1 * Math.sin(tickCount * Math.PI);
        //还没飞到集合点就飞到集合点，飞到了就不动
        if(!getOwner().isShooting){

            //懒得发包同步了
            if(target0 == Vec3.ZERO) {
                target0 = getOwner().position().add(0, 3, 0);
            }

            //计算玩家视角和剑方向的夹角，如果在身后则抬高
            Vec2 ownerPosVec2, ownerViewVec2;
            ownerPosVec2 = new Vec2((float) (getX() - getOwner().getX()), (float) (getZ() - getOwner().getZ()));
            ownerViewVec2 = new Vec2(((float) getOwner().getViewVector(1.0f).x), ((float) getOwner().getViewVector(1.0f).z));
            double dotProduct = ownerPosVec2.dot(ownerViewVec2);
            double cosTheta = dotProduct / (ownerPosVec2.length() * ownerViewVec2.length());
            double angleInRadians = Math.acos(cosTheta);

            //如果还没到玩家身边则飞向玩家
            if(getPosition(1.0f).distanceTo(target0) > dis){
                setDeltaMovement(target0.subtract(getPosition(1.0f)).normalize().add(0,offsetY * 5,0).scale(0.5f));
            } else {
                //如果到了但是在身后就抬高一点。夹角大于九十则视为在身后。
                //如果到指定位置了就停下并矫正角度（注意还没到不要矫正）。
                if(angleInRadians > Math.PI / 2 && getY() < target0.y + height){
                    setDeltaMovement(0,0.1,0);
                } else if(getY() > target0.y + height + 1){
                    setDeltaMovement(0,-0.1,0);
                } else {
                    setDeltaMovement(Vec3.ZERO);
                }
                setXRot(-getOwner().getXRot());
                setYRot(-getOwner().getYRot());
            }
        } else {
            //陆续发射，每次20根
            if(shootingCountDown / 20 > 1){
                if(shootingCountDown == Integer.MAX_VALUE){
                    shootingCountDown = tickCount;
                } else {
                    shootingCountDown--;
                }
                setYRot((float) Math.toDegrees(Math.atan2(getOwner().dir.x, getOwner().dir.z)));
                setXRot((float) Math.toDegrees(Math.atan2(getOwner().dir.y, getOwner().dir.horizontalDistance())));

                return;
            }

            //先射向目标，再随机抖动前进。
            shootingTick++;
//            double offsetXZ = 0;
            if(!isFinalTargetPosPassed){
                if(getPosition(1.0f).distanceTo(getOwner().finalTargetPos) < 1){
                    isFinalTargetPosPassed = true;
                    level().playSound(null, getOnPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 1f, 1f);
                }
                if(getOwner().finalTargetPos == Vec3.ZERO) {
                    getOwner().finalTargetPos = getOwner().getViewVector(1.0f).scale(5.0F);
                }
                setDeltaMovement(getOwner().finalTargetPos.subtract(getPosition(1.0f)).normalize().add(offsetXZ, offsetY,offsetXZ));
            }else {
                if(getOwner().dir == Vec3.ZERO) {
                    getOwner().dir = getOwner().getViewVector(1.0f);
                }
                setDeltaMovement(getOwner().dir.normalize().add(offsetXZ,offsetY,offsetXZ));
            }

            //起飞一定时间后紫砂
            if(shootingTick > 100){
                discard();
            }
        }

        //保险
        if(tickCount > 142857){
            discard();
        }

    }



    /**
     * 不自毁的版本
     */
    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(entity.getId() != getOwner().getId()){
            entity.hurt(damageSources().mobAttack(getOwner()),0.5f);
            level().playSound(null, getOnPos(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.BLOCKS,1f,1f);
            level().addParticle(ParticleTypes.SMOKE, getX(),getY(),getZ(),0,0,0);
        }
    }

    /**
     * 穿透方块？
     */
    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {}

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

    /**
     * 批量生成剑
     */
    public static void summonSwords(LivingEntity livingEntity, int swordCnt, int r){
        RandomSource random = livingEntity.getRandom();
        double angle = random.nextDouble() * Math.PI / 2; // 生成0到90度之间的角度
        double radius = 10 + Math.abs(random.nextDouble()) * r; // 生成半径。开局远一点比较好看
        double y = 2 + Math.abs(random.nextDouble()) * r / 4;
        int type = swordCnt % 4;
        double x = switch (type){
            case 1, 2 -> -radius * Math.cos(angle);
            default -> radius * Math.cos(angle);
        };
        double z = switch (type){
            case 0, 1 -> radius * Math.sin(angle);
            default -> -radius * Math.sin(angle);
        };
        summonSword(livingEntity, livingEntity.getX()+x,livingEntity.getY()+y,livingEntity.getZ()+z);
    }

    /**
     * 召唤单根剑
     * @param livingEntity 发射者位置
     * @param x x 偏移
     * @param y y 偏移
     * @param z z 偏移
     */
    public static void summonSword(LivingEntity livingEntity, double x, double y, double z, double targetX, double targetY, double targetZ){
        SwordConvergenceEntity sword = new SwordConvergenceEntity(livingEntity.level(), new Vec3(targetX, targetY, targetZ));
        sword.setOwner(livingEntity);
        sword.setNoGravity(true);
        sword.setBaseDamage(0);
        sword.setSilent(false);
        sword.pickup = AbstractArrow.Pickup.DISALLOWED;
        sword.setKnockback(1);//击退
        sword.setPierceLevel((byte) 5);//穿透
        sword.setPos(x,y,z);
        sword.setDeltaMovement(new Vec3(targetX,targetY,targetZ).subtract(sword.getPosition(1.0f)).normalize());
        livingEntity.level().playSound(null, sword.getOnPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 0.1f,0.5f);
        livingEntity.level().addFreshEntity(sword);
    }

    /**
     * 召唤单根剑
     * @param livingEntity 发射者位置
     * @param x x 偏移
     * @param y y 偏移
     * @param z z 偏移
     */
    public static void summonSword(LivingEntity livingEntity, double x, double y, double z){
        summonSword(livingEntity, x, y, z, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
    }

}
