package com.gaboj1.tcr.item;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRModBlocks;
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
				tabData.accept(TCRModBlocks.TIGER_TRIAL_BLOCK.get());
				tabData.accept(TCRModBlocks.MIAO_YIN_BLOCK.get());
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

				//货币
				tabData.accept(TCRItems.DENSE_FOREST_CERTIFICATE.get());
				tabData.accept(TCRItems.DREAMSCAPE_COIN.get());
				tabData.accept(TCRItems.DREAMSCAPE_COIN_PLUS.get());
				tabData.accept(TCRItems.WAN_MING_PEARL.get());
				tabData.accept(TCRItems.RAW_ORICHALCUM.get());
				tabData.accept(TCRItems.ORICHALCUM.get());

			}).build());

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

						//法宝
						tabData.accept(TCRItems.TREE_SPIRIT_WAND.get());
						tabData.accept(TCRItems.HOLY_SWORD.get());

					}).build());

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
