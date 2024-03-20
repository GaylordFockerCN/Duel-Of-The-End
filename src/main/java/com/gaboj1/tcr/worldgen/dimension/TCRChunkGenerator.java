package com.gaboj1.tcr.worldgen.dimension;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.gaboj1.tcr.worldgen.noise.NoiseMapGenerator;
import com.gaboj1.tcr.worldgen.structure.BiomeForcedLandmarkPlacement;
import com.gaboj1.tcr.worldgen.structure.EnumStructures;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.noise.module.source.Perlin;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

import static com.gaboj1.tcr.worldgen.structure.EnumStructures.CHURCH;

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
        buildPeak(pLevel);
    }

    private void buildPeak(WorldGenRegion primer){

        BlockState grassTop = Blocks.GRASS_BLOCK.defaultBlockState();

        BlockState stone = Blocks.STONE.defaultBlockState();

        BlockState snowBlock = Blocks.SNOW_BLOCK.defaultBlockState();

        BlockState snow = Blocks.SNOW.defaultBlockState();

        Perlin perlin = new Perlin();
        perlin.setFrequency(0.03);
        if(this.getBiomeSource() instanceof TCRBiomeProvider provider){
            //群系噪声半径
            int r = (int) (provider.getCenter2().distance(provider.getMainCenter()) * NoiseMapGenerator.SCALE_OF_A_CENTER_R);

            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {

                    //判断是否属于第二群系中心
                    BlockPos pos = primer.getCenter().getWorldPosition().offset(x, 0, z);
                    Optional<ResourceKey<Biome>> biome = primer.getBiome(pos).unwrapKey();
                    if (!TCRBiomes.AZURE_SKIES.location().equals(biome.get().location()))
                        continue;
                    //NOTE! 要getCorrectValue，因为数组是正的，中心是(width/2,height/2),而实际群系中心是(0,0) 详情看BiomeForceLandMarkPlacement...
//                    int correctX = provider.getCorrectValue(pos.getX()>>2);
//                    int correctZ = provider.getCorrectValue(pos.getZ()>>2);
//                    Point center = provider.getCenter2();
//                    double dis = Math.sqrt(Math.pow(correctX - center.x,2)+Math.pow(correctZ-center.y,2)) * 4;//不能<<2哈哈
//                    double t = Math.max(0, r-dis);
//                    //double t = Math.pow(Math.max(0, r-dis),2) / 64;
//                    //根据距离进行缩放
//                    double scale = 0.01;
//                    int height = (int) (perlin.get(correctX*scale,0 ,correctZ*scale) * t) + (int)(perlin.get(correctX*scale,0 ,correctZ*scale)*10-10);

                    int height = provider.getMountainHeight(pos);

                    // 寻找最顶的方块
                    int gBase = 75;
                    for (int y = 80; y > gBase; y--) {
                        Block currentBlock = primer.getBlockState(pos.atY(y)).getBlock();
                        if (currentBlock != Blocks.AIR) {
                            gBase = y;
                            break;
                        }
                    }

                    //造山咯
                    int y  = 0;
                    while(y ++ < height-1){
                        primer.setBlock(pos.atY(gBase+y), stone, 3);
                    }
                    primer.setBlock(pos.atY(gBase+y), grassTop, 3);
                    if(y > 50-new Random().nextInt(20)){
                        primer.setBlock(pos.atY(gBase+y+1), new Random().nextBoolean()?snowBlock:snow, 3);
//                        primer.setBlock(pos.atY(gBase+y+2), snow, 3);
                    }
                }
            }
        }

    }

    private void fixPrimerSurface(WorldGenRegion primer){
        Perlin perlin = new Perlin();

        BlockState grassTop = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState dirt = Blocks.DIRT.defaultBlockState();
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

                if(gBase == 75) {
                    if(TCRConfig.MORE_HOLE.get()){
                        continue;//不填坑
                    }
                    gBase = 77;//TODO 填坑，但是数值有待调
                }

                //获取对应噪声值并填补地形
                double scale = 0.01;
                double perlinValue = perlin.get(pos.getX()*scale,0,pos.getZ()*scale);
                int height;
//                if(TCRBiomes.AZURE_SKIES.location().equals(biome.get().location())){
//                    height = Math.max(0,(int)(perlinValue*10-8));
//                }else {
//                    height = perlinValue==1 ? 1 : (perlinValue > 1 ? 2 : 0);
//                }
                height = perlinValue==1 ? 1 : (perlinValue > 1 ? 2 : 0);
                int i;
                for(i = 0; i < height-1; i++){
                    primer.setBlock(pos.atY(gBase+i), dirt, 3);
                }
                primer.setBlock(pos.atY(gBase+i), grassTop, 3);
            }
        }
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void createStructures(RegistryAccess pRegistryAccess, ChunkGeneratorStructureState pStructureState, StructureManager pStructureManager, ChunkAccess pChunk, StructureTemplateManager pStructureTemplateManager) {
        ChunkPos pos = pChunk.getPos();
        SectionPos sectionPos = SectionPos.bottomOf(pChunk);
        RandomState randomState = pStructureState.randomState();
        pStructureState.possibleStructureSets().forEach((value) -> {
            StructurePlacement structurePlacement = (value.value()).placement();
            List<StructureSet.StructureSelectionEntry> iterator = (value.value()).structures();
            Iterator var12 = iterator.iterator();

            while(var12.hasNext()) {
                StructureSet.StructureSelectionEntry $$11 = (StructureSet.StructureSelectionEntry)var12.next();
                StructureStart structureStart = pStructureManager.getStartForStructure(sectionPos, $$11.structure().value(), pChunk);
                if (structureStart != null && structureStart.isValid()) {
                    return;
                }
            }

            //此处加一行判断即可，其他全是抄原版的
            if ((structurePlacement instanceof BiomeForcedLandmarkPlacement biomeForcedLandmarkPlacement && biomeForcedLandmarkPlacement.isTCRPlacementChunk(this,pChunk,pos.x,pos.z)) || structurePlacement.isStructureChunk(pStructureState, pos.x, pos.z)) {
                System.out.println("Ok"+pos+" "+sectionPos+structurePlacement);
                if (iterator.size() == 1) {
                    this.tryGenerateStructure(iterator.get(0), pStructureManager, pRegistryAccess, randomState, pStructureTemplateManager, pStructureState.getLevelSeed(), pChunk, pos, sectionPos);
             } else {
                    ArrayList<StructureSet.StructureSelectionEntry> list = new ArrayList(iterator.size());
                    list.addAll(iterator);
                    WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
                    worldgenRandom.setLargeFeatureSeed(pStructureState.getLevelSeed(), pos.x, pos.z);
                    int i = 0;

                    StructureSet.StructureSelectionEntry $$16;
                    for(Iterator var15 = list.iterator(); var15.hasNext(); i += $$16.weight()) {
                        $$16 = (StructureSet.StructureSelectionEntry)var15.next();
                    }

                    while(!list.isEmpty()) {
                        int $$17 = worldgenRandom.nextInt(i);
                        int $$18 = 0;

                        for(Iterator var17 = list.iterator(); var17.hasNext(); ++$$18) {
                            StructureSet.StructureSelectionEntry $$19 = (StructureSet.StructureSelectionEntry)var17.next();
                            $$17 -= $$19.weight();
                            if ($$17 < 0) {
                                break;
                            }
                        }

                        StructureSet.StructureSelectionEntry selectionEntry = list.get($$18);
                        if (this.tryGenerateStructure(selectionEntry, pStructureManager, pRegistryAccess, randomState, pStructureTemplateManager, pStructureState.getLevelSeed(), pChunk, pos, sectionPos)) {
                            return;
                        }
                        list.remove($$18);
                        i -= selectionEntry.weight();
                    }

                }
            }
        });
    }

    int tries = 5;//太多怕爆栈
    private boolean tryGenerateStructure(StructureSet.StructureSelectionEntry pStructureSelectionEntry, StructureManager pStructureManager, RegistryAccess pRegistryAccess, RandomState pRandom, StructureTemplateManager pStructureTemplateManager, long pSeed, ChunkAccess pChunk, ChunkPos pChunkPos, SectionPos pSectionPos) {
        Structure $$9 = pStructureSelectionEntry.structure().value();
        int $$10 = fetchReferences(pStructureManager, pChunk, pSectionPos, $$9);
        HolderSet<Biome> $$11 = $$9.biomes();
        Objects.requireNonNull($$11);
        Predicate<Holder<Biome>> $$12 = $$11::contains;
        StructureStart $$13 = $$9.generate(pRegistryAccess, this, this.biomeSource, pRandom, pStructureTemplateManager, pSeed, pChunkPos, $$10, pChunk, $$12);
        if ($$13.isValid()) {
            pStructureManager.setStartForStructure(pSectionPos, $$9, $$13, pChunk);
            return true;
        } else {
//            //NOTE: 非常之危险，但是应该可以保证生成率？每次递归x多一格区块
            System.out.println("Try Again");
            if(tries-->0)
                tryGenerateStructure(pStructureSelectionEntry,pStructureManager,pRegistryAccess,pRandom,pStructureTemplateManager,pSeed,pChunk,new ChunkPos(pChunkPos.x+1,pChunkPos.z), pSectionPos);
            System.out.println("give up");
            return false;
        }
    }

    private static int fetchReferences(StructureManager pStructureManager, ChunkAccess pChunk, SectionPos pSectionPos, Structure pStructure) {
        StructureStart $$4 = pStructureManager.getStartForStructure(pSectionPos, pStructure, pChunk);
        return $$4 != null ? $$4.getReferences() : 0;
    }

    //用于搜结构
    @Nullable
    @Override
    public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel level, HolderSet<Structure> targetStructures, BlockPos pos, int searchRadius, boolean skipKnownStructures) {
        ChunkGeneratorStructureState state = level.getChunkSource().getGeneratorState();

        @Nullable
        Pair<BlockPos, Holder<Structure>> nearest = super.findNearestMapStructure(level, targetStructures, pos, searchRadius, skipKnownStructures);

        Map<BiomeForcedLandmarkPlacement, Set<Holder<Structure>>> placementSetMap = new Object2ObjectArrayMap<>();
        for (Holder<Structure> holder : targetStructures) {
            for (StructurePlacement structureplacement : state.getPlacementsForStructure(holder)) {
                if (structureplacement instanceof BiomeForcedLandmarkPlacement landmarkPlacement) {
                    placementSetMap.computeIfAbsent(landmarkPlacement, v -> new ObjectArraySet<>()).add(holder);
                }
            }
        }
        if (placementSetMap.isEmpty()) return nearest;
        if(this.getBiomeSource() instanceof TCRBiomeProvider provider){
            for (Map.Entry<BiomeForcedLandmarkPlacement, Set<Holder<Structure>>> landmarkPlacement : placementSetMap.entrySet()) {
                BiomeForcedLandmarkPlacement placement = landmarkPlacement.getKey();
                int structure = placement.structure;
                Point p = new Point(0,0);
                //在这里判断结构是什么，并且返回对应的点
                switch (structure){
                    case 0: p = provider.getMainCenter(); break;
                    case 1: p = provider.getCenter1(); break;
                }
                int chunkX = provider.deCorrectValue(p.x)>>2;
                int chunkZ = provider.deCorrectValue(p.y)>>2;
                BlockPos pos1 = new BlockPos(chunkX<<4, pos.getY(),chunkZ<<4);
                for (Holder<Structure> targetStructure : targetStructures) {
                    if (landmarkPlacement.getValue().contains(targetStructure)) {
                        nearest = new Pair<>(pos1, targetStructure);
                    }
                }
            }
        }

        return nearest;
    }

}
