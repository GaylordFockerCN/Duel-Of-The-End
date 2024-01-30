package com.gaboj1.tcr.worldgen.biome.transformer;

import com.gaboj1.tcr.worldgen.biome.Area;
import com.gaboj1.tcr.worldgen.biome.BigContext;
import com.gaboj1.tcr.worldgen.biome.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface AreaTransformer0 {
    default <R extends Area> R run(BigContext<R> context) {
        return context.createResult((x, z) -> {
            context.initRandom(x, z);
            return this.applyPixel(context, x, z);
        });
    }

    ResourceKey<Biome> applyPixel(Context context, int x, int z);
}
