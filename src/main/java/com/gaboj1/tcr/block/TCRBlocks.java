package com.gaboj1.tcr.block;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.custom.*;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class TCRBlocks {
    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<Block> BETTER_STRUCTURE_BLOCK = registerBlock("better_structure_block",
            ()-> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK)));

    public static final RegistryObject<Block> PORTAL_BED = registerBlock("portal_bed",
            () -> new PortalBed(DyeColor.WHITE,BlockBehaviour.Properties.copy(Blocks.BLACK_BED)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return TCRItems.REGISTRY.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

}
