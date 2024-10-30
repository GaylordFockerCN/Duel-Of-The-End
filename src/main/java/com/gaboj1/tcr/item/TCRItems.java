package com.gaboj1.tcr.item;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.custom.*;
import com.gaboj1.tcr.item.custom.armor.IceTigerArmorItem;
import com.gaboj1.tcr.item.custom.armor.OrichalcumArmorItem;
import com.gaboj1.tcr.item.custom.armor.TreeArmorItem;
import com.gaboj1.tcr.item.custom.boss_loot.FlySword;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import com.gaboj1.tcr.item.custom.weapon.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TCRItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<Item> AMMO = REGISTRY.register("ammo", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> GUN_COMMON = REGISTRY.register("gun", GunCommon::new);
	public static final RegistryObject<Item> GUN_PLUS = REGISTRY.register("gun_plus", GunPlus::new);
	public static final RegistryObject<Item> BULLET = REGISTRY.register("bullet", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> ICE_THORN = REGISTRY.register("bingci", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BASIC_RESIN = REGISTRY.register("basic_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(TCRRarities.FAN_PIN),1));
	public static final RegistryObject<Item> INTERMEDIATE_RESIN = REGISTRY.register("intermediate_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(TCRRarities.LIANG_PIN),BasicResin.INTERMEDIATE_RESIN));
	public static final RegistryObject<Item> ADVANCED_RESIN = REGISTRY.register("advanced_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(TCRRarities.SHANG_PIN),BasicResin.ADVANCED_RESIN));
	public static final RegistryObject<Item> SUPER_RESIN = REGISTRY.register("super_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(TCRRarities.TE_PIN),BasicResin.SUPER_RESIN));
	public static final RegistryObject<Item> COPY_RESIN = REGISTRY.register("copy_resin", () -> new CopyResin(new Item.Properties().setNoRepair().stacksTo(64).rarity((TCRRarities.XIAN_PIN))));
	public static final RegistryObject<Item> PURIFICATION_TALISMAN = REGISTRY.register("purification_talisman", () -> new PurificationTalisman(new Item.Properties().setNoRepair().stacksTo(1).rarity(TCRRarities.XIAN_PIN)));

	//神金（锭），原版金锭色阶 +70 -70 0
	public static final RegistryObject<Item> ORICHALCUM = REGISTRY.register("orichalcum", () -> new DropItem(new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	//粗神金
	public static final RegistryObject<Item> RAW_ORICHALCUM = REGISTRY.register("raw_orichalcum", () -> new DropItem(new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	//真·神金锭
	public static final RegistryObject<Item> GOD_ORICHALCUM = REGISTRY.register("god_orichalcum", () -> new DropItem(new Item.Properties().rarity(TCRRarities.TE_PIN)));
	//粗真·神金锭
	public static final RegistryObject<Item> GOD_RAW_ORICHALCUM = REGISTRY.register("god_raw_orichalcum", () -> new DropItem(new Item.Properties().rarity(TCRRarities.TE_PIN)));

	public static final RegistryObject<Item> ORICHALCUM_HELMET = REGISTRY.register("orichalcum_helmet",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.HELMET, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_CHESTPLATE = REGISTRY.register("orichalcum_chestplate",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_LEGGINGS = REGISTRY.register("orichalcum_leggings",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_BOOTS = REGISTRY.register("orichalcum_boots",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.BOOTS, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_BOW = REGISTRY.register("orichalcum_bow", () -> new OrichalcumBow(new Item.Properties().rarity(TCRRarities.LIANG_PIN).defaultDurability(128)));
	public static final RegistryObject<Item> ORICHALCUM_CROSSBOW = REGISTRY.register("orichalcum_crossbow", () -> new OrichalcumCrossbow(new Item.Properties().rarity(TCRRarities.SHANG_PIN).defaultDurability(128)));
	public static final RegistryObject<Item> GOD_ORICHALCUM_CROSSBOW = REGISTRY.register("god_orichalcum_crossbow", () -> new OrichalcumCrossbow(new Item.Properties().rarity(TCRRarities.TE_PIN).defaultDurability(256)){
		/**
		 * 耐久翻倍，不会损坏，自带多重射击
		 */
		@Override
		public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
			ItemStack itemStack = player.getItemInHand(hand);
			//一次消耗3点耐久
			if(itemStack.getDamageValue() + 3 >= itemStack.getMaxDamage()){
				return InteractionResultHolder.fail(itemStack);
			}
			return super.use(level, player, hand);
		}
	});
	public static final RegistryObject<Item> ORICHALCUM_GREAT_SWORD = REGISTRY.register("orichalcum_greatsword", () -> new Greatsword(TCRTiers.ORICHALCUM, 13, -3.5F, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_SWORD = REGISTRY.register("orichalcum_sword", () -> new SwordItem(TCRTiers.ORICHALCUM, 6, -2.4F, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_AXE = REGISTRY.register("orichalcum_axe", () -> new AxeItem(TCRTiers.ORICHALCUM, 8, -3.0F, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ORICHALCUM_PICKAXE = REGISTRY.register("orichalcum_pickaxe", () -> new PickaxeItem(TCRTiers.ORICHALCUM, 3, -2.4F, new Item.Properties().rarity(TCRRarities.LIANG_PIN)));
	public static final RegistryObject<Item> ICE_TIGER_HELMET = REGISTRY.register("ice_tiger_helmet",
			() -> new IceTigerArmorItem(TCRArmorMaterials.ICE_TIGER, ArmorItem.Type.HELMET, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> ICE_TIGER_CHESTPLATE = REGISTRY.register("ice_tiger_chestplate",
			() -> new IceTigerArmorItem(TCRArmorMaterials.ICE_TIGER, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> ICE_TIGER_LEGGINGS = REGISTRY.register("ice_tiger_leggings",
			() -> new IceTigerArmorItem(TCRArmorMaterials.ICE_TIGER, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> ICE_TIGER_BOOTS = REGISTRY.register("ice_tiger_boots",
			() -> new IceTigerArmorItem(TCRArmorMaterials.ICE_TIGER, ArmorItem.Type.BOOTS, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));

	public static final RegistryObject<Item> TREE_HELMET = REGISTRY.register("tree_helmet",
			() -> new TreeArmorItem(TCRArmorMaterials.TREE, ArmorItem.Type.HELMET, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> TREE_CHESTPLATE = REGISTRY.register("tree_chestplate",
			() -> new TreeArmorItem(TCRArmorMaterials.TREE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> TREE_LEGGINGS = REGISTRY.register("tree_leggings",
			() -> new TreeArmorItem(TCRArmorMaterials.TREE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> TREE_BOOTS = REGISTRY.register("tree_boots",
			() -> new TreeArmorItem(TCRArmorMaterials.TREE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(TCRRarities.SHANG_PIN)));
	public static final RegistryObject<Item> TREE_SPIRIT_WAND = REGISTRY.register("tree_spirit_wand", TreeSpiritWand::new);

	public static final RegistryObject<Item> SPIRIT_WAND = REGISTRY.register("spirit_wand", SpiritWand::new);
	public static final RegistryObject<Item> HEALTH_WAND = REGISTRY.register("health_wand", HealthWand::new);
	public static final RegistryObject<Item> TREE_DEMON_HORN = REGISTRY.register("tree_demon_horn", () -> new DropItem(new Item.Properties().rarity(TCRRarities.XIAN_PIN)));
	public static final RegistryObject<Item> TREE_DEMON_MASK = REGISTRY.register("tree_demon_mask", () -> new DropItem(new Item.Properties().rarity(TCRRarities.XIAN_PIN)));
	public static final RegistryObject<Item> TREE_DEMON_BRANCH = REGISTRY.register("tree_demon_branch", () -> new DropItem(new Item.Properties().rarity(TCRRarities.XIAN_PIN)));
	public static final RegistryObject<Item> TREE_DEMON_FRUIT = REGISTRY.register("tree_demon_fruit", () -> new DropItem(new Item.Properties().rarity(TCRRarities.XIAN_PIN)));
	public static final RegistryObject<Item> WITHERING_TOUCH = REGISTRY.register("withering_touch", () -> new DropItem(new Item.Properties().rarity(TCRRarities.TE_PIN)));
	public static final RegistryObject<Item> HEART_OF_THE_SAPLING = REGISTRY.register("heart_of_the_sapling", DropItem::new);
	public static final RegistryObject<Item> ESSENCE_OF_THE_ANCIENT_TREE = REGISTRY.register("essence_of_the_ancient_tree", DropItem::new);
	public static final RegistryObject<Item> BARK_OF_THE_GUARDIAN = REGISTRY.register("bark_of_the_guardian", DropItem::new);
	public static final RegistryObject<Item> STARLIT_DEWDROP = REGISTRY.register("starlit_dewdrop", DropItem::new);
	public static final RegistryObject<Item> DENSE_FOREST_CERTIFICATE = REGISTRY.register("dense_forest_certificate",  () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHEN_ZHEN)));
	public static final RegistryObject<Item> ELDER_STAFF = REGISTRY.register("elder_staff", DropItem::new);
	public static final RegistryObject<Item> HOLY_SWORD = REGISTRY.register("holy_sword", FlySword::new);
	public static final RegistryObject<Item> BRAWLER_GLOVES = REGISTRY.register("brawler_gloves", DropItem::new);
	public static final RegistryObject<Item> HAMMER_BEARER_FRAGMENT = REGISTRY.register("hammer_bearer_fragment", DropItem::new);
	public static final RegistryObject<Item> SWORDMASTER_TALISMAN = REGISTRY.register("swordmaster_talisman", DropItem::new);
	public static final RegistryObject<Item> ICE_TIGER_CLAW = REGISTRY.register("ice_tiger_claw", DropItem::new);
	public static final RegistryObject<Item> TIGER_KARAMBIT = REGISTRY.register("tiger_karambit", TigerKarambit::new);
	public static final RegistryObject<Item> TIGER_SOUL_ICE = REGISTRY.register("hupobing", DropItem::new);
	public static final RegistryObject<Item> AZURE_SKY_CERTIFICATE = REGISTRY.register("azure_sky_certificate",  () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHEN_ZHEN)));
	public static final RegistryObject<Item> PI_PA = REGISTRY.register("pi_pa", PiPa::new);

	public static final RegistryObject<Item> DREAMSCAPE_COIN = REGISTRY.register("dreamscape_coin",() ->  new DropItem(new Item.Properties().setNoRepair().rarity(TCRRarities.TE_PIN)));
	public static final RegistryObject<Item> DREAMSCAPE_COIN_PLUS = REGISTRY.register("dreamscape_coin_plus",() ->  new DropItem(new Item.Properties().setNoRepair().rarity(TCRRarities.XIAN_PIN)));
	public static final RegistryObject<Item> WALLET = REGISTRY.register("wallet", Wallet::new);
	public static final RegistryObject<Item> RECALL_SCROLL = REGISTRY.register("recall_scroll", RecallScroll::new);
	public static final RegistryObject<Item> BLUE_BANANA = REGISTRY.register("blue_banana",() ->  new DropItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> DREAM_TA = REGISTRY.register("dream_ta",() ->  new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> BEER = REGISTRY.register("beer",() ->  new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> COOKIE = REGISTRY.register("cookie",() ->  new DropItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> ELDER_CAKE = REGISTRY.register("elder_cake",() ->  new DropItem(new Item.Properties().food(TCRFoods.ELDER_CAKE)));
	//说出来你可能不信，以下的小物品是训练chatGPT3.5仿写后修改的（大力解放生产力！）
	public static final RegistryObject<Item> EDEN_APPLE = REGISTRY.register("eden_apple", () -> new DropItem(new Item.Properties().food(TCRFoods.EDEN_APPLE).rarity(TCRRarities.TE_PIN)));
	public static final RegistryObject<Item> DRINK1 = REGISTRY.register("drink1", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DRINK)));
	public static final RegistryObject<Item> DRINK2 = REGISTRY.register("drink2", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DRINK)));
	public static final RegistryObject<Item> GOLDEN_WIND_AND_DEW = REGISTRY.register("golden_wind_and_dew", () -> new DrinkItem(new Item.Properties().rarity(TCRRarities.TE_PIN).food(TCRFoods.GOLDEN_WIND_AND_DEW)));
	public static final RegistryObject<Item> GREEN_BANANA = REGISTRY.register("green_banana", () -> new DropItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> HOT_CHOCOLATE = REGISTRY.register("hot_chocolate", () -> new DrinkItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> JUICE_TEA = REGISTRY.register("juice_tea", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DRINK)));
	public static final RegistryObject<Item> MAO_DAI = REGISTRY.register("maodai", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> PINE_CONE = REGISTRY.register("pine_cone", () -> new DropItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> RED_WINE = REGISTRY.register("red_wine", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> WAN_MING_PEARL = REGISTRY.register("wan_ming_pearl", WanMingPearl::new);
	public static final RegistryObject<Item> LIGHT_ELIXIR = REGISTRY.register("light_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.NIGHT_VISION_PELLET)));
	public static final RegistryObject<Item> ASCENSION_ELIXIR = REGISTRY.register("ascension_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.TE_PIN).food(TCRFoods.SPEED_PELLET)));
	public static final RegistryObject<Item> LUCKY_ELIXIR = REGISTRY.register("lucky_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.LUCK_PELLET)));
	public static final RegistryObject<Item> EVASION_ELIXIR = REGISTRY.register("evasion_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.DAMAGE_RESISTANCE_PELLET)));
	public static final RegistryObject<Item> WATER_AVOIDANCE_ELIXIR = REGISTRY.register("water_avoidance_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.WATER_RESISTANCE_PELLET)));
	public static final RegistryObject<Item> FIRE_AVOIDANCE_ELIXIR = REGISTRY.register("fire_avoidance_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.FIRE_RESISTANCE_PELLET)));
	public static final RegistryObject<Item> COLD_AVOIDANCE_ELIXIR = REGISTRY.register("cold_avoidance_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.FROZEN_RESISTANCE_PELLET)));
	public static final RegistryObject<Item> THUNDER_AVOIDANCE_ELIXIR = REGISTRY.register("thunder_avoidance_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.THUNDER_RESISTANCE_PELLET)));
	public static final RegistryObject<Item> POISON_AVOIDANCE_ELIXIR = REGISTRY.register("poison_avoidance_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHANG_PIN).food(TCRFoods.POISON_RESISTANCE_PELLET)));
	public static final RegistryObject<Item> NINE_TURN_REVIVAL_ELIXIR = REGISTRY.register("nine_turn_revival_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.SHEN_ZHEN).food(TCRFoods.NINE_TRANSFORMATIONS_RESURRECTION_PELLET)));
	public static final RegistryObject<Item> STRENGTH_PILL = REGISTRY.register("strength_pill", () -> new DropItem(new Item.Properties().rarity(TCRRarities.TE_PIN).food(TCRFoods.POWER_PELLET)));
	public static final RegistryObject<Item> AQUA_GOLD_ELIXIR = REGISTRY.register("aqua_gold_elixir", () -> new DropItem(new Item.Properties().rarity(TCRRarities.XIAN_PIN).food(TCRFoods.GOLDEN_PELLET)));

//	public static final RegistryObject<Item> JELLY_CAT_SPAWN_EGG = registerEgg("jelly_cat_spawn_egg", TCRModEntities.JELLY_CAT);
	public static final RegistryObject<Item> JELLY_CAT_SPAWN_EGG = REGISTRY.register("jelly_cat_spawn_egg",
		() -> new ForgeSpawnEggItem(TCREntities.JELLY_CAT, 0xD57E36, 0x1D0D00,
			new Item.Properties()));
	public static final RegistryObject<Item> CATNIP = REGISTRY.register("catnip",  () -> new DropItem(new Item.Properties().food(TCRFoods.CATNIP)));
	public static final RegistryObject<Item> BLUE_MUSHROOM = REGISTRY.register("blue_mushroom",  () -> new DropItem(new Item.Properties().food(Foods.MUSHROOM_STEW)));


	public static final RegistryObject<Item> CAT_JELLY = REGISTRY.register("cat_jelly",  () -> new DropItem(new Item.Properties().food(TCRFoods.JELLY_CAT)));
	public static final RegistryObject<Item> SQUIRREL_SPAWN_EGG = REGISTRY.register("squirrel_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SQUIRREL, 0xD57E36, 0x1D0D00,
					new Item.Properties()));
	public static final RegistryObject<Item> CRAB_SPAWN_EGG = REGISTRY.register("crab_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.CRAB, 0xD57E36, 0x1D0D00,
					new Item.Properties()));
	public static final RegistryObject<Item> MIDDLE_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("middle_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.MIDDLE_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SMALL_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("small_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SMALL_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> TREE_GUARDIAN_SPAWN_EGG = REGISTRY.register("tree_guardian_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.TREE_GUARDIAN, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> HORRIBLE_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("horrible_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.UNKNOWN, 666, 888,
					new Item.Properties()));

	public static final RegistryObject<Item> WIND_FEATHER_FALCON_SPAWN_EGG = REGISTRY.register("wind_feather_falcon_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.WIND_FEATHER_FALCON, 9999, 888,
					new Item.Properties()));

	public static final RegistryObject<Item> SPRITE_SPAWN_EGG = REGISTRY.register("sprite_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SPRITE, 1, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> TIGER_SPAWN_EGG = REGISTRY.register("tiger_egg",
			() -> new ForgeSpawnEggItem(TCREntities.TIGER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> BOXER_SPAWN_EGG = REGISTRY.register("boxer_egg",
			() -> new ForgeSpawnEggItem(TCREntities.BOXER, 2, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> BIG_HAMMER_EGG = REGISTRY.register("big_hammer_egg",
			() -> new ForgeSpawnEggItem(TCREntities.BIG_HAMMER, 3, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SNOW_SWORDMAN_SPAWN_EGG = REGISTRY.register("snow_swordman_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SNOW_SWORDMAN, 26, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SWORD_CONTROLLER_SPAWN_EGG = REGISTRY.register("sword_controller_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SWORD_CONTROLLER, 50, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SECOND_BOSS_SPAWN_EGG = REGISTRY.register("second_boss_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SECOND_BOSS, 800, 50,
					new Item.Properties()));

	public static final RegistryObject<Item> CANG_LAN_SPAWN_EGG = REGISTRY.register("cang_lan_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.CANG_LAN, 6000, 50,
					new Item.Properties()));

	public static final RegistryObject<Item> SUALIONG_SPAWN_EGG = REGISTRY.register("sualiong_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.SUALIONG, 80000, 520,
					new Item.Properties()));

	public static final RegistryObject<Item> DESERT_FIGURE_SPAWN_EGG = REGISTRY.register("desert_figure_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.DESERT_FIGURE, 80000, 520,
					new Item.Properties()));

	public static final RegistryObject<Item> YGGDRASIL_SPAWN_EGG = REGISTRY.register("yggdrasil_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.YGGDRASIL, 1, 0x1D0D00,
					new Item.Properties()));
	public static final RegistryObject<Item> PASTORAL_PLAIN_VILLAGER_SPAWN_EGG = REGISTRY.register("pastoral_plain_villager_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.PASTORAL_PLAIN_VILLAGER, 0xD99999, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG = REGISTRY.register("pastoral_plain_villager_elder_spawn_egg",
			() -> new ForgeSpawnEggItem(TCREntities.PASTORAL_PLAIN_VILLAGER_ELDER, 0xD99999, 0x1D0D00,
					new Item.Properties()));

	public static RegistryObject<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> type){
		return REGISTRY.register(name,
				() -> new ForgeSpawnEggItem(type, 15714446, 9794134,
						new Item.Properties()));
	}

	public static RegistryObject<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int bgColor, int highlightColor){
		return REGISTRY.register(name,
				() -> new ForgeSpawnEggItem(type, bgColor, highlightColor,
						new Item.Properties()));
	}

}
