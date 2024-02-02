package com.gaboj1.tcr.worldgen.tf.transformer;

public interface DimensionTransformer {
    int getParentX(int x);

    int getParentY(int z);
}
