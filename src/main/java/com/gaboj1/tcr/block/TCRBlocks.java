package com.gaboj1.tcr.block;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.custom.*;
import com.gaboj1.tcr.block.custom.spawner.BigHammerSpawnerBlock;
import com.gaboj1.tcr.block.custom.spawner.MiaoYinSpawnerBlock;
import com.gaboj1.tcr.block.custom.spawner.TigerTrialSpawnerBlock;
import com.gaboj1.tcr.block.custom.spawner.YggdrasilSpawnerBlock;
import com.gaboj1.tcr.item.TCRItems;
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


public class TCRBlocks {
    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TheCasketOfReveriesMod.MOD_ID);

    public static final RegistryObject<Block> BETTER_STRUCTURE_BLOCK = registerBlock("better_structure_block",
            ()-> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK)));

    public static final RegistryObject<Block> PORTAL_BED = registerBlock("portal_bed",
            () -> new PortalBed(DyeColor.WHITE,BlockBehaviour.Properties.copy(Blocks.BLACK_BED)));

    public static final RegistryObject<Block> DENSE_FOREST_SPIRIT_FLOWER = registerBlock("dense_forest_spirit_flower",
            ()-> new DenseForestSpiritFlower(() -> MobEffects.POISON, 5, BlockBehaviour.Properties.copy(Blocks.WITHER_ROSE)));
    public static final RegistryObject<Block> POTTED_DENSE_FOREST_SPIRIT_FLOWER = REGISTRY.register("potted_dense_forest_spirit_flower",
            ()-> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).noOcclusion()));

    public static final RegistryObject<Block> CATNIP = registerBlock("catnip_block",
            ()-> new FlowerBlock(() -> MobEffects.SLOW_FALLING, 0, BlockBehaviour.Properties.copy(Blocks.LILY_OF_THE_VALLEY)));
    public static final RegistryObject<Block> POTTED_CATNIP = REGISTRY.register("potted_catnip",
            ()-> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), TCRBlocks.CATNIP,
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
            ()->new StairBlock(()-> TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
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
    public static final RegistryObject<Block>  DENSE_FOREST_SPIRIT_VINE = registerBlock("dense_forest_spirit_vine",
            () -> new VineBlock(BlockBehaviour.Properties.copy(Blocks.VINE)));
    public static final RegistryObject<Block> BOSS2_ROOM_1 = registerBlock("boss2_room_1",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_2 = registerBlock("boss2_room_2",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_3 = registerBlock("boss2_room_3",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_4 = registerBlock("boss2_room_4",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_5 = registerBlock("boss2_room_5",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_6 = registerBlock("boss2_room_6",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_7 = registerBlock("boss2_room_7",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_8 = registerBlock("boss2_room_8",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_9 = registerBlock("boss2_room_9",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_1_STAIRS = registerBlock("boss2_room_1_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_1.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_1_SLAB = registerBlock("boss2_room_1_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_2_STAIRS = registerBlock("boss2_room_2_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_2.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_2_SLAB = registerBlock("boss2_room_2_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_3_STAIRS = registerBlock("boss2_room_3_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_3.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_3_SLAB = registerBlock("boss2_room_3_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_4_STAIRS = registerBlock("boss2_room_4_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_4.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_4_SLAB = registerBlock("boss2_room_4_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_5_STAIRS = registerBlock("boss2_room_5_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_5.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_5_SLAB = registerBlock("boss2_room_5_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_6_STAIRS = registerBlock("boss2_room_6_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_6.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_6_SLAB = registerBlock("boss2_room_6_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_7_STAIRS = registerBlock("boss2_room_7_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_7.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_7_SLAB = registerBlock("boss2_room_7_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_8_STAIRS = registerBlock("boss2_room_8_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_8.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_8_SLAB = registerBlock("boss2_room_8_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Block> BOSS2_ROOM_9_STAIRS = registerBlock("boss2_room_9_stairs",
            ()->new StairBlock(()-> TCRBlocks.BOSS2_ROOM_9.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> BOSS2_ROOM_9_SLAB = registerBlock("boss2_room_9_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).strength(-1.0F, 3600000.0F).noLootTable()));


    //神金矿
    public static final RegistryObject<Block> ORICHALCUM_ORE = registerBlock("orichalcum_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));

    //注意：拿在手上是没有完整模型的，这里偷懒了，本来应该新建一个物品类的。不过反正生存模式用不到，开发者才用得到，就懒得做了hh
    public static final RegistryObject<Block> PORTAL_BLOCK = registerBlock("portal_block",
            () -> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> YGGDRASIL_BLOCK = registerBlock("yggdrasil_block",
            () -> new YggdrasilSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> TIGER_TRIAL_BLOCK = registerBlock("tiger_trial_block",
            () -> new TigerTrialSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> MIAO_YIN_BLOCK = registerBlock("miao_yin_block",
            () -> new MiaoYinSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> ELITE_BIG_HAMMER_BLOCK = registerBlock("elite_big_hammer_block",
            () -> new BigHammerSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000.0F).noOcclusion().noLootTable()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return TCRItems.REGISTRY.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

}
