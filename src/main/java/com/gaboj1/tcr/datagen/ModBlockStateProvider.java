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

//        simpleBlockWithItem(TCRModBlocks.PORTAL_BED.get(), models().singleTexture((blockTexture(TCRModBlocks.PORTAL_BED.get()).getPath(),
//                blockTexture(TCRModBlocks.PORTAL_BED.get())).renderType("cutout"));


        simpleBlockWithItem(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(), models().cross(blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get()).getPath(),
                blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get())).renderType("cutout"));
        simpleBlockWithItem(TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(), models().singleTexture("potted_dense_forest_spirit_flower", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get())).renderType("cutout"));

        logBlock(((RotatedPillarBlock) TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()));
        axisBlock(((RotatedPillarBlock) TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get()),blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()),blockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get()));

        axisBlock(((RotatedPillarBlock) TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()), blockTexture(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/dense_forest_spirit_tree_log_top"));
        axisBlock(((RotatedPillarBlock) TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get()), blockTexture(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()),
                blockTexture(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get()));

        blockItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG);
        blockItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD);
        blockItem(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG);
        blockItem(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD);
        blockWithItem(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS);
        leavesBlock(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES);
        saplingBlock(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING);

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
