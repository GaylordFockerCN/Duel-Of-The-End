package com.p1nero.dote.block;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.custom.*;
import com.p1nero.dote.block.custom.spawner.*;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DOTEBlocks {
    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<Block> BETTER_STRUCTURE_BLOCK = registerBlock("better_structure_block",
            ()-> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> SENBAI_SPAWNER = registerBlock("senbai_spawner",
            ()-> new SenbaiSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    public static final RegistryObject<Block> GOLDEN_FLAME_SPAWNER = registerBlock("golden_flame_spawner",
            ()-> new GoldenFlameSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    public static final RegistryObject<Block> TAR_SPAWNER = registerBlock("tar_spawner",
            ()-> new TARSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> TPP_SPAWNER = registerBlock("tpp_spawner",
            ()-> new TPPSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> TSE_SPAWNER = registerBlock("tse_spawner",
            ()-> new TSESpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return DOTEItems.REGISTRY.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

}
