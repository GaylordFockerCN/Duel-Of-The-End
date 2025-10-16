package com.p1nero.dote.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class DOTERecipeGenerator extends DOTERecipeProvider implements IConditionBuilder {
    public DOTERecipeGenerator(PackOutput output) {
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

    }

}
