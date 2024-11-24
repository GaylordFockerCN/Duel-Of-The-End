package com.p1nero.dote.datagen;

import com.p1nero.dote.item.DOTEItems;
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DOTEItems.STAR_CORE.get(), 1)
                .requires(DOTEItems.BOOK_OF_ENDING.get())
                .unlockedBy(getHasName(DOTEItems.BOOK_OF_ENDING.get()), has(DOTEItems.BOOK_OF_ENDING.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DOTEItems.ADGRAIN.get(), 4)
                .requires(DOTEItems.ADVENTURESPAR.get())
                .unlockedBy(getHasName(DOTEItems.ADVENTURESPAR.get()), has(DOTEItems.ADVENTURESPAR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.ADVENTURESPAR.get())
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', DOTEItems.ADGRAIN.get())
                .unlockedBy(getHasName(DOTEItems.ADGRAIN.get()), has(DOTEItems.ADGRAIN.get()))
                .save(consumer);
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
                .define('A', DOTEItems.ADVENTURESPAR.get())
                .define('B', DOTEItems.HOLY_RADIANCE_SEED.get())
                .unlockedBy(getHasName(DOTEItems.HOLY_RADIANCE_SEED.get()), has(DOTEItems.HOLY_RADIANCE_SEED.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.U_KEY.get())
                .pattern(" A ")
                .pattern("B C")
                .pattern(" D ")
                .define('A', DOTEItems.HOLY_RADIANCE_SEED.get())
                .define('B', DOTEItems.NETHERITESS.get())
                .define('C', DOTEItems.WITHERC.get())
                .define('D', DOTEItems.CORE_OF_HELL.get())
                .unlockedBy(getHasName(DOTEItems.CORE_OF_HELL.get()), has(DOTEItems.CORE_OF_HELL.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.IMMORTALESSENCE.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.ROTTEN_FLESH)
                .define('B', DOTEItems.ADGRAIN.get())
                .unlockedBy(getHasName(DOTEItems.ADGRAIN.get()), has(DOTEItems.ADGRAIN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.WKNIGHT_INGOT.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.IRON_BLOCK)
                .define('B', DOTEItems.HOLY_RADIANCE_SEED.get())
                .unlockedBy(getHasName(DOTEItems.HOLY_RADIANCE_SEED.get()), has(DOTEItems.HOLY_RADIANCE_SEED.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.NETHERROT_INGOT.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.COPPER_BLOCK)
                .define('B', DOTEItems.CORE_OF_HELL.get())
                .unlockedBy(getHasName(DOTEItems.CORE_OF_HELL.get()), has(DOTEItems.CORE_OF_HELL.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DOTEItems.DRAGONSTEEL_INGOT.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.OBSIDIAN)
                .define('B', DOTEItems.BOOK_OF_ENDING.get())
                .unlockedBy(getHasName(DOTEItems.BOOK_OF_ENDING.get()), has(DOTEItems.BOOK_OF_ENDING.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_SCRAP)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', Items.DIAMOND)
                .define('B', DOTEItems.ADGRAIN.get())
                .define('C', Items.GOLD_INGOT)
                .unlockedBy(getHasName(DOTEItems.ADGRAIN.get()), has(DOTEItems.ADGRAIN.get()))
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
        smithing(consumer, DOTEItems.WKNIGHT_INGOT.get(), Items.DIAMOND_HELMET, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.WKNIGHT_HELMET.get());
        smithing(consumer, DOTEItems.WKNIGHT_INGOT.get(), Items.DIAMOND_CHESTPLATE, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.WKNIGHT_CHESTPLATE.get());
        smithing(consumer, DOTEItems.WKNIGHT_INGOT.get(), Items.DIAMOND_LEGGINGS, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.WKNIGHT_LEGGINGS.get());
        smithing(consumer, DOTEItems.WKNIGHT_INGOT.get(), Items.DIAMOND_BOOTS, Items.NETHERITE_SCRAP, RecipeCategory.COMBAT, DOTEItems.WKNIGHT_BOOTS.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_HELMET, Items.NETHERITE_INGOT, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_HELMET.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_CHESTPLATE, Items.NETHERITE_INGOT, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_CHESTPLATE.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_LEGGINGS, Items.NETHERITE_INGOT, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_LEGGINGS.get());
        smithing(consumer, DOTEItems.NETHERROT_INGOT.get(), Items.DIAMOND_BOOTS, Items.NETHERITE_INGOT, RecipeCategory.COMBAT, DOTEItems.NETHERITEROT_BOOTS.get());
        smithing(consumer, DOTEItems.DRAGONSTEEL_INGOT.get(), Items.NETHERITE_HELMET, Items.GOLD_BLOCK, RecipeCategory.COMBAT, DOTEItems.GOLDEN_DRAGON_HELMET.get());
        smithing(consumer, DOTEItems.DRAGONSTEEL_INGOT.get(), Items.NETHERITE_CHESTPLATE, Items.GOLD_BLOCK, RecipeCategory.COMBAT, DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get());
        smithing(consumer, DOTEItems.DRAGONSTEEL_INGOT.get(), Items.NETHERITE_LEGGINGS, Items.GOLD_BLOCK, RecipeCategory.COMBAT, DOTEItems.GOLDEN_DRAGON_LEGGINGS.get());
        smithing(consumer, DOTEItems.DRAGONSTEEL_INGOT.get(), Items.NETHERITE_BOOTS, Items.GOLD_BLOCK, RecipeCategory.COMBAT, DOTEItems.GOLDEN_DRAGON_BOOTS.get());
    }

}
