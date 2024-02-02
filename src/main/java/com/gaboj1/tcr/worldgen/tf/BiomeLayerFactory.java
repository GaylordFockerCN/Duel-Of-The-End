package com.gaboj1.tcr.worldgen.tf;

import java.util.function.LongFunction;

public interface BiomeLayerFactory {
    LazyArea build(LongFunction<LazyAreaContext> contextFactory);

    BiomeLayerType getType();
}
