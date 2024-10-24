package com.gaboj1.tcr.item;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TCRItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCasketOfReveriesMod.MOD_ID);

	//方块
	public static final RegistryObject<CreativeModeTab> BLOCK = REGISTRY.register("block",
			() -> CreativeModeTab.builder()
					.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
					.withTabsAfter(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spawn_egg"))
					.title(Component.translatable("item_group.the_casket_of_reveries.block"))
					.icon(() -> new ItemStack(TCRBlocks.PORTAL_BED.get()))
					.displayItems((parameters, tabData) -> {

				tabData.accept(TCRBlocks.BETTER_STRUCTURE_BLOCK.get());
				tabData.accept(TCRBlocks.PORTAL_BED.get());
				tabData.accept(TCRBlocks.PORTAL_BLOCK.get());
				tabData.accept(TCRBlocks.YGGDRASIL_BLOCK.get());
				tabData.accept(TCRBlocks.TIGER_TRIAL_BLOCK.get());
				tabData.accept(TCRBlocks.MIAO_YIN_BLOCK.get());
				tabData.accept(TCRBlocks.ELITE_BIG_HAMMER_BLOCK.get());
				tabData.accept(TCRBlocks.CATNIP.get());
				tabData.accept(TCRBlocks.BLUE_MUSHROOM.get());
				tabData.accept(TCRBlocks.THIRST_BLOOD_ROSE.get());
				tabData.accept(TCRBlocks.LAZY_ROSE.get());
				tabData.accept(TCRBlocks.MELANCHOLIC_ROSE.get());
				tabData.accept(TCRBlocks.WITHERED_ROSE.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_SAPLING.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get());
				tabData.accept(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_TRAPDOOR.get());
				tabData.accept(TCRBlocks.ORICHALCUM_ORE.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_1.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_2.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_3.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_4.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_5.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_6.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_7.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_8.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_9.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_1_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_1_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_2_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_2_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_3_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_3_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_4_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_4_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_5_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_5_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_6_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_6_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_7_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_7_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_8_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_8_SLAB.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_9_STAIRS.get());
				tabData.accept(TCRBlocks.BOSS2_ROOM_9_SLAB.get());


					}).build());

	//生物蛋
	public static final RegistryObject<CreativeModeTab> SPAWN_EGG = REGISTRY.register("spawn_egg",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block"))
					.withTabsAfter(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "loot"))
					.title(Component.translatable("item_group.the_casket_of_reveries.spawn_egg"))
					.icon(() -> new ItemStack(TCRItems.JELLY_CAT_SPAWN_EGG.get()))
					.displayItems((parameters, tabData) -> {
						//梦灵
						tabData.accept(TCRItems.JELLY_CAT_SPAWN_EGG.get());
						tabData.accept(TCRItems.SQUIRREL_SPAWN_EGG.get());
						tabData.accept(TCRItems.CRAB_SPAWN_EGG.get());
						//Biome1
						tabData.accept(TCRItems.SMALL_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRItems.MIDDLE_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRItems.TREE_GUARDIAN_SPAWN_EGG.get());
						tabData.accept(TCRItems.SPRITE_SPAWN_EGG.get());
						tabData.accept(TCRItems.HORRIBLE_TREE_MONSTER_SPAWN_EGG.get());
						tabData.accept(TCRItems.WIND_FEATHER_FALCON_SPAWN_EGG.get());
						tabData.accept(TCRItems.YGGDRASIL_SPAWN_EGG.get());
						tabData.accept(TCRItems.CANG_LAN_SPAWN_EGG.get());

						tabData.accept(TCRItems.PASTORAL_PLAIN_VILLAGER_SPAWN_EGG.get());
						tabData.accept(TCRItems.PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG.get());

						//Biome2
						tabData.accept(TCRItems.TIGER_SPAWN_EGG.get());
						tabData.accept(TCRItems.BOXER_SPAWN_EGG.get());
						tabData.accept(TCRItems.BIG_HAMMER_EGG.get());
						tabData.accept(TCRItems.SNOW_SWORDMAN_SPAWN_EGG.get());
						tabData.accept(TCRItems.SWORD_CONTROLLER_SPAWN_EGG.get());
						tabData.accept(TCRItems.SECOND_BOSS_SPAWN_EGG.get());

						//Biome3
						tabData.accept(TCRItems.SUALIONG_SPAWN_EGG.get());
						tabData.accept(TCRItems.DESERT_FIGURE_SPAWN_EGG.get());


					}).build());

	//战利品
	public static final RegistryObject<CreativeModeTab> LOOT = REGISTRY.register("loot",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spawn_egg"))
					.title(Component.translatable("item_group.the_casket_of_reveries.loot"))
					.icon(() -> new ItemStack(TCRItems.WITHERING_TOUCH.get()))
					.displayItems((parameters, tabData) -> {
				//树脂
				tabData.accept(TCRItems.BASIC_RESIN.get());
				tabData.accept(TCRItems.INTERMEDIATE_RESIN.get());
				tabData.accept(TCRItems.ADVANCED_RESIN.get());
				tabData.accept(TCRItems.SUPER_RESIN.get());
				tabData.accept(TCRItems.COPY_RESIN.get());

				//掉落物
				tabData.accept(TCRItems.ELDER_STAFF.get());
				tabData.accept(TCRItems.PURIFICATION_TALISMAN.get());

				tabData.accept(TCRItems.TREE_DEMON_HORN.get());
				tabData.accept(TCRItems.TREE_DEMON_MASK.get());
				tabData.accept(TCRItems.TREE_DEMON_FRUIT.get());
				tabData.accept(TCRItems.TREE_DEMON_BRANCH.get());

				tabData.accept(TCRItems.WITHERING_TOUCH.get());

				tabData.accept(TCRItems.HEART_OF_THE_SAPLING.get());
				tabData.accept(TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get());
				tabData.accept(TCRItems.BARK_OF_THE_GUARDIAN.get());
				tabData.accept(TCRItems.STARLIT_DEWDROP.get());

				tabData.accept(TCRItems.ICE_THORN.get());
				tabData.accept(TCRItems.TIGER_SOUL_ICE.get());
				tabData.accept(TCRItems.ICE_TIGER_CLAW.get());
				tabData.accept(TCRItems.BRAWLER_GLOVES.get());
				tabData.accept(TCRItems.HAMMER_BEARER_FRAGMENT.get());
				tabData.accept(TCRItems.SWORDMASTER_TALISMAN.get());

				//货币
				tabData.accept(TCRItems.DENSE_FOREST_CERTIFICATE.get());
				tabData.accept(TCRItems.DREAMSCAPE_COIN.get());
				tabData.accept(TCRItems.DREAMSCAPE_COIN_PLUS.get());
				tabData.accept(TCRItems.WALLET.get());
				tabData.accept(TCRItems.RECALL_SCROLL.get());
				tabData.accept(TCRItems.WAN_MING_PEARL.get());
				tabData.accept(TCRItems.RAW_ORICHALCUM.get());
				tabData.accept(TCRItems.ORICHALCUM.get());

			}).build());

	//战斗用品
	public static final RegistryObject<CreativeModeTab> WEAPON = REGISTRY.register("weapon",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spawn_egg"))
					.title(Component.translatable("item_group.the_casket_of_reveries.weapon"))
					.icon(() -> new ItemStack(TCRItems.TIGER_KARAMBIT.get()))
					.displayItems((parameters, tabData) -> {

						tabData.accept(TCRItems.AMMO.get());
						tabData.accept(TCRItems.GUN_COMMON.get());
						tabData.accept(TCRItems.GUN_PLUS.get());
						tabData.accept(TCRItems.SPIRIT_WAND.get());
						tabData.accept(TCRItems.HEALTH_WAND.get());
						tabData.accept(TCRItems.TIGER_KARAMBIT.get());
						tabData.accept(TCRItems.PI_PA.get());

						//盔甲
						tabData.accept(TCRItems.ORICHALCUM_HELMET.get());
						tabData.accept(TCRItems.ORICHALCUM_CHESTPLATE.get());
						tabData.accept(TCRItems.ORICHALCUM_LEGGINGS.get());
						tabData.accept(TCRItems.ORICHALCUM_BOOTS.get());

						tabData.accept(TCRItems.ICE_TIGER_HELMET.get());
						tabData.accept(TCRItems.ICE_TIGER_CHESTPLATE.get());
						tabData.accept(TCRItems.ICE_TIGER_LEGGINGS.get());
						tabData.accept(TCRItems.ICE_TIGER_BOOTS.get());

						tabData.accept(TCRItems.TREE_HELMET.get());
						tabData.accept(TCRItems.TREE_CHESTPLATE.get());
						tabData.accept(TCRItems.TREE_LEGGINGS.get());
						tabData.accept(TCRItems.TREE_BOOTS.get());

						//法宝
						tabData.accept(TCRItems.TREE_SPIRIT_WAND.get());
						tabData.accept(TCRItems.HOLY_SWORD.get());

					}).build());

	//可食用物品
	public static final RegistryObject<CreativeModeTab> FOOD = REGISTRY.register("food",
			() -> CreativeModeTab.builder()
					.withTabsBefore(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spawn_egg"))
					.title(Component.translatable("item_group.the_casket_of_reveries.food"))
					.icon(() -> new ItemStack(TCRItems.BLUE_BANANA.get()))
					.displayItems((parameters, tabData) -> {
						tabData.accept(TCRItems.BLUE_BANANA.get());
						tabData.accept(TCRItems.DREAM_TA.get());
						tabData.accept(TCRItems.BEER.get());
						tabData.accept(TCRItems.COOKIE.get());
						tabData.accept(TCRItems.ELDER_CAKE.get());
						tabData.accept(TCRItems.EDEN_APPLE.get());
						tabData.accept(TCRItems.DRINK1.get());
						tabData.accept(TCRItems.DRINK2.get());
						tabData.accept(TCRItems.GOLDEN_WIND_AND_DEW.get());
						tabData.accept(TCRItems.GREEN_BANANA.get());
						tabData.accept(TCRItems.HOT_CHOCOLATE.get());
						tabData.accept(TCRItems.JUICE_TEA.get());
						tabData.accept(TCRItems.MAO_DAI.get());
						tabData.accept(TCRItems.PINE_CONE.get());
						tabData.accept(TCRItems.RED_WINE.get());
						tabData.accept(TCRItems.CATNIP.get());
						tabData.accept(TCRItems.BLUE_MUSHROOM.get());

						tabData.accept(TCRItems.LIGHT_ELIXIR.get());
						tabData.accept(TCRItems.ASCENSION_ELIXIR.get());
						tabData.accept(TCRItems.LUCKY_ELIXIR.get());
						tabData.accept(TCRItems.EVASION_ELIXIR.get());
						tabData.accept(TCRItems.WATER_AVOIDANCE_ELIXIR.get());
						tabData.accept(TCRItems.FIRE_AVOIDANCE_ELIXIR.get());
						tabData.accept(TCRItems.COLD_AVOIDANCE_ELIXIR.get());
						tabData.accept(TCRItems.THUNDER_AVOIDANCE_ELIXIR.get());
						tabData.accept(TCRItems.POISON_AVOIDANCE_ELIXIR.get());
						tabData.accept(TCRItems.NINE_TURN_REVIVAL_ELIXIR.get());
						tabData.accept(TCRItems.STRENGTH_PILL.get());
						tabData.accept(TCRItems.AQUA_GOLD_ELIXIR.get());

					}).build());

}
