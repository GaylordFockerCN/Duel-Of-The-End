package com.gaboj1.tcr.worldgen.tree.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class DenseSpiritTreeTrunkPlacer extends MegaJungleTrunkPlacer {
    public static final Codec<DenseSpiritTreeTrunkPlacer> CODEC = RecordCodecBuilder.create((i) -> {
        return trunkPlacerParts(i).apply(i, DenseSpiritTreeTrunkPlacer::new);
    });

    public DenseSpiritTreeTrunkPlacer(int p_70193_, int p_70194_, int p_70195_) {
        super(p_70193_, p_70194_, p_70195_);
    }

    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader p_226140_, @NotNull BiConsumer<BlockPos, BlockState> p_226141_, @NotNull RandomSource p_226142_, int p_226143_, @NotNull BlockPos p_226144_, @NotNull TreeConfiguration p_226145_) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        list.addAll(super.placeTrunk(p_226140_, p_226141_, p_226142_, p_226143_, p_226144_, p_226145_));

        for(int i = p_226143_ - 2 - p_226142_.nextInt(4); i > p_226143_ / 2; i -= 2 + p_226142_.nextInt(4)) {
            float f = p_226142_.nextFloat() * ((float)Math.PI * 2F);
            int j = 0;
            int k = 0;

            for(int l = 0; l < 5; ++l) {
                j = (int)(1.5F + Mth.cos(f) * (float)l);
                k = (int)(1.5F + Mth.sin(f) * (float)l);
                BlockPos blockpos = p_226144_.offset(j, i - 3 + l / 2, k);
                this.placeLog(p_226140_, p_226141_, p_226142_, blockpos, p_226145_);
            }

            list.add(new FoliagePlacer.FoliageAttachment(p_226144_.offset(j, i, k), -2, false));
        }

        return list;
    }
}
