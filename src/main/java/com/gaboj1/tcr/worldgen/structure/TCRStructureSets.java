package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class TCRStructureSets {

    public static final ResourceKey<StructureSet> FLOWER_ALTAR = registerKey("flower_altar");

    private static ResourceKey<StructureSet> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, TheCasketOfReveriesMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(FLOWER_ALTAR, new StructureSet(structures.getOrThrow(TCRStructures.FLOWER_ALTAR), new BiomeForcedLandmarkPlacement(EnumStructures.FLOWER_ALTAR.ordinal())));
    }
}
