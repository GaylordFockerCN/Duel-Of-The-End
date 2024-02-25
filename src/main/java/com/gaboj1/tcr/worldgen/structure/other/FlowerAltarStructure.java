package com.gaboj1.tcr.worldgen.structure.other;

import com.gaboj1.tcr.datagen.tags.ModBiomeTagGenerator;
import com.gaboj1.tcr.worldgen.structure.DecorationClearance;
import com.gaboj1.tcr.worldgen.structure.TCRStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlowerAltarStructure extends Structure implements DecorationClearance {

    public static final Codec<FlowerAltarStructure> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Structure.settingsCodec(instance))
            .apply(instance, FlowerAltarStructure::new));
    int seed = 0;
    public FlowerAltarStructure(StructureSettings structureSettings) {
        super(structureSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();

        boolean dontCenter = this.dontCenter();
        int x = (chunkPos.x << 4) + (dontCenter ? 0 : 7);
        int z = (chunkPos.z << 4) + (dontCenter ? 0 : 7);

        int y = this.adjustForTerrain(context, x, z);

        return Optional
                .ofNullable(this.getFirstPiece(context, RandomSource.create(context.seed() + chunkPos.x * 25117L + chunkPos.z * 151121L), chunkPos, x, y, z))
                .map(piece -> getStructurePieceGenerationStubFunction(piece, context, x, y, z))
                ;
    }

    private static Structure.GenerationStub getStructurePieceGenerationStubFunction(StructurePiece startingPiece, GenerationContext context, int x, int y, int z) {
        return new GenerationStub(new BlockPos(x, y, z), structurePiecesBuilder -> {
            structurePiecesBuilder.addPiece(startingPiece);
            startingPiece.addChildren(startingPiece, structurePiecesBuilder, context.random());
        });
    }

    @Deprecated
    protected boolean dontCenter() {
        return true;
    }

    @Nullable
    protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
        return null;
    }

    @Override
    public StructureType<?> type() {
        return TCRStructureTypes.FLOWER_ALTAR.get();
    }

    public static FlowerAltarStructure buildStructureConfig(BootstapContext<Structure> context) {
        return new FlowerAltarStructure(
                new Structure.StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(ModBiomeTagGenerator.VALID_BIOME1),
                        Arrays.stream(MobCategory.values()).collect(Collectors.toMap(category -> category, category -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE
                )
        );
    }

    @Override
    public boolean shouldAdjustToTerrain() {
        return false;
    }
}
