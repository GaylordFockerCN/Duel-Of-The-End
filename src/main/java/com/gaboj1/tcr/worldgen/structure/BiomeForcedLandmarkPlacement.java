package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

//TODO 改判断区域逻辑
public class BiomeForcedLandmarkPlacement extends StructurePlacement {
    public static final Codec<BiomeForcedLandmarkPlacement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("structure").forGetter(p -> p.structure)//对不起太菜了只能用数字枚举然后一个个判断
//            ,Codec.BOOL.fieldOf("hasGenerated").forGetter(p -> p.hasGenerated)//好像不是很有必要写进去来着
    ).apply(inst, BiomeForcedLandmarkPlacement::new));

    public final int structure;

    public boolean hasGenerated;

    public BiomeForcedLandmarkPlacement(int structure) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1f, 0, Optional.empty()); // None of these params matter except for possibly flat-world or whatever
        this.structure = structure;
//        this.hasGenerated = hasGenerated;
        this.hasGenerated = false;
    }

    public boolean isTCRPlacementChunk(TCRChunkGenerator chunkGen, ChunkAccess pChunk, int chunkX, int chunkZ) {

        if(chunkGen.getBiomeSource() instanceof TCRBiomeProvider provider){
            int correctX = provider.getCorrectValue(chunkX << 2);
            int correctZ = provider.getCorrectValue(chunkZ << 2);

//            if(this.structure == EnumStructures.CHURCH.ordinal() && correctX == provider.getCenter1().x && correctZ == provider.getCenter1().y){
//                return true;
//            }
            //存在误差，所以用一个范围好，而且大结构需要多个区块
            int size = 2;
            if(this.structure == EnumStructures.CHURCH.ordinal()
                    && correctX >= provider.getCenter1().x - size && correctZ >= provider.getCenter1().y-size
                        && correctX <= provider.getCenter1().x + size && correctZ <= provider.getCenter1().y+size
                            && !hasGenerated){
                hasGenerated = true;//防止多次生成
                return true;
            }



            //采用结构方块递归生成，所以不需要很大空间，但是需要偏移
            //竞技场半径104，所以转换成群系坐标即为 104>>2 = 26
            int deOffsetX = provider.getMainCenter().x -26;
            int deOffsetZ = provider.getMainCenter().y -26;
            if(this.structure == EnumStructures.FINAL.ordinal()
                    && correctX >= deOffsetX - size && correctZ >= deOffsetZ-size
                    && correctX <= deOffsetX  + size && correctZ <= deOffsetZ+size
                    && !hasGenerated){
                hasGenerated = true;
                return true;
            }

        }

        return false;
    }

    @Override
    protected boolean isPlacementChunk(ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
        //反正应该或许不会被调用吧

        return false;
    }

    @Override
    public StructurePlacementType<?> type() {
        return TCRStructurePlacementTypes.FORCED_LANDMARK_PLACEMENT_TYPE.get();
    }

}
