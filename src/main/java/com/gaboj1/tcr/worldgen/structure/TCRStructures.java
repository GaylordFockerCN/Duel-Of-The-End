package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.structure.other.FlowerAltarStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TCRStructures {

    public static final ResourceKey<Structure> FLOWER_ALTAR = registerKey("flower_altar");


    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, TheCasketOfReveriesMod.prefix(name));
    }

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstapContext<Structure> context) {
            context.register(FLOWER_ALTAR, FlowerAltarStructure.buildStructureConfig(context));
    }

}
