package com.gaboj1.tcr.block;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.entity.BetterStructureBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.GoldenFlameSpawnerBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.SenbaiSpawnerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DOTEBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<BetterStructureBlockEntity>> BETTER_STRUCTURE_BLOCK_ENTITY =
            REGISTRY.register("better_structure_block_entity", () ->
                    BlockEntityType.Builder.of(BetterStructureBlockEntity::new,
                            DOTEBlocks.BETTER_STRUCTURE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SenbaiSpawnerBlockEntity>> SENBAI_SPAWNER_BLOCK_ENTITY =
            REGISTRY.register("senbai_spawner_block_entity", () ->
                    BlockEntityType.Builder.of(SenbaiSpawnerBlockEntity::new,
                            DOTEBlocks.SENBAI_SPAWNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<GoldenFlameSpawnerBlockEntity>> GOLDEN_FLAME_SPAWNER_BLOCK_ENTITY =
            REGISTRY.register("golden_flame_spawner_block_entity", () ->
                    BlockEntityType.Builder.of(GoldenFlameSpawnerBlockEntity::new,
                            DOTEBlocks.GOLDEN_FLAME_SPAWNER.get()).build(null));

}
