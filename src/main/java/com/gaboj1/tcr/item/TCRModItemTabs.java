package com.gaboj1.tcr.item;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.item.TCRModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
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
				tabData.accept(TCRModBlocks.PORTAL_BLOCK.get());
				tabData.accept(TCRModBlocks.YGGDRASIL_BLOCK.get());
				tabData.accept(TCRModBlocks.CATNIP.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_TRAPDOOR.get());
				tabData.accept(TCRModBlocks.ORICHALCUM_ORE.get());

			}).build());

	//生物蛋
	public static final RegistryObject<CreativeModeTab> SPAWN_EGG = REGISTRY.register("spawn_egg",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block"))
					.withTabsAfter(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "loot"))
					.title(Component.translatable("item_group.the_casket_of_reveries.spawn_egg"))
					.icon(() -> new ItemStack(TCRModItems.JELLY_CAT_SPAWN_EGG.get()))
					.displayItems((parameters, tabData) -> {
						tabData.accept(TCRModItems.JELLY_CAT_SPAWN_EGG.get());
						tabData.accept(TCRModItems.SQUIRREL_SPAWN_EGG.get());
						tabData.accept(TCRModItems.CRAB_SPAWN_EGG.get());
						tabData.accept(TCRModItems.SMALL_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.MIDDLE_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.TREE_GUARDIAN_SPAWN_EGG.get());
						tabData.accept(TCRModItems.YGGDRASIL_SPAWN_EGG.get());
						tabData.accept(TCRModItems.PASTORAL_PLAIN_VILLAGER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.SPRITE_SPAWN_EGG.get());
						tabData.accept(TCRModItems.TIGER_EGG.get());
						tabData.accept(TCRModItems.BOXER_EGG.get());
						tabData.accept(TCRModItems.BIG_HAMMER_EGG.get());
						tabData.accept(TCRModItems.SNOW_SWORDMAN_SPAWN_EGG.get());
						tabData.accept(TCRModItems.SWORD_CONTROLLER_SPAWN_EGG.get());
						tabData.accept(TCRModItems.SECOND_BOSS_SPAWN_EGG.get());

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

				//掉落物
				tabData.accept(TCRModItems.ELDER_STAFF.get());
				tabData.accept(TCRModItems.TREE_DEMON_HORN.get());
				tabData.accept(TCRModItems.TREE_DEMON_MASK.get());
				tabData.accept(TCRModItems.TREE_DEMON_FRUIT.get());
				tabData.accept(TCRModItems.TREE_DEMON_BRANCH.get());
				tabData.accept(TCRModItems.WITHERING_TOUCH.get());

				//盔甲
				tabData.accept(TCRModItems.ORICHALCUM_HELMET.get());
				tabData.accept(TCRModItems.ORICHALCUM_CHESTPLATE.get());
				tabData.accept(TCRModItems.ORICHALCUM_LEGGINGS.get());
				tabData.accept(TCRModItems.ORICHALCUM_BOOTS.get());

				//货币
				tabData.accept(TCRModItems.DENSE_FOREST_CERTIFICATE.get());
				tabData.accept(TCRModItems.DREAMSCAPE_COIN.get());
				tabData.accept(TCRModItems.DREAMSCAPE_COIN_PLUS.get());
				tabData.accept(TCRModItems.RAW_ORICHALCUM.get());
				tabData.accept(TCRModItems.ORICHALCUM.get());

				//法宝
				tabData.accept(TCRModItems.TREE_SPIRIT_WAND.get());
				tabData.accept(TCRModItems.HOLY_SWORD.get());

				//食物
				tabData.accept(TCRModItems.BLUE_BANANA.get());
				tabData.accept(TCRModItems.DREAM_TA.get());
				tabData.accept(TCRModItems.BEER.get());
				tabData.accept(TCRModItems.COOKIE.get());
				tabData.accept(TCRModItems.ELDER_CAKE.get());
				tabData.accept(TCRModItems.EDEN_APPLE.get());
				tabData.accept(TCRModItems.DRINK1.get());
				tabData.accept(TCRModItems.DRINK2.get());
				tabData.accept(TCRModItems.GOLDEN_WIND_AND_DEW.get());
				tabData.accept(TCRModItems.GREEN_BANANA.get());
				tabData.accept(TCRModItems.HOT_CHOCOLATE.get());
				tabData.accept(TCRModItems.JUICE_TEA.get());
				tabData.accept(TCRModItems.MAO_DAI.get());
				tabData.accept(TCRModItems.PINE_CONE.get());
				tabData.accept(TCRModItems.RED_WINE.get());
				tabData.accept(TCRModItems.CATNIP.get());

			}).build());

}
