package com.gaboj1.tcr.block;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.custom.*;
import com.gaboj1.tcr.block.custom.spawner.SenbaiSpawnerBlock;
import com.gaboj1.tcr.item.DOTEItems;
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
            ()-> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK)));

    public static final RegistryObject<Block> SENBAI_SPAWNER = registerBlock("senbai_spawner",
            ()-> new SenbaiSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return DOTEItems.REGISTRY.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

}
