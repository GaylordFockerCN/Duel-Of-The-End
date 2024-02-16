package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.loot.TCRLoot;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementData extends ForgeAdvancementProvider {
    public ModAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new AetherAdvancements()));
    }

    public static class AetherAdvancements implements AdvancementGenerator {

        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            Advancement theCasketOfReveries = Advancement.Builder.advancement()
                    .display(TCRModBlocks.PORTAL_BED.get(),
                            Component.translatable("advancement.tcr."+TheCasketOfReveriesMod.MOD_ID),
                            Component.translatable("advancement.tcr."+TheCasketOfReveriesMod.MOD_ID+".desc"),
                            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/block/dense_forest_dirt.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion(TheCasketOfReveriesMod.MOD_ID, ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.SKY_ISLAND_LEVEL_KEY))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, TheCasketOfReveriesMod.MOD_ID), existingFileHelper);

            Advancement enterRealmOfTheDream = Advancement.Builder.advancement()
                    .parent(theCasketOfReveries)
                    .display(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(),
                            Component.translatable("advancement.tcr.enter_realm_of_the_dream"),
                            Component.translatable("advancement.tcr.enter_realm_of_the_dream.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("enter_realm_of_the_dream", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.SKY_ISLAND_LEVEL_KEY))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{TCRLoot.ENTER_REALM_OF_THE_DREAM}, new ResourceLocation[0], CommandFunction.CacheableFunction.NONE))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "enter_realm_of_the_dream"), existingFileHelper);

            Advancement wow = Advancement.Builder.advancement()
                    .parent(theCasketOfReveries)
                    .display(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get(),//TODO:换成小树怪图标
                            Component.translatable("advancement.tcr.wow"),
                            Component.translatable("advancement.tcr.wow.desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion("wow", new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "wow"), existingFileHelper);


        }
    }
}
