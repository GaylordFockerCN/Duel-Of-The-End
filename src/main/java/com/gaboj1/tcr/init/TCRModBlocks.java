package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.custom.*;
import com.gaboj1.tcr.worldgen.tree.DenseSpiritTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class TCRModBlocks {
    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TheCasketOfReveriesMod.MOD_ID);

    public static final RegistryObject<Block> BETTER_STRUCTURE_BLOCK = registerBlock("better_structure_block",
            ()-> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK)));

    public static final RegistryObject<Block> PORTAL_BED = registerBlock("portal_bed",
            () -> new PortalBed(DyeColor.WHITE,BlockBehaviour.Properties.copy(Blocks.BLACK_BED)));

    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_FLOWER = registerBlock("dense_forest_spirit_flower",
            ()-> new DenseForestSpiritFlower(() -> MobEffects.POISON, 5, BlockBehaviour.Properties.copy(Blocks.WITHER_ROSE)));
    public static final RegistryObject<Block> POTTED_DENSE_FOREST_SPIRIT_FLOWER = REGISTRY.register("potted_dense_forest_spirit_flower",
            ()-> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).noOcclusion()));


    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_LOG = registerBlock("dense_forest_spirit_tree_log",
            ()-> new DenseForestTreeBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LOG).strength(3f)));
        public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_WOOD = registerBlock("dense_forest_spirit_tree_wood",
            ()-> new DenseForestTreeBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_WOOD).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG = registerBlock("stripped_dense_forest_spirit_tree_log",
            ()-> new DenseForestTreeBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD = registerBlock("stripped_dense_forest_spirit_tree_wood",
            ()-> new DenseForestTreeBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD).strength(3f)));

    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_PLANKS = registerBlock("dense_forest_spirit_tree_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.JUNGLE_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_LEAVES = registerBlock("dense_forest_spirit_tree_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_SAPLING = registerBlock("dense_forest_spirit_sapling",
            () -> new SaplingBlock(new DenseSpiritTreeGrower(), BlockBehaviour.Properties.copy(Blocks.JUNGLE_SAPLING)));

    public static final RegistryObject<Block> PORTAL_BLOCK = REGISTRY.register("portal_block",
            () -> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f).noOcclusion()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return TCRModItems.REGISTRY.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

}
