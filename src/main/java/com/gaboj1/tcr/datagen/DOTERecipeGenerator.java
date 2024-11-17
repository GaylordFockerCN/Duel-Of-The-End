package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.item.DOTEItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class DOTERecipeGenerator extends DOTERecipeProvider implements IConditionBuilder {
    public DOTERecipeGenerator(PackOutput output) {
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.M_KEY.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.ROTTEN_FLESH)
                .define('B', Items.ENDER_PEARL)
                .unlockedBy(getHasName(Items.ROTTEN_FLESH), has(Items.ROTTEN_FLESH))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.P_KEY.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', DOTEItems.ADGRAIN.get())
                .define('B', DOTEItems.NETHERITESS.get())
                .unlockedBy(getHasName(DOTEItems.ADGRAIN.get()), has(DOTEItems.ADGRAIN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.IMMORTALESSENCE.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.ROTTEN_FLESH)
                .define('B', DOTEItems.ADGRAIN.get())
                .unlockedBy(getHasName(DOTEItems.ADGRAIN.get()), has(DOTEItems.ADGRAIN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.NETHERROT_INGOT.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.COPPER_BLOCK)
                .define('B', DOTEItems.NETHERITESS.get())
                .unlockedBy(getHasName(DOTEItems.NETHERITESS.get()), has(DOTEItems.NETHERITESS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_SCRAP)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', DOTEItems.IMMORTALESSENCE.get())
                .define('B', DOTEItems.NETHERITESS.get())
                .unlockedBy(getHasName(DOTEItems.NETHERITESS.get()), has(DOTEItems.NETHERITESS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DOTEItems.TIESTONEH.get())
                .pattern("   ")
                .pattern("ABA")
                .pattern("B B")
                .define('A', Items.LEATHER)
                .define('B', Items.COBBLESTONE)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DOTEItems.TIESTONEC.get())
                .pattern("B B")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.LEATHER)
                .define('B', Items.COBBLESTONE)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DOTEItems.TIESTONEL.get())
                .pattern("BCB")
                .pattern("A A")
                .pattern("B B")
                .define('A', Items.LEATHER)
                .define('B', Items.COBBLESTONE)
                .define('C', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DOTEItems.TIESTONES.get())
                .pattern("   ")
                .pattern("A A")
                .pattern("B B")
                .define('A', Items.LEATHER)
                .define('B', Items.COBBLESTONE)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(consumer);
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_HELMET, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_HELMET.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_CHESTPLATE, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_CHESTPLATE.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_LEGGINGS, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_LEGGINGS.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_BOOTS, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_BOOTS.get());
    }

}
