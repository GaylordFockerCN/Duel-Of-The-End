package com.gaboj1.tcr.worldgen.biome.transformer;

public interface DimensionOffset0Transformer extends DimensionTransformer {
    @Override
    default int getParentX(int x) {
        return x;
    }

    @Override
    default int getParentY(int z) {
        return z;
    }
}
