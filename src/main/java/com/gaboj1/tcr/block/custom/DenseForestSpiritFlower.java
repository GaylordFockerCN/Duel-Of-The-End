package com.gaboj1.tcr.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class DenseForestSpiritFlower extends FlowerBlock {
    public DenseForestSpiritFlower(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
        super(effectSupplier, p_53513_, p_53514_);
    }

    //TODO 抄凋零玫瑰的，到时候换自己的特效
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        VoxelShape $$4 = this.getShape(pState, pLevel, pPos, CollisionContext.empty());
        Vec3 $$5 = $$4.bounds().getCenter();
        double $$6 = (double)pPos.getX() + $$5.x;
        double $$7 = (double)pPos.getZ() + $$5.z;

        for(int $$8 = 0; $$8 < 3; ++$$8) {
            if (pRandom.nextBoolean()) {
                pLevel.addParticle(ParticleTypes.PORTAL, $$6 + pRandom.nextDouble() / 5.0,  (double)pPos.getY() + (0.5 - pRandom.nextDouble()), $$7 + pRandom.nextDouble() / 5.0, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide && pLevel.getDifficulty() != Difficulty.PEACEFUL) {
            if (pEntity instanceof LivingEntity) {
                LivingEntity $$4 = (LivingEntity)pEntity;
                if (!$$4.isInvulnerableTo(pLevel.damageSources().wither())) {
                    $$4.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
                }
            }
        }
    }
}
