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

    //不知道会不会起作用
    public boolean isTCRPlacementChunk(TCRChunkGenerator chunkGen, ChunkAccess pChunk, int chunkX, int chunkZ) {

        if(chunkGen.getBiomeSource() instanceof TCRBiomeProvider provider){
            int correctX = provider.getCorrectValue(chunkX << 2);
            int correctZ = provider.getCorrectValue(chunkZ << 2);

//            if(this.structure == EnumStructures.CHURCH.ordinal() && correctX == provider.getCenter1().x && correctZ == provider.getCenter1().y){
//                return true;
//            }
            //存在误差，所以用一个范围好
            int size = 2;
            if(this.structure == EnumStructures.CHURCH.ordinal()
                    && correctX >= provider.getCenter1().x - size && correctZ >= provider.getCenter1().y-size
                        && correctX <= provider.getCenter1().x + size && correctZ <= provider.getCenter1().y+size
                            && !hasGenerated){
//                BlockPos pos = new BlockPos(chunkX<<4,80,chunkZ<<4);
//                System.out.println((chunkX<<4)+","+(chunkZ<<4)+pChunk.getBlockState(pos).getBlock());
//                if(pChunk.getBlockState(pos).getBlock() != Blocks.AIR){//因为有些地面是后面补的，防止生成在地下。
//                    hasGenerated = true;
//                    return true;
//                }
                //FIXME 不想修了，看看大结构会不会也有这个bug吧妈的（建筑生成不了的bug）
                return true;
            }

            size = 3;
            if(this.structure == EnumStructures.FINAL.ordinal()
                    && correctX >= provider.getMainCenter().x - size && correctZ >= provider.getMainCenter().y-size
                    && correctX <= provider.getMainCenter().x + size && correctZ <= provider.getMainCenter().y+size
                    && !hasGenerated){
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
