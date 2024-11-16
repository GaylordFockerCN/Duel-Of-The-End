package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TCRStructurePlacementTypes {
    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<StructurePlacementType<PositionPlacement>> SPECIFIC_LOCATION_PLACEMENT_TYPE = registerPlacer("specific_location", () -> () -> PositionPlacement.CODEC);

    private static <P extends StructurePlacement> RegistryObject<StructurePlacementType<P>> registerPlacer(String name, Supplier<StructurePlacementType<P>> factory) {
        return STRUCTURE_PLACEMENT_TYPES.register(name, factory);
    }
}
