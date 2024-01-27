package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TheCasketOfReveriesMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        logBlock(((RotatedPillarBlock) TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()));
        axisBlock(((RotatedPillarBlock) TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get()),blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()),blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()));

        axisBlock(((RotatedPillarBlock) TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()), blockTexture(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/stripped_dense_forest_spirit_tree_log_top"));
        axisBlock(((RotatedPillarBlock) TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get()), blockTexture(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                blockTexture(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()));

        blockItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG);
        blockItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD);
        blockItem(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG);
        blockItem(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD);
        blockWithItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS);
        leavesBlock(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES);

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

//    public void makeStrawberryCrop(CropBlock block, String modelName, String textureName) {
//        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);
//
//        getVariantBuilder(block).forAllStates(function);
//    }

//    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
//        ConfiguredModel[] models = new ConfiguredModel[1];
//        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
//                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));
//
//        return models;
//    }

//    public void makeCornCrop(CropBlock block, String modelName, String textureName) {
//        Function<BlockState, ConfiguredModel[]> function = state -> cornStates(state, block, modelName, textureName);
//
//        getVariantBuilder(block).forAllStates(function);
//    }
//
//    private ConfiguredModel[] cornStates(BlockState state, CropBlock block, String modelName, String textureName) {
//        ConfiguredModel[] models = new ConfiguredModel[1];
//        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CornCropBlock) block).getAgeProperty()),
//                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + textureName + state.getValue(((CornCropBlock) block).getAgeProperty()))).renderType("cutout"));
//
//        return models;
//    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
