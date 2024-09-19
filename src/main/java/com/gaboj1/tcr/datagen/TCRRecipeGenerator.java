package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class TCRRecipeGenerator extends TCRRecipeProvider implements IConditionBuilder {
    public TCRRecipeGenerator(PackOutput output) {
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TCRModBlocks.PORTAL_BED.get())
                .define('B', Items.BLACK_BED)
                .define('C', Items.WITHER_ROSE)
                .unlockedBy(getHasName(Items.WITHER_ROSE), has(Items.WITHER_ROSE))
                .pattern(" C ")
                .pattern(" B ")
                .pattern("   ")
                .save(consumer);

        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.BASIC_RESIN.get(), RecipeCategory.MISC, TCRItems.INTERMEDIATE_RESIN.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.INTERMEDIATE_RESIN.get(), RecipeCategory.MISC, TCRItems.ADVANCED_RESIN.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.ADVANCED_RESIN.get(), RecipeCategory.MISC, TCRItems.SUPER_RESIN.get());

        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.DREAMSCAPE_COIN.get(), RecipeCategory.MISC, TCRItems.DREAMSCAPE_COIN_PLUS.get());

        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get(), TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get(), TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get(), TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get(), TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());

        this.oreSmelting(TCRItems.ORICHALCUM.get(), TCRItems.RAW_ORICHALCUM.get(), 0.7f, 200, consumer);

    }

}
