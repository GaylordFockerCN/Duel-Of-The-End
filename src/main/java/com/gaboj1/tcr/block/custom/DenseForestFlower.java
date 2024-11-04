package com.gaboj1.tcr.block.custom;

import com.gaboj1.tcr.datagen.tags.TCREntityTagGenerator;
import com.gaboj1.tcr.item.custom.armor.TreeRobeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DenseForestFlower extends FlowerBlock {
    private final int durationTicks;
    private final int level;

    public DenseForestFlower(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
        super(effectSupplier, p_53513_, p_53514_);
        this.durationTicks = 20;
        this.level = 0;
    }

    public DenseForestFlower(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_, int durationTicks, int level) {
        super(effectSupplier, p_53513_, p_53514_);
        this.durationTicks = durationTicks;
        this.level = level;
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if(getParticle() == null){
            return;
        }
        VoxelShape $$4 = this.getShape(pState, pLevel, pPos, CollisionContext.empty());
        Vec3 $$5 = $$4.bounds().getCenter();
        double $$6 = (double)pPos.getX() + $$5.x;
        double $$7 = (double)pPos.getZ() + $$5.z;

        for(int $$8 = 0; $$8 < 3; ++$$8) {
            if (pRandom.nextBoolean()) {
                pLevel.addParticle(getParticle(), $$6 + pRandom.nextDouble() / 5.0,  (double)pPos.getY() + (0.5 - pRandom.nextDouble()), $$7 + pRandom.nextDouble() / 5.0, 0.0, 0.0, 0.0);
            }
        }
    }

    @Nullable
    public SimpleParticleType getParticle(){
        return null;
    }

    public MobEffectInstance getEffectWhenEntityIn(){
        return new MobEffectInstance(getSuspiciousEffect(), durationTicks, level);
    }

    /**
     * 防止密林自己的怪物被打hhh
     */
    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        if(pEntity.getType().is(TCREntityTagGenerator.MOB_IN_DENSE_FOREST) || TreeRobeItem.isFullSet(pEntity)){
            return;
        }
        if (!pLevel.isClientSide && pLevel.getDifficulty() != Difficulty.PEACEFUL) {
            if (pEntity instanceof LivingEntity entity) {
                entity.addEffect(getEffectWhenEntityIn());
            }
        }
    }
}
