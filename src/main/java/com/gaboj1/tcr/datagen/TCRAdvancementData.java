package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.datagen.loot.TCRLoot;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TCRAdvancementData extends ForgeAdvancementProvider {
    public TCRAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new TCRAdvancements()));
    }

    public static class TCRAdvancements implements AdvancementGenerator {

        public final String pre = "advancement."+TheCasketOfReveriesMod.MOD_ID+".";
        private Consumer<Advancement> consumer;
        private ExistingFileHelper helper;
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
            this.consumer = consumer;
            this.helper = existingFileHelper;

            Advancement theCasketOfReveries = Advancement.Builder.advancement()
                    .display(TCRBlocks.PORTAL_BED.get(),
                            Component.translatable(pre+TheCasketOfReveriesMod.MOD_ID),
                            Component.translatable(pre+TheCasketOfReveriesMod.MOD_ID+".desc"),
                            new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/block/dense_forest_dirt.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion(TheCasketOfReveriesMod.MOD_ID, ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.P_SKY_ISLAND_LEVEL_KEY))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, TheCasketOfReveriesMod.MOD_ID), existingFileHelper);

            Advancement enterRealmOfTheDream = Advancement.Builder.advancement()
                    .parent(theCasketOfReveries)
                    .display(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(),
                            Component.translatable(pre+"enter_realm_of_the_dream"),
                            Component.translatable(pre+"enter_realm_of_the_dream.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("enter_realm_of_the_dream", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(TCRDimension.P_SKY_ISLAND_LEVEL_KEY))
                    .rewards(new AdvancementRewards(0, new ResourceLocation[]{TCRLoot.ENTER_REALM_OF_THE_DREAM}, new ResourceLocation[0], CommandFunction.CacheableFunction.NONE))
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "enter_realm_of_the_dream"), existingFileHelper);

            //Biome1成就
            Advancement wow = registerAdvancement(enterRealmOfTheDream, "wow", FrameType.GOAL, TCRItems.SMALL_TREE_MONSTER_SPAWN_EGG.get());
            Advancement tryWakeUp = registerAdvancement(enterRealmOfTheDream, "try_wake_up", FrameType.GOAL, TCRBlocks.PORTAL_BED.get().asItem());
            Advancement mass_production = registerAdvancement(enterRealmOfTheDream, "mass_production", FrameType.GOAL, TCRItems.COPY_RESIN.get());
            Advancement so_rich = registerAdvancement(enterRealmOfTheDream, "so_rich", FrameType.GOAL, Items.GLASS_BOTTLE);
            Advancement spend_money_like_water = registerAdvancement(enterRealmOfTheDream, "spend_money_like_water", FrameType.GOAL, TCRItems.ORICHALCUM.get());
            Advancement day_dreamer = registerAdvancement(enterRealmOfTheDream, "day_dreamer", FrameType.GOAL, TCRItems.GUN_COMMON.get());
            Advancement can_double_hold = registerAdvancement(enterRealmOfTheDream, "can_double_hold", FrameType.GOAL, TCRItems.GUN_COMMON.get());
            Advancement cats_friend = registerAdvancement(enterRealmOfTheDream, "cats_friend", FrameType.GOAL, TCRItems.JELLY_CAT_SPAWN_EGG.get());
            Advancement first_cat = registerAdvancement(enterRealmOfTheDream, "first_cat", FrameType.GOAL, TCRItems.JELLY_CAT_SPAWN_EGG.get(), true, true, true, TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(TCREntities.JELLY_CAT.get()).build()));
            Advancement cat_food = registerAdvancement(enterRealmOfTheDream, "cat_food", FrameType.CHALLENGE, TCRItems.CATNIP.get(), true, true, true, ConsumeItemTrigger.TriggerInstance.usedItem(TCRItems.CATNIP.get()));
            Advancement cat_jelly = registerAdvancement(enterRealmOfTheDream, "cat_jelly", FrameType.CHALLENGE, TCRItems.CAT_JELLY.get(), true, true, true, ConsumeItemTrigger.TriggerInstance.usedItem(TCRItems.CAT_JELLY.get()));
            Advancement ride_llama = registerAdvancement(enterRealmOfTheDream, "ride_llama", FrameType.GOAL, Items.LLAMA_SPAWN_EGG);
            Advancement purification_talisman = registerAdvancement(enterRealmOfTheDream, "purification_talisman", FrameType.GOAL, TCRItems.PURIFICATION_TALISMAN.get());

            Advancement finish_biome_1 = registerAdvancement(enterRealmOfTheDream, "finish_biome_1", FrameType.CHALLENGE, TCRItems.DENSE_FOREST_CERTIFICATE.get());
            Advancement die_for_summon = registerAdvancement(finish_biome_1, "die_for_summon", FrameType.GOAL, TCRItems.TREE_SPIRIT_WAND.get());
            Advancement melee_mage = registerAdvancement(finish_biome_1, "melee_mage", FrameType.GOAL, TCRItems.TREE_SPIRIT_WAND.get());

            //Biome2成就
            Advancement finish_biome_2 = registerAdvancement(enterRealmOfTheDream, "finish_biome_2", FrameType.CHALLENGE, TCRItems.TREE_SPIRIT_WAND.get());
            Advancement no_see = registerAdvancement(enterRealmOfTheDream, "no_see", FrameType.GOAL, TCRItems.PI_PA.get());
            Advancement kill_shu_fu = registerAdvancement(enterRealmOfTheDream, "kill_shu_fu", FrameType.GOAL, TCRItems.PI_PA.get());
            Advancement kill_shu_fu2 = registerAdvancement(enterRealmOfTheDream, "kill_shu_fu2", FrameType.GOAL, TCRItems.PI_PA.get());
            Advancement jump_1000 = registerAdvancement(enterRealmOfTheDream, "jump_1000", FrameType.GOAL, Items.RABBIT);

        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, boolean showToast, boolean announceToChat, boolean hidden){
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            type, showToast, announceToChat, hidden)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, boolean showToast, boolean announceToChat, boolean hidden, CriterionTriggerInstance instance){
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(pre+name),
                            Component.translatable(pre+name+".desc"),
                            null,
                            type, true, true, true)
                    .addCriterion(name, instance)
                    .save(consumer, new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display){
            return registerAdvancement(parent, name, type, display, true, true, true);
        }


    }

    public static void getAdvancement(String name, ServerPlayer serverPlayer){
        Advancement _adv = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,name));
        if(_adv == null){
            TheCasketOfReveriesMod.LOGGER.info("advancement:\""+name+"\" is null!");
            return;
        }
        AdvancementProgress _ap = serverPlayer.getAdvancements().getOrStartProgress(_adv);
        if (!_ap.isDone()) {
            for (String criteria : _ap.getRemainingCriteria())
                serverPlayer.getAdvancements().award(_adv, criteria);
        }
    }

    public static boolean isDone(String name, ServerPlayer serverPlayer){
        Advancement _adv = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,name));
        if(_adv == null){
            TheCasketOfReveriesMod.LOGGER.info("advancement:\""+name+"\" is null!");
            return false;
        }
        AdvancementProgress _ap = serverPlayer.getAdvancements().getOrStartProgress(_adv);
        return _ap.isDone();
    }

}
