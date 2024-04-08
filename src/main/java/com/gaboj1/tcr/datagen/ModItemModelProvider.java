package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        eggItem(TCRModItems.JELLY_CAT_SPAWN_EGG);
        eggItem(TCRModItems.SMALL_TREE_MONSTER_SPAWN_EGG);
        eggItem(TCRModItems.MIDDLE_TREE_MONSTER_SPAWN_EGG);
        eggItem(TCRModItems.TREE_GUARDIAN_SPAWN_EGG);
        eggItem(TCRModItems.PASTORAL_PLAIN_VILLAGER_SPAWN_EGG);
        eggItem(TCRModItems.PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG);

        simpleItem(TCRModItems.DREAMSCAPE_COIN);
        //handheldItem()会变很大个，适合用于武器什么的
        simpleItem(TCRModItems.BLUE_BANANA);
        simpleItem(TCRModItems.DREAM_TA);
        simpleItem(TCRModItems.BEER);
        simpleItem(TCRModItems.COOKIE);
        //说出来你可能不信，以下的小物品是训练chatGPT3.5仿写后修改的（大力解放生产力！）
        simpleItem(TCRModItems.EDEN_APPLE);
        simpleItem(TCRModItems.DRINK1);
        simpleItem(TCRModItems.DRINK2);
        simpleItem(TCRModItems.GOLDEN_WIND_AND_DEW);
        simpleItem(TCRModItems.GREEN_BANANA);
        simpleItem(TCRModItems.HOT_CHOCOLATE);
        simpleItem(TCRModItems.JUICE_TEA);
        simpleItem(TCRModItems.MAO_DAI);
        simpleItem(TCRModItems.PINE_CONE);
        simpleItem(TCRModItems.RED_WINE);
        simpleBlockItemBlockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER);
        saplingItem(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING);
    }

    private ItemModelBuilder eggItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(TheCasketOfReveriesMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"block/" + item.getId().getPath()));
    }
}
