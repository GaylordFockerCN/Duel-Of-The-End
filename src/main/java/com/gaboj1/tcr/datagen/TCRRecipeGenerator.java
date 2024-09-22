package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class TCRRecipeGenerator extends TCRRecipeProvider implements IConditionBuilder {
    public TCRRecipeGenerator(PackOutput output) {
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCRBlocks.PORTAL_BED.get())
                .requires(ItemTags.BEDS)
                .requires(Items.WITHER_ROSE)
                .unlockedBy(getHasName(Items.WITHER_ROSE), has(Items.WITHER_ROSE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCRItems.TIGER_KARAMBIT.get())
                .requires(TCRItems.ICE_TIGER_CLAW.get())
                .requires(TCRItems.TIGER_SOUL_ICE.get())
                .requires(Blocks.OBSIDIAN)
                .unlockedBy(getHasName(TCRItems.TIGER_SOUL_ICE.get()), has(TCRItems.TIGER_SOUL_ICE.get()))
                .save(consumer);

        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.BASIC_RESIN.get(), RecipeCategory.MISC, TCRItems.INTERMEDIATE_RESIN.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.INTERMEDIATE_RESIN.get(), RecipeCategory.MISC, TCRItems.ADVANCED_RESIN.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.ADVANCED_RESIN.get(), RecipeCategory.MISC, TCRItems.SUPER_RESIN.get());

        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, TCRItems.DREAMSCAPE_COIN.get(), RecipeCategory.MISC, TCRItems.DREAMSCAPE_COIN_PLUS.get());

        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get(), TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get(), TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get(), TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
        woodBlockStorageRecipes(consumer, RecipeCategory.DECORATIONS, TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get(), TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());

        this.oreSmelting(TCRItems.ORICHALCUM.get(), TCRItems.RAW_ORICHALCUM.get(), 0.7f, 200, consumer);

    }

}
