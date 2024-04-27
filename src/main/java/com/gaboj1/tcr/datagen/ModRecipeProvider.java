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

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike, RecipeCategory category1, ItemLike itemLike1) {
        nineBlockStorageRecipes(consumer, category, itemLike, category1, itemLike1, getSimpleRecipeName(itemLike1), (String)null, getSimpleRecipeName(itemLike), (String)null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike,
                                                  RecipeCategory category1, ItemLike itemLike1, String p_250475_, @Nullable String p_248641_,
                                                  String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(category, itemLike, 9).requires(itemLike1).group(p_250414_).unlockedBy(getHasName(itemLike1), has(itemLike1))
                .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, p_252237_+"shapeless"));//加后缀防止重名
        ShapedRecipeBuilder.shaped(category1, itemLike1).define('#', itemLike).pattern("###").pattern("###").pattern("###").group(p_248641_)
                .unlockedBy(getHasName(itemLike), has(itemLike)).save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, p_250475_+"shaped"));
    }

    protected static void woodBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike, ItemLike itemLike1) {
        woodBlockStorageRecipes(consumer, category, itemLike, itemLike1, getSimpleRecipeName(itemLike), (String)null);
    }

    protected static void woodBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike itemLike,
                                                   ItemLike itemLike1,String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(category, itemLike, 4).requires(itemLike1).group(p_250414_).unlockedBy(getHasName(itemLike1), has(itemLike1))
                .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, p_252237_));
    }

    protected void oreSmelting(ItemLike result, ItemLike ingredient, float experience, int cookingTime, Consumer<FinishedRecipe> consume) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookingTime).unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consume, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, getItemName(result)) + "_from_smelting" + "_" + getItemName(ingredient));
    }

}
