package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.structure.BiomeForcedLandmarkPlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TCRStructurePlacementTypes {
    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, TheCasketOfReveriesMod.MOD_ID);

    public static final RegistryObject<StructurePlacementType<BiomeForcedLandmarkPlacement>> FORCED_LANDMARK_PLACEMENT_TYPE = registerPlacer("forced_landmark", () -> () -> BiomeForcedLandmarkPlacement.CODEC);

    private static <P extends StructurePlacement> RegistryObject<StructurePlacementType<P>> registerPlacer(String name, Supplier<StructurePlacementType<P>> factory) {
        return STRUCTURE_PLACEMENT_TYPES.register(name, factory);
    }
}
