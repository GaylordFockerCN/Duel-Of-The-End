package com.p1nero.dote.block;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.custom.BetterStructureBlock;
import com.p1nero.dote.block.custom.spawner.GoldenFlameSpawnerBlock;
import com.p1nero.dote.block.custom.spawner.MsAbyssSpawnerBlock;
import com.p1nero.dote.block.custom.spawner.SandCaptainSpawnerBlock;
import com.p1nero.dote.block.custom.spawner.SenbaiSpawnerBlock;
import com.p1nero.dote.block.entity.spawner.MsAbyssSpawnerBlockEntity;
import com.p1nero.dote.block.renderer.MsAbyssSpawnerBlockRenderer;
import com.p1nero.dote.block.renderer.SandCaptainSpawnerBlockRenderer;
import com.p1nero.dote.block.entity.spawner.SandCaptainSpawnerBlockEntity;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.item.DOTEItems;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DOTEBlocks {
    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<Block> BETTER_STRUCTURE_BLOCK = registerBlock("better_structure_block",
            () -> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> M_BLOCK = registerBlock("m_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> P_BLOCK = registerBlock("p_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> U_BLOCK = registerBlock("u_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> SENBAI_SPAWNER = registerBlock("senbai_spawner",
            () -> new SenbaiSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    public static final RegistryObject<Block> GOLDEN_FLAME_SPAWNER = registerBlock("golden_flame_spawner",
            () -> new GoldenFlameSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));


    public static final BlockEntry<SandCaptainSpawnerBlock> SAND_CAPTAIN_SPAWNER = DuelOfTheEndMod.REGISTRATE.block("sand_captain_spawner", p -> new SandCaptainSpawnerBlock(
                BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()))
            .blockstate((ctx, pvd) ->
                    pvd.simpleBlock(ctx.get()))
            .defaultLoot()
            .simpleItem()
            .register();


    public static final BlockEntityEntry<SandCaptainSpawnerBlockEntity> SAND_CAPTAIN_SPAWNER_ENTITY = DuelOfTheEndMod.REGISTRATE.<SandCaptainSpawnerBlockEntity>blockEntity("sand_captain_spawner_entity",
                    ((blockEntityType, blockPos, blockState) -> new SandCaptainSpawnerBlockEntity(blockEntityType, DOTEEntities.SAND_CAPTAIN.get(), blockPos, blockState)))
            .validBlock(SAND_CAPTAIN_SPAWNER).renderer(() -> SandCaptainSpawnerBlockRenderer::new).register();


    public static final BlockEntry<MsAbyssSpawnerBlock> MS_ABYSS_SPAWNER = DuelOfTheEndMod.REGISTRATE.block("ms_abyss_spawner", p -> new MsAbyssSpawnerBlock(
                    BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()))
            .blockstate((ctx, pvd) ->
                    pvd.simpleBlock(ctx.get()))
            .defaultLoot()
            .simpleItem()
            .register();


    public static final BlockEntityEntry<MsAbyssSpawnerBlockEntity> MS_ABYSS_SPAWNER_ENTITY = DuelOfTheEndMod.REGISTRATE.<MsAbyssSpawnerBlockEntity>blockEntity("ms_abyss_spawner_entity",
                    ((blockEntityType, blockPos, blockState) -> new MsAbyssSpawnerBlockEntity(blockEntityType, DOTEEntities.SAND_CAPTAIN.get(), blockPos, blockState)))
            .validBlock(MS_ABYSS_SPAWNER).renderer(() -> MsAbyssSpawnerBlockRenderer::new).register();

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return DOTEItems.REGISTRY.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register() {
    }

}
