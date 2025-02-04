package com.p1nero.dote.datagen;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.DOTEBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DOTEBlockStateProvider extends BlockStateProvider {
    public DOTEBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DuelOfTheEndMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        leavesBlock(DOTEBlocks.BETTER_STRUCTURE_BLOCK);
        simpleBlockWithItem(DOTEBlocks.SENBAI_SPAWNER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/senbai_spawner")));
        simpleBlockWithItem(DOTEBlocks.GOLDEN_FLAME_SPAWNER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/golden_flame_spawner")));
        simpleBlockWithItem(DOTEBlocks.TAR_SPAWNER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/senbai_spawner")));
        simpleBlockWithItem(DOTEBlocks.TPP_SPAWNER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/senbai_spawner")));
        simpleBlockWithItem(DOTEBlocks.TSE_SPAWNER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/senbai_spawner")));
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
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(DuelOfTheEndMod.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
