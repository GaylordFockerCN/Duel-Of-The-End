package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class TCRRecipeGenerator extends TCRRecipeProvider implements IConditionBuilder {
    public TCRRecipeGenerator(PackOutput output) {
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.TREE_HELMET.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("   ")
                .define('A', TCRItems.HEART_OF_THE_SAPLING.get())
                .unlockedBy(getHasName(TCRItems.HEART_OF_THE_SAPLING.get()), has(TCRItems.HEART_OF_THE_SAPLING.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.TREE_CHESTPLATE.get())
                .pattern("A A")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get())
                .define('B', TCRItems.BARK_OF_THE_GUARDIAN.get())
                .unlockedBy(getHasName(TCRItems.BARK_OF_THE_GUARDIAN.get()), has(TCRItems.BARK_OF_THE_GUARDIAN.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.TREE_LEGGINGS.get())
                .pattern("ABA")
                .pattern("A A")
                .pattern("A A")
                .define('A', TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get())
                .define('B', TCRItems.BARK_OF_THE_GUARDIAN.get())
                .unlockedBy(getHasName(TCRItems.BARK_OF_THE_GUARDIAN.get()), has(TCRItems.BARK_OF_THE_GUARDIAN.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.TREE_BOOTS.get())
                .pattern("A A")
                .pattern("A A")
                .pattern("   ")
                .define('A', TCRItems.HEART_OF_THE_SAPLING.get())
                .unlockedBy(getHasName(TCRItems.HEART_OF_THE_SAPLING.get()), has(TCRItems.HEART_OF_THE_SAPLING.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.ORICHALCUM_SWORD.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', TCRItems.ORICHALCUM.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(TCRItems.ORICHALCUM.get()), has(TCRItems.ORICHALCUM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.ORICHALCUM_AXE.get())
                .pattern("AA ")
                .pattern("AB ")
                .pattern(" B ")
                .define('A', TCRItems.ORICHALCUM.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(TCRItems.ORICHALCUM.get()), has(TCRItems.ORICHALCUM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TCRItems.ORICHALCUM_PICKAXE.get())
                .pattern("AAA")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', TCRItems.ORICHALCUM.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(TCRItems.ORICHALCUM.get()), has(TCRItems.ORICHALCUM.get()))
                .save(consumer);

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

        oreSmelting(TCRItems.ORICHALCUM.get(), TCRItems.RAW_ORICHALCUM.get(), 0.7f, 200, consumer);
        smithing(consumer, TCRItems.GOD_ORICHALCUM.get(), TCRItems.ORICHALCUM_CROSSBOW.get(), Items.AIR, RecipeCategory.COMBAT, TCRItems.GOD_ORICHALCUM_CROSSBOW.get());
    }

}
