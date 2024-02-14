package com.gaboj1.tcr.worldgen.structure;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface DecorationClearance {

    boolean shouldAdjustToTerrain();

    default int adjustForTerrain(Structure.GenerationContext context, int x, int z) {
        return this.shouldAdjustToTerrain()
                ? Mth.clamp(context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()), context.chunkGenerator().getSeaLevel() + 1, context.chunkGenerator().getSeaLevel() + 7)
                : context.chunkGenerator().getSeaLevel();
    }

}
