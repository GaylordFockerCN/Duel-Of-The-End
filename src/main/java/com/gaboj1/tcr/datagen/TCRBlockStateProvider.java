package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRBlockStateProvider extends BlockStateProvider {
    public TCRBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TheCasketOfReveriesMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlockWithItem(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(), models().cross(blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get()).getPath(),
                blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get())).renderType("cutout"));
        simpleBlockWithItem(TCRBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(), models().singleTexture("potted_dense_forest_spirit_flower", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get())).renderType("cutout"));

        simpleBlockWithItem(TCRBlocks.CATNIP.get(), models().cross(blockTexture(TCRBlocks.CATNIP.get()).getPath(),
                blockTexture(TCRBlocks.CATNIP.get())).renderType("cutout"));
        simpleBlockWithItem(TCRBlocks.POTTED_CATNIP.get(), models().singleTexture("potted_catnip", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(TCRBlocks.CATNIP.get())).renderType("cutout"));

        simpleBlockWithItem(TCRBlocks.BLUE_MUSHROOM.get(), models().cross(blockTexture(TCRBlocks.BLUE_MUSHROOM.get()).getPath(),
                blockTexture(TCRBlocks.BLUE_MUSHROOM.get())).renderType("cutout"));
        simpleBlockWithItem(TCRBlocks.POTTED_BLUE_MUSHROOM.get(), models().singleTexture("potted_blue_mushroom", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(TCRBlocks.BLUE_MUSHROOM.get())).renderType("cutout"));

        logBlock(((RotatedPillarBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()));
        axisBlock(((RotatedPillarBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get()),blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()),blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()));

        axisBlock(((RotatedPillarBlock) TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()), blockTexture(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/dense_forest_spirit_tree_log_top"));
        axisBlock(((RotatedPillarBlock) TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get()), blockTexture(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                blockTexture(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()));

        blockItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG);
        blockItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD);
        blockItem(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG);
        blockItem(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD);
        blockWithItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS);
        blockWithItem(TCRBlocks.PORTAL_BLOCK);

        blockWithItem(TCRBlocks.YGGDRASIL_BLOCK);
        blockWithItem(TCRBlocks.TIGER_TRIAL_BLOCK);
        blockWithItem(TCRBlocks.MIAO_YIN_BLOCK);
        blockWithItem(TCRBlocks.ELITE_BIG_HAMMER_BLOCK);

        blockWithItem(TCRBlocks.ORICHALCUM_ORE);
        leavesBlock(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES);
        saplingBlock(TCRBlocks.DENSE_FOREST_SPIRIT_SAPLING);
        stairsBlock(((StairBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS.get()), blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get()));
        slabBlock(((SlabBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get()), blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get()), blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get()));
        fenceBlock(((FenceBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get()), blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get()), blockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get()));
        doorBlockWithRenderType(((DoorBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get()), modLoc("block/dense_forest_spirit_tree_door_bottom"), modLoc("block/dense_forest_spirit_tree_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) TCRBlocks.DENSE_FOREST_SPIRIT_TREE_TRAPDOOR.get()), modLoc("block/dense_forest_spirit_tree_trapdoor"), true, "cutout");

        blockWithItem(TCRBlocks.BOSS2_ROOM_1);
        blockWithItem(TCRBlocks.BOSS2_ROOM_2);
        blockWithItem(TCRBlocks.BOSS2_ROOM_3);
        blockWithItem(TCRBlocks.BOSS2_ROOM_4);
        blockWithItem(TCRBlocks.BOSS2_ROOM_5);
        blockWithItem(TCRBlocks.BOSS2_ROOM_6);
        blockWithItem(TCRBlocks.BOSS2_ROOM_7);
        blockWithItem(TCRBlocks.BOSS2_ROOM_8);
        blockWithItem(TCRBlocks.BOSS2_ROOM_9);
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_1_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_1.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_1_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_1_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_1.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_2_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_2.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_2_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_2_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_2.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_3_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_3.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_3_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_3_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_3.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_4_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_4.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_4_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_4_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_4.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_5_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_5.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_5_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_5_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_5.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_6_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_6.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_6_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_6_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_6.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_7_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_7.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_7_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_7_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_7.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_8_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_8.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_8_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_8_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_8.get()));
        stairsBlock(((StairBlock) TCRBlocks.BOSS2_ROOM_9_STAIRS.get()), blockTexture(TCRBlocks.BOSS2_ROOM_9.get()));
        slabBlock(((SlabBlock) TCRBlocks.BOSS2_ROOM_9_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_9_SLAB.get()), blockTexture(TCRBlocks.BOSS2_ROOM_9.get()));



    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(TheCasketOfReveriesMod.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
