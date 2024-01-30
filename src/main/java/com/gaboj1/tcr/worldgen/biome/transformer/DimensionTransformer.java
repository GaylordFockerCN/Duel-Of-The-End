package com.gaboj1.tcr.worldgen.biome.transformer;

public interface DimensionTransformer {
    int getParentX(int x);

    int getParentY(int z);
}
