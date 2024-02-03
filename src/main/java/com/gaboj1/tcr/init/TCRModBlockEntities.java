package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.BetterStructureBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TheCasketOfReveriesMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<BetterStructureBlockEntity>> BETTER_STRUCTURE_BLOCK_ENTITY =
            REGISTRY.register("better_structure_block_entity", () ->
                    BlockEntityType.Builder.of(BetterStructureBlockEntity::new,
                            TCRModBlocks.BETTER_STRUCTURE_BLOCK.get()).build(null));

}
