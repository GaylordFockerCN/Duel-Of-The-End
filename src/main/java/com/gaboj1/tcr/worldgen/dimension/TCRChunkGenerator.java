package com.gaboj1.tcr.worldgen.dimension;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import com.gaboj1.tcr.worldgen.structure.PositionPlacement;
import com.gaboj1.tcr.worldgen.structure.TCRStructuresEnum;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.noise.module.source.Perlin;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class TCRChunkGenerator extends NoiseBasedChunkGeneratorWrapper {

    public static final Codec<TCRChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ChunkGenerator.CODEC.fieldOf("wrapped_generator").forGetter(o -> o.delegate),
            NoiseGeneratorSettings.CODEC.fieldOf("noise_generation_settings").forGetter(NoiseBasedChunkGenerator::generatorSettings)
            ).apply(instance, TCRChunkGenerator::new));

    public TCRChunkGenerator(ChunkGenerator delegate, Holder<NoiseGeneratorSettings> noiseGeneratorSettingsHolder) {
        super(delegate, noiseGeneratorSettingsHolder);
    }

    //TODO 生成自己的地表
    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {
        super.buildSurface(pLevel, pStructureManager, pRandom, pChunk);
        buildMountain(pLevel);
    }

    private void buildMountain(WorldGenRegion primer){
        BlockState grassTop = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState dirt = Blocks.DIRT.defaultBlockState();
        BlockState stone = Blocks.STONE.defaultBlockState();
        BlockState snowBlock = Blocks.SNOW_BLOCK.defaultBlockState();
        BlockState snow = Blocks.SNOW.defaultBlockState();
        if(this.getBiomeSource() instanceof TCRBiomeProvider provider){

            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {

                    //判断是否属于第二群系中心
                    BlockPos pos = primer.getCenter().getWorldPosition().offset(x, 0, z);
                    Optional<ResourceKey<Biome>> biome = primer.getBiome(pos).unwrapKey();
                    if (!TCRBiomes.AZURE_SKIES.location().equals(biome.orElseThrow().location()))
                        continue;

                    //寻找和周围的方块的最大差值，防止石头露出
                    int maxDiff = 0;
                    int height = provider.getMountainHeight(pos);
                    int eastH = provider.getMountainHeight(pos.east());
                    int westH = provider.getMountainHeight(pos.west());
                    int northH = provider.getMountainHeight(pos.north());
                    int southH = provider.getMountainHeight(pos.south());
                    if (height > eastH || height > westH || height > northH || height > southH) {
                        maxDiff = Math.max(height - eastH, Math.max(height - westH, Math.max(height - northH, height - southH)));
                    }

                    // 寻找最顶的方块
                    int gBase = 75;
                    for (int y = 120; y > gBase; y--) {
                        Block currentBlock = primer.getBlockState(pos.atY(y)).getBlock();
                        if (currentBlock != Blocks.AIR) {
                            gBase = y;
                            break;
                        }
                    }

                    //造山咯
                    int snowLine = (int) (gBase + 60 + 30 * Math.sin(2 * Math.PI * pos.getX() / 500.0));
                    int y  = 0;
                    if(height < snowLine){
                        while(y <= height - 3){
                            primer.setBlock(pos.atY(gBase + y), stone, 3);
                            y++;
                        }
                        while(y <= height){
                            primer.setBlock(pos.atY(gBase + y), dirt, 3);
                            y++;
                        }
                    } else {
                        //防止石头露出雪来
                        while(y <= height - maxDiff){
                            primer.setBlock(pos.atY(gBase + y), stone, 3);
                            y++;
                        }
                        while(y <= height){
                            primer.setBlock(pos.atY(gBase + y), snowBlock, 3);
                            y++;
                        }
                    }

                    if(y < snowLine){
                        primer.setBlock(pos.atY(gBase + y), grassTop, 3);
                    } else {
                        primer.setBlock(pos.atY(gBase + y - 1), snowBlock, 3);
                        primer.setBlock(pos.atY(gBase + y), snow, 3);
                    }
                }
            }
        }

    }

    //填补原始地形。因为非平原的情况下地形一言难尽。。
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
//                    if(TCRConfig.MORE_HOLE.get()){
//                        continue;//不填坑
//                    }
                    gBase = 77;//TODO 填坑，但是数值有待调
                }

                //获取对应噪声值并填补地形
                double scale = 0.01;
                double perlinValue = perlin.get(pos.getX()*scale,0,pos.getZ()*scale);
                int height;
//                if(TCRBiomes.AZURE_SKIES.location().equals(biome.get().location())){
//                    height = Math.max(0,(int)(perlinValue*10-10));
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

    /**
     * 从原版抄的，重点在判断StructurePlacement的时候加入自己的判断。如果返回true即可在该位置生成结构。
     */
    @Override
    public void createStructures(@NotNull RegistryAccess pRegistryAccess, ChunkGeneratorStructureState pStructureState, @NotNull StructureManager pStructureManager, ChunkAccess pChunk, @NotNull StructureTemplateManager pStructureTemplateManager) {
        ChunkPos pos = pChunk.getPos();
        SectionPos sectionPos = SectionPos.bottomOf(pChunk);
        RandomState randomState = pStructureState.randomState();
        pStructureState.possibleStructureSets().forEach((value) -> {
            StructurePlacement structurePlacement = (value.value()).placement();
            List<StructureSet.StructureSelectionEntry> iterator = (value.value()).structures();

            for (StructureSet.StructureSelectionEntry $$11 : iterator) {
                StructureStart structureStart = pStructureManager.getStartForStructure(sectionPos, $$11.structure().value(), pChunk);
                if (structureStart != null && structureStart.isValid()) {
                    return;
                }
            }

            //此处加一行判断即可，其他全是抄原版的
            if ((structurePlacement instanceof PositionPlacement positionPlacement && positionPlacement.isTCRPlacementChunk(this,pChunk,pos.x,pos.z)) || structurePlacement.isStructureChunk(pStructureState, pos.x, pos.z)) {
                TheCasketOfReveriesMod.LOGGER.info("Ok"+pos+" "+sectionPos+structurePlacement);
                if (iterator.size() == 1) {
                    this.tryGenerateStructure(iterator.get(0), pStructureManager, pRegistryAccess, randomState, pStructureTemplateManager, pStructureState.getLevelSeed(), pChunk, pos, sectionPos);
             } else {
                    ArrayList<StructureSet.StructureSelectionEntry> list = new ArrayList<>(iterator.size());
                    list.addAll(iterator);
                    WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
                    worldgenRandom.setLargeFeatureSeed(pStructureState.getLevelSeed(), pos.x, pos.z);
                    int i = 0;

                    StructureSet.StructureSelectionEntry $$16;
                    for(Iterator<StructureSet.StructureSelectionEntry> var15 = list.iterator(); var15.hasNext(); i += $$16.weight()) {
                        $$16 = var15.next();
                    }

                    while(!list.isEmpty()) {
                        int $$17 = worldgenRandom.nextInt(i);
                        int $$18 = 0;

                        for(Iterator<StructureSet.StructureSelectionEntry> var17 = list.iterator(); var17.hasNext(); ++$$18) {
                            StructureSet.StructureSelectionEntry $$19 = var17.next();
                            $$17 -= $$19.weight();
                            if ($$17 < 0) {
                                break;
                            }
                        }

                        StructureSet.StructureSelectionEntry selectionEntry = list.get($$18);
                        if (this.tryGenerateOtherStructure(selectionEntry, pStructureManager, pRegistryAccess, randomState, pStructureTemplateManager, pStructureState.getLevelSeed(), pChunk, pos, sectionPos)) {
                            return;
                        }
                        list.remove($$18);
                        i -= selectionEntry.weight();
                    }

                }
            }
        });
    }

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
        }
        return false;
    }

    /**
     * 检查不能和地标过近
     */
    private boolean tryGenerateOtherStructure(StructureSet.StructureSelectionEntry pStructureSelectionEntry, StructureManager pStructureManager, RegistryAccess pRegistryAccess, RandomState pRandom, StructureTemplateManager pStructureTemplateManager, long pSeed, ChunkAccess pChunk, ChunkPos pChunkPos, SectionPos pSectionPos) {
        if(getBiomeSource() instanceof TCRBiomeProvider provider){
            for(TCRStructuresEnum structure : TCRStructuresEnum.values()){
                Point p = structure.getPoint();
                int chunkX = provider.deCorrectValue(p.x)>>2;
                int chunkZ = provider.deCorrectValue(p.y)>>2;

                if(Math.pow(chunkX - pChunkPos.x, 2) + Math.pow(chunkZ - pChunkPos.z, 2) < Math.pow(TCRConfig.MIN_CHUNK_BETWEEN_STRUCTURE.get(), 2)){
                    return false;
                }
            }
        }
        return this.tryGenerateStructure(pStructureSelectionEntry, pStructureManager, pRegistryAccess, pRandom, pStructureTemplateManager, pSeed, pChunk, pChunkPos, pSectionPos);
    }

    private static int fetchReferences(StructureManager pStructureManager, ChunkAccess pChunk, SectionPos pSectionPos, Structure pStructure) {
        StructureStart $$4 = pStructureManager.getStartForStructure(pSectionPos, pStructure, pChunk);
        return $$4 != null ? $$4.getReferences() : 0;
    }

    /**
     * 搜索结构必备，不能少
     */
    @Nullable
    @Override
    public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel level, @NotNull HolderSet<Structure> targetStructures, @NotNull BlockPos pos, int searchRadius, boolean skipKnownStructures) {
        ChunkGeneratorStructureState state = level.getChunkSource().getGeneratorState();

        @Nullable
        Pair<BlockPos, Holder<Structure>> nearest = super.findNearestMapStructure(level, targetStructures, pos, searchRadius, skipKnownStructures);

        Map<PositionPlacement, Set<Holder<Structure>>> placementSetMap = new Object2ObjectArrayMap<>();
        for (Holder<Structure> holder : targetStructures) {
            for (StructurePlacement structureplacement : state.getPlacementsForStructure(holder)) {
                if (structureplacement instanceof PositionPlacement landmarkPlacement) {
                    placementSetMap.computeIfAbsent(landmarkPlacement, v -> new ObjectArraySet<>()).add(holder);
                }
            }
        }
        if (placementSetMap.isEmpty()) return nearest;
        if(this.getBiomeSource() instanceof TCRBiomeProvider provider){
            for (Map.Entry<PositionPlacement, Set<Holder<Structure>>> landmarkPlacement : placementSetMap.entrySet()) {
                PositionPlacement placement = landmarkPlacement.getKey();
                Point p = new Point(0,0);
                //在这里判断结构是什么，并且返回对应的点
                for(TCRStructuresEnum structure : TCRStructuresEnum.values()){
                    if(structure.ordinal() == placement.structure){
                        p = structure.getPoint();
                        break;
                    }
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
