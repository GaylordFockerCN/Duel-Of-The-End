package com.gaboj1.tcr.worldgen.dimension;

import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;

public class TCRChunkGenerator extends NoiseBasedChunkGenerator {
    public TCRChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> noiseGeneratorSettingsHolder) {
        super(biomeSource, noiseGeneratorSettingsHolder);
    }

    //TODO 生成自己的地表
    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {
        super.buildSurface(pLevel, pStructureManager, pRandom, pChunk);



    }
}
