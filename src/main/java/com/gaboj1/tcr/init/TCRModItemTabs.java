package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TCRModItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCasketOfReveriesMod.MOD_ID);

	//方块
	public static final RegistryObject<CreativeModeTab> BLOCK = REGISTRY.register("block",
			() -> CreativeModeTab.builder()
					.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
					.withTabsAfter(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spawn_egg"))
					.title(Component.translatable("item_group.the_casket_of_reveries.block"))
					.icon(() -> new ItemStack(TCRModBlocks.PORTAL_BED.get()))
					.displayItems((parameters, tabData) -> {

				tabData.accept(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get());
				tabData.accept(TCRModBlocks.PORTAL_BED.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get());



			}).build());

	//生物蛋
	public static final RegistryObject<CreativeModeTab> SPAWN_EGG = REGISTRY.register("spawn_egg",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block"))
					.withTabsAfter(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "loot"))
					.title(Component.translatable("item_group.the_casket_of_reveries.spawn_egg"))
					.icon(() -> new ItemStack(TCRModItems.MIDDLE_TREE_MONSTER_SPAWN_EGG.get()))
					.displayItems((parameters, tabData) -> {
						tabData.accept(TCRModItems.SMALL_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.MIDDLE_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.TREE_GUARDIAN_SPAWN_EGG.get());
						tabData.accept(TCRModItems.PASTORAL_PLAIN_VILLAGER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG.get());

					}).build());

	//战利品
	public static final RegistryObject<CreativeModeTab> LOOT = REGISTRY.register("loot",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spawn_egg"))
					.title(Component.translatable("item_group.the_casket_of_reveries.loot"))
					.icon(() -> new ItemStack(TCRModItems.TREE_SPIRIT_WAND.get()))
					.displayItems((parameters, tabData) -> {

				tabData.accept(TCRModItems.DESERT_EAGLE_AMMO.get());
				tabData.accept(TCRModItems.DESERT_EAGLE.get());

				//树脂
				tabData.accept(TCRModItems.BASIC_RESIN.get());
				tabData.accept(TCRModItems.INTERMEDIATE_RESIN.get());
				tabData.accept(TCRModItems.ADVANCED_RESIN.get());
				tabData.accept(TCRModItems.SUPER_RESIN.get());
				tabData.accept(TCRModItems.COPY_RESIN.get());


				tabData.accept(TCRModItems.ELDER_STAFF.get());
				tabData.accept(TCRModItems.TREE_DEMON_HORN.get());

				//法宝
				tabData.accept(TCRModItems.TREE_SPIRIT_WAND.get());

			}).build());

}
