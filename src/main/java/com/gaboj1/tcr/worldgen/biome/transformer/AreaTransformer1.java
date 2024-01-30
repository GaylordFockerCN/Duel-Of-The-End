package com.gaboj1.tcr.worldgen.biome.transformer;

import com.gaboj1.tcr.worldgen.biome.Area;
import com.gaboj1.tcr.worldgen.biome.BigContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface AreaTransformer1 extends DimensionTransformer {
    default <R extends Area> R run(BigContext<R> context, R area) {
        return context.createResult((x, z) -> {
            context.initRandom(x, z);
            return this.applyPixel(context, area, x, z);
        }, area);
    }

    ResourceKey<Biome> applyPixel(BigContext<?> context, Area layer, int x, int z);
}