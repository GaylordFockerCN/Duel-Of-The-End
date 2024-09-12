package com.gaboj1.tcr.block;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.BetterStructureBlockEntity;
import com.gaboj1.tcr.block.entity.PortalBedEntity;
import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.TigerTrialSpawnerBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.YggdrasilSpawnerBlockEntity;
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

    public static final RegistryObject<BlockEntityType<PortalBedEntity>> PORTAL_BED =
            REGISTRY.register("portal_bed_entity", () ->
                    BlockEntityType.Builder.of(PortalBedEntity::new,
                            TCRModBlocks.PORTAL_BED.get()).build(null));

    public static final RegistryObject<BlockEntityType<PortalBlockEntity>> PORTAL_BLOCK_ENTITY =
            REGISTRY.register("portal_block_entity", () ->
                    BlockEntityType.Builder.of(PortalBlockEntity::new,
                            TCRModBlocks.PORTAL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<YggdrasilSpawnerBlockEntity>> YGGDRASIL_SPAWNER_BLOCK_ENTITY =
            REGISTRY.register("yggdrasil_spawner_block_entity", () ->
                    BlockEntityType.Builder.of(YggdrasilSpawnerBlockEntity::new,
                            TCRModBlocks.YGGDRASIL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TigerTrialSpawnerBlockEntity>> TIGER_TRIAL_SPAWNER_BLOCK_ENTITY =
            REGISTRY.register("tiger_trial_spawner_block_entity", () ->
                    BlockEntityType.Builder.of(TigerTrialSpawnerBlockEntity::new,
                            TCRModBlocks.TIGER_TRIAL_BLOCK.get()).build(null));


}
