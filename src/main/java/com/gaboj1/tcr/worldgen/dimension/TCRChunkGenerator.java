package com.gaboj1.tcr.worldgen.dimension;

import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.noise.module.source.Perlin;

import java.util.Optional;

public class TCRChunkGenerator extends NoiseBasedChunkGeneratorWrapper {

    public static final Codec<TCRChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ChunkGenerator.CODEC.fieldOf("wrapped_generator").forGetter(o -> o.delegate),
            NoiseGeneratorSettings.CODEC.fieldOf("noise_generation_settings").forGetter(o -> o.generatorSettings())
            ).apply(instance, TCRChunkGenerator::new));

    public TCRChunkGenerator(ChunkGenerator delegate, Holder<NoiseGeneratorSettings> noiseGeneratorSettingsHolder) {
        super(delegate, noiseGeneratorSettingsHolder);
    }

    //TODO 生成自己的地表
    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {
        super.buildSurface(pLevel, pStructureManager, pRandom, pChunk);
        fixPrimerSurface(pLevel);
    }

    private void fixPrimerSurface(WorldGenRegion primer){

        Perlin perlin = new Perlin();
        perlin.setLacunarity(2);
        perlin.setPersistence(0.5);
        perlin.setOctaveCount(6);

        BlockState grassTop = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState grass = Blocks.DIRT.defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
                BlockPos pos = primer.getCenter().getWorldPosition().offset(x, 0, z);
                Optional<ResourceKey<Biome>> biome = primer.getBiome(pos).unwrapKey();
                if (biome.isEmpty() ||
                        TCRBiomes.AIR.location().equals(biome.get().location()) ||
                            TCRBiomes.FINAL.location().equals(biome.get().location())) continue;
                // 寻找最顶的方块
                int gBase = 75;
                for (int y = 80; y > gBase; y--) {
                    Block currentBlock = primer.getBlockState(pos.atY(y)).getBlock();
                    if (currentBlock != Blocks.AIR) {
                        gBase = y;
                        break;
                    }
                }

                if(gBase == 75)
//                    continue;
                    gBase = 78;

                //获取对应噪声值并填补地形
                double scale = 0.01;
                double perlinValue = perlin.get(pos.getX()*scale,0,pos.getZ()*scale);
                int height = perlinValue==1 ? 1 : (perlinValue > 1 ? 2 : 0);
                int i;
                for(i = 0; i < height-1; i++){
                    primer.setBlock(pos.atY(gBase+i), grass, 3);
                }
                primer.setBlock(pos.atY(gBase+i), grassTop, 3);
            }
        }
    }

    private boolean inCircle(int r,int x,int y){
        double d = Math.sqrt(Math.pow((x - r), 2) + Math.pow((y - r), 2));
        return d <= r;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

}
