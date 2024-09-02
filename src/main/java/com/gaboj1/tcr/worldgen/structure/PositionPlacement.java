package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;

/**
 * 从暮色得到灵感，在关键方法判断该区是否是指定的位置。
 * @author LZY
 */
public class PositionPlacement extends StructurePlacement {
    public static final Codec<PositionPlacement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("structure").forGetter(p -> p.structure)//对不起太菜了只能用数字枚举然后一个个判断
    ).apply(inst, PositionPlacement::new));

    public final int structure;

    public boolean hasGenerated;

    public PositionPlacement(int structure) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1f, 0, Optional.empty()); // None of these params matter except for possibly flat-world or whatever
        this.structure = structure;
        this.hasGenerated = false;
    }

    public boolean isTCRPlacementChunk(TCRChunkGenerator chunkGen, ChunkAccess pChunk, int chunkX, int chunkZ) {

        if(chunkGen.getBiomeSource() instanceof TCRBiomeProvider provider){
            int correctX = provider.getCorrectValue(chunkX << 2);
            int correctZ = provider.getCorrectValue(chunkZ << 2);

            for(TCRStructuresEnum structure : TCRStructuresEnum.values()){
                if(checkStructure(structure, structure.getPoint(), correctX, correctZ))
                    return true;
            }

        }

        return false;
    }

    /**
     * 判断该区块是否复合map所指定的结构的位置
     */
    private boolean checkStructure(TCRStructuresEnum structure, Point point, int correctX, int correctZ){

        int deOffsetX = point.x - structure.getOffsetX();
        int deOffsetZ = point.y - structure.getOffsetZ();
        int size = structure.getSize();
        //存在误差，所以用一个范围好，而且大结构需要多个区块
        if(this.structure == structure.ordinal()
                && correctX >= deOffsetX - size && correctZ >= deOffsetZ-size
                    && correctX <= deOffsetX  + size && correctZ <= deOffsetZ+size
                        && !hasGenerated){
            hasGenerated = true;//防止多次生成
            return true;
        }
        return false;
    }

    @Override
    protected boolean isPlacementChunk(@NotNull ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
        //反正应该或许不会被调用吧
        return false;
    }

    @Override
    public @NotNull StructurePlacementType<?> type() {
        return TCRStructurePlacementTypes.SPECIFIC_LOCATION_PLACEMENT_TYPE.get();
    }

}
