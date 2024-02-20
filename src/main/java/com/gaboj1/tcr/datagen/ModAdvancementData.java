package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.loot.TCRLoot;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
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

        public final String pre = "advancement."+TheCasketOfReveriesMod.MOD_ID+".";
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {

            Advancement theCasketOfReveries = Advancement.Builder.advancement()
                    .display(TCRModBlocks.PORTAL_BED.get(),
                            Component.translatable(pre+TheCasketOfReveriesMod.MOD_ID),
                            Component.translatable(pre+TheCasketOfReveriesMod.MOD_ID+".desc"),
                            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/block/dense_forest_dirt.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion(TheCasketOfReveriesMod.MOD_ID, ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.SKY_ISLAND_LEVEL_KEY))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, TheCasketOfReveriesMod.MOD_ID), existingFileHelper);

            Advancement enterRealmOfTheDream = Advancement.Builder.advancement()
                    .parent(theCasketOfReveries)
                    .display(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(),
                            Component.translatable(pre+"enter_realm_of_the_dream"),
                            Component.translatable(pre+"enter_realm_of_the_dream.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("enter_realm_of_the_dream", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.SKY_ISLAND_LEVEL_KEY))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{TCRLoot.ENTER_REALM_OF_THE_DREAM}, new ResourceLocation[0], CommandFunction.CacheableFunction.NONE))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "enter_realm_of_the_dream"), existingFileHelper);

            String name = "wow";
            Advancement wow = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)
                    .display(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get(),//TODO:换成小树怪图标
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "try_wake_up";
            Advancement tryWakeUp = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)
                    .display(TCRModBlocks.PORTAL_BED.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);


        }
    }
}
