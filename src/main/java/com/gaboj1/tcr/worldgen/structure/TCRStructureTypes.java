package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TCRStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, TheCasketOfReveriesMod.MOD_ID);

    public static final RegistryObject<StructureType<Structure>> FLOWER_ALTAR = registerType("flower_altar", () -> () -> Structure.DIRECT_CODEC);

    private static <P extends Structure> RegistryObject<StructureType<P>> registerType(String name, Supplier<StructureType<P>> factory) {
        return STRUCTURE_TYPES.register(name, factory);
    }
}
