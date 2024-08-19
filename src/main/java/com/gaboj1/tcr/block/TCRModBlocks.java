package com.gaboj1.tcr.block;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.custom.*;
import com.gaboj1.tcr.block.custom.spawner.YggdrasilSpawnerBlock;
import com.gaboj1.tcr.item.TCRModItems;
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
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
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

    public static final RegistryObject<Block> CATNIP = registerBlock("catnip_block",
            ()-> new FlowerBlock(() -> MobEffects.SLOW_FALLING, 0, BlockBehaviour.Properties.copy(Blocks.LILY_OF_THE_VALLEY)));
    public static final RegistryObject<Block> POTTED_CATNIP = REGISTRY.register("potted_catnip",
            ()-> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), TCRModBlocks.CATNIP,
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

    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_STAIRS = registerBlock("dense_forest_spirit_tree_stairs",
            ()->new StairBlock(()-> TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_SLAB = registerBlock("dense_forest_spirit_tree_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));

    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_FENCE = registerBlock("dense_forest_spirit_tree_fence",
            ()->new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_FENCE_GATE = registerBlock("dense_forest_spirit_tree_fence_gate",
            ()->new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_DOOR = registerBlock("dense_forest_spirit_tree_door",
            ()->new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_TREE_TRAPDOOR = registerBlock("dense_forest_spirit_tree_trapdoor",
            ()->new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));

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

    //神金矿
    public static final RegistryObject<Block> ORICHALCUM_ORE = registerBlock("orichalcum_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));

    //注意：拿在手上是没有完整模型的，这里偷懒了，本来应该新建一个物品类的。不过反正生存模式用不到，开发者才用得到，就懒得做了hh
    public static final RegistryObject<Block> PORTAL_BLOCK = registerBlock("portal_block",
            () -> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> YGGDRASIL_BLOCK = registerBlock("yggdrasil_block",
            () -> new YggdrasilSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return TCRModItems.REGISTRY.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

}
