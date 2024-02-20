package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_)
                    .unlockedBy(getHasName(itemlike), has(itemlike)).save(p_250791_, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, getItemName(p_250066_)) + p_249236_ + "_" + getItemName(itemlike));
        }
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike, RecipeCategory category1, ItemLike itemLike1) {
        nineBlockStorageRecipes(consumer, category, itemLike, category1, itemLike1, getSimpleRecipeName(itemLike1), (String)null, getSimpleRecipeName(itemLike), (String)null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike,
                                                  RecipeCategory category1, ItemLike itemLike1, String p_250475_, @Nullable String p_248641_,
                                                  String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(category, itemLike, 9).requires(itemLike1).group(p_250414_).unlockedBy(getHasName(itemLike1), has(itemLike1))
                .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(category1, itemLike1).define('#', itemLike).pattern("###").pattern("###").pattern("###").group(p_248641_)
                .unlockedBy(getHasName(itemLike), has(itemLike)).save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, p_250475_));
    }

    protected static void woodBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike, ItemLike itemLike1) {
        woodBlockStorageRecipes(consumer, category, itemLike, itemLike1, getSimpleRecipeName(itemLike), (String)null);
    }

    protected static void woodBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike,
                                                   ItemLike itemLike1,String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(category, itemLike, 4).requires(itemLike1).group(p_250414_).unlockedBy(getHasName(itemLike1), has(itemLike1))
                .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, p_252237_));
    }

}
