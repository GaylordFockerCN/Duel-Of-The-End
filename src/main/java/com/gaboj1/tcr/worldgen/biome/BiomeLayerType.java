package com.gaboj1.tcr.worldgen.biome;

import com.mojang.serialization.Codec;

@FunctionalInterface
public interface BiomeLayerType {
    Codec<? extends BiomeLayerFactory> getCodec();
}
