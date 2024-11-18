package com.p1nero.dote.worldgen.structure;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DOTEStructurePlacementTypes {
    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, DuelOfTheEndMod.MOD_ID);
    public static final RegistryObject<StructurePlacementType<PositionPlacement>> SPECIFIC_LOCATION_PLACEMENT_TYPE = STRUCTURE_PLACEMENT_TYPES.register("specific_location", () -> () -> PositionPlacement.CODEC);
}
