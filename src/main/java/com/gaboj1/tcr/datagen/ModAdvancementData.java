package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModItems;
import com.gaboj1.tcr.loot.TCRLoot;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
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
//                    .addCriterion(TheCasketOfReveriesMod.MOD_ID, ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.SKY_ISLAND_LEVEL_KEY))
//                    .addCriterion(TheCasketOfReveriesMod.MOD_ID, ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.P_SKY_ISLAND_LEVEL_KEY))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, TheCasketOfReveriesMod.MOD_ID), existingFileHelper);

            Advancement enterRealmOfTheDream = Advancement.Builder.advancement()
                    .parent(theCasketOfReveries)
                    .display(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(),
                            Component.translatable(pre+"enter_realm_of_the_dream"),
                            Component.translatable(pre+"enter_realm_of_the_dream.desc"),
                            null,
                            FrameType.TASK, true, true, false)
//                    .addCriterion("enter_realm_of_the_dream", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.SKY_ISLAND_LEVEL_KEY))
//                    .addCriterion("enter_realm_of_the_dream", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.P_SKY_ISLAND_LEVEL_KEY))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{TCRLoot.ENTER_REALM_OF_THE_DREAM}, new ResourceLocation[0], CommandFunction.CacheableFunction.NONE))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "enter_realm_of_the_dream"), existingFileHelper);

            String name = "wow";
            Advancement wow = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)
                    .display(TCRModItems.SMALL_TREE_MONSTER_SPAWN_EGG.get(),
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

            name = "melee_mage";
            Advancement melee_mage = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)//TODO 换成打过树妖后
                    .display(TCRModItems.TREE_SPIRIT_WAND.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "mass_production";
            Advancement mass_production = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)//TODO 换成打过树妖后
                    .display(TCRModItems.COPY_RESIN.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "so_rich";
            Advancement so_rich = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)//TODO 换成打过树妖后
                    .display(Items.GLASS_BOTTLE,
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "die_for_summon";
            Advancement die_for_summon = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)//TODO 换成打过树妖后
                    .display(TCRModItems.TREE_SPIRIT_WAND.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "day_dreamer";
            Advancement day_dreamer = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)
                    .display(TCRModItems.DESERT_EAGLE.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "can_double_hold";
            Advancement can_double_hold = Advancement.Builder.advancement()
                    .parent(day_dreamer)
                    .display(TCRModItems.DESERT_EAGLE.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

            name = "cats_friend";
            Advancement cats_friend = Advancement.Builder.advancement()
                    .parent(enterRealmOfTheDream)
                    .display(TCRModItems.JELLY_CAT_SPAWN_EGG.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            FrameType.GOAL, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), existingFileHelper);

        }

        /**
         * 备用
         * @param parent
         * @param name
         * @param consumer
         * @param helper
         * @param type
         * @return
         */
        public Advancement registerAdvancement(Advancement parent, String name, Consumer<Advancement> consumer , ExistingFileHelper helper, FrameType type){
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(TCRModItems.JELLY_CAT_SPAWN_EGG.get(),
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            type, true, true, true)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), helper);
        }


    }

    public static void getAdvancement(String name, ServerPlayer serverPlayer){
        Advancement _adv = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,name));
        AdvancementProgress _ap = serverPlayer.getAdvancements().getOrStartProgress(_adv);
        if (!_ap.isDone()) {
            for (String criteria : _ap.getRemainingCriteria())
                serverPlayer.getAdvancements().award(_adv, criteria);
        }
    }

}
