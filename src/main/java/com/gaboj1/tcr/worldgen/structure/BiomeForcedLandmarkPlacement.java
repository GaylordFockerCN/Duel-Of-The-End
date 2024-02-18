package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

//TODO 改判断区域逻辑
public class BiomeForcedLandmarkPlacement extends StructurePlacement {
    public static final Codec<BiomeForcedLandmarkPlacement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("structure").forGetter(p -> p.structure)//对不起太菜了只能用数字枚举然后一个个判断
    ).apply(inst, BiomeForcedLandmarkPlacement::new));

    private final int structure;

    public BiomeForcedLandmarkPlacement(int structure) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1f, 0, Optional.empty()); // None of these params matter except for possibly flat-world or whatever
        this.structure = structure;
    }

    //不知道会不会起作用
    public boolean isTCRPlacementChunk(TCRChunkGenerator chunkGen, ChunkGeneratorStructureState state, int chunkX, int chunkZ) {

        if(chunkGen.getBiomeSource() instanceof TCRBiomeProvider provider){
            int correctX = provider.getCorrectValue(chunkX << 2);
            int correctZ = provider.getCorrectValue(chunkZ << 2);

            if(this.structure == EnumStructures.CHURCH.ordinal() && correctX == provider.getCenter1().x && correctZ == provider.getCenter1().y){
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
