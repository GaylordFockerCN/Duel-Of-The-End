package com.gaboj1.tcr.worldgen.dimension;

import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * 不叠一层似乎没法用上CODEC
 */
public abstract class NoiseBasedChunkGeneratorWrapper extends NoiseBasedChunkGenerator {
    public final ChunkGenerator delegate;

    public NoiseBasedChunkGeneratorWrapper(ChunkGenerator delegate, Holder<NoiseGeneratorSettings> noiseGeneratorSettingsHolder) {
        super(delegate.getBiomeSource(),noiseGeneratorSettingsHolder);
        this.delegate = delegate;
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState random, BiomeManager biomeManager, StructureManager manager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
        this.delegate.applyCarvers(region, seed, random, biomeManager, manager, chunkAccess, carving);
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager manager, RandomState random, ChunkAccess chunkAccess) {
        this.delegate.buildSurface(level, manager, random, chunkAccess);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        this.delegate.spawnOriginalMobs(region);
    }

    @Override
    public int getSpawnHeight(LevelHeightAccessor level) {
        return this.delegate.getSpawnHeight(level);
    }

    @Override
    public int getGenDepth() {
        return this.delegate.getGenDepth();
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunkAccess) {
        return this.delegate.fillFromNoise(executor, blender, random, structureManager, chunkAccess);
    }

    @Override
    public int getSeaLevel() {
        return this.delegate.getSeaLevel();
    }

    @Override
    public int getMinY() {
        return this.delegate.getMinY();
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightMap, LevelHeightAccessor level, RandomState random) {
        return this.delegate.getBaseHeight(x, z, heightMap, level, random);
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState random) {
        return this.delegate.getBaseColumn(x, z, level, random);
    }
}
