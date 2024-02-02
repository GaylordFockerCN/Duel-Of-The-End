package com.gaboj1.tcr.worldgen.tf;

import com.mojang.serialization.Codec;

@FunctionalInterface
public interface BiomeLayerType {
    Codec<? extends BiomeLayerFactory> getCodec();
}
