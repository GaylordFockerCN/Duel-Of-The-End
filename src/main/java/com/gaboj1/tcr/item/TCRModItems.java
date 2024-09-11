package com.gaboj1.tcr.item;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.item.custom.*;
import com.gaboj1.tcr.item.custom.armor.OrichalcumArmorItem;
import com.gaboj1.tcr.item.custom.boss_loot.FlySword;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import com.gaboj1.tcr.item.custom.gun.GunCommon;
import com.gaboj1.tcr.item.custom.gun.GunPlus;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TCRModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<Item> AMMO = REGISTRY.register("ammo", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> GUN_COMMON = REGISTRY.register("gun", GunCommon::new);
	public static final RegistryObject<Item> GUN_PLUS = REGISTRY.register("gun_plus", GunPlus::new);
	public static final RegistryObject<Item> BULLET = REGISTRY.register("bullet", () -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> BASIC_RESIN = REGISTRY.register("basic_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.COMMON),1));
	public static final RegistryObject<Item> INTERMEDIATE_RESIN = REGISTRY.register("intermediate_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.UNCOMMON),BasicResin.INTERMEDIATE_RESIN));
	public static final RegistryObject<Item> ADVANCED_RESIN = REGISTRY.register("advanced_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.RARE),BasicResin.ADVANCED_RESIN));
	public static final RegistryObject<Item> SUPER_RESIN = REGISTRY.register("super_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.EPIC),BasicResin.SUPER_RESIN));
	public static final RegistryObject<Item> COPY_RESIN = REGISTRY.register("copy_resin", () -> new CopyResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.EPIC)));

	//神金（锭），原版金锭色阶 +70 -70 0
	public static final RegistryObject<Item> ORICHALCUM = REGISTRY.register("orichalcum", () -> new DropItem(new Item.Properties().rarity(Rarity.RARE)));
	//粗神金
	public static final RegistryObject<Item> RAW_ORICHALCUM = REGISTRY.register("raw_orichalcum", () -> new DropItem(new Item.Properties().rarity(Rarity.RARE)));

	public static final RegistryObject<Item> ORICHALCUM_HELMET = REGISTRY.register("orichalcum_helmet",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> ORICHALCUM_CHESTPLATE = REGISTRY.register("orichalcum_chestplate",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> ORICHALCUM_LEGGINGS = REGISTRY.register("orichalcum_leggings",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> ORICHALCUM_BOOTS = REGISTRY.register("orichalcum_boots",
			() -> new OrichalcumArmorItem(TCRArmorMaterials.ORICHALCUM, ArmorItem.Type.BOOTS, new Item.Properties()));
	public static final RegistryObject<Item> TREE_SPIRIT_WAND = REGISTRY.register("tree_spirit_wand", TreeSpiritWand::new);

	public static final RegistryObject<Item> SPIRIT_WAND = REGISTRY.register("spirit_wand", SpiritWand::new);
	public static final RegistryObject<Item> HEALTH_WAND = REGISTRY.register("health_wand", HealthWand::new);
	public static final RegistryObject<Item> TREE_DEMON_HORN = REGISTRY.register("tree_demon_horn", () -> new DropItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> TREE_DEMON_MASK = REGISTRY.register("tree_demon_mask", () -> new DropItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> TREE_DEMON_BRANCH = REGISTRY.register("tree_demon_branch", () -> new DropItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> TREE_DEMON_FRUIT = REGISTRY.register("tree_demon_fruit", () -> new DropItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> WITHERING_TOUCH = REGISTRY.register("withering_touch", () -> new DropItem(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> HEART_OF_THE_SAPLING = REGISTRY.register("heart_of_the_sapling", DropItem::new);
	public static final RegistryObject<Item> ESSENCE_OF_THE_ANCIENT_TREE = REGISTRY.register("essence_of_the_ancient_tree", DropItem::new);
	public static final RegistryObject<Item> BARK_OF_THE_GUARDIAN = REGISTRY.register("bark_of_the_guardian", DropItem::new);
	public static final RegistryObject<Item> STARLIT_DEWDROP = REGISTRY.register("starlit_dewdrop", DropItem::new);
	public static final RegistryObject<Item> DENSE_FOREST_CERTIFICATE = REGISTRY.register("dense_forest_certificate",  () -> new DropItem(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> ELDER_STAFF = REGISTRY.register("elder_staff", DropItem::new);
	public static final RegistryObject<Item> HOLY_SWORD = REGISTRY.register("holy_sword", FlySword::new);
	public static final RegistryObject<Item> DREAMSCAPE_COIN = REGISTRY.register("dreamscape_coin",() ->  new DropItem(new Item.Properties().setNoRepair().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> DREAMSCAPE_COIN_PLUS = REGISTRY.register("dreamscape_coin_plus",() ->  new DropItem(new Item.Properties().setNoRepair().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> BLUE_BANANA = REGISTRY.register("blue_banana",() ->  new DropItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> DREAM_TA = REGISTRY.register("dream_ta",() ->  new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> BEER = REGISTRY.register("beer",() ->  new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> COOKIE = REGISTRY.register("cookie",() ->  new DropItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> ELDER_CAKE = REGISTRY.register("elder_cake",() ->  new DropItem(new Item.Properties().food(TCRFoods.ELDER_CAKE)));
	//说出来你可能不信，以下的小物品是训练chatGPT3.5仿写后修改的（大力解放生产力！）
	public static final RegistryObject<Item> EDEN_APPLE = REGISTRY.register("eden_apple", () -> new DropItem(new Item.Properties().food(TCRFoods.EDEN_APPLE).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> DRINK1 = REGISTRY.register("drink1", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DRINK)));
	public static final RegistryObject<Item> DRINK2 = REGISTRY.register("drink2", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DRINK)));
	public static final RegistryObject<Item> GOLDEN_WIND_AND_DEW = REGISTRY.register("golden_wind_and_dew", () -> new DrinkItem(new Item.Properties().food(TCRFoods.GOLDEN_WIND_AND_DEW)));
	public static final RegistryObject<Item> GREEN_BANANA = REGISTRY.register("green_banana", () -> new DropItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> HOT_CHOCOLATE = REGISTRY.register("hot_chocolate", () -> new DrinkItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> JUICE_TEA = REGISTRY.register("juice_tea", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DRINK)));
	public static final RegistryObject<Item> MAO_DAI = REGISTRY.register("maodai", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> PINE_CONE = REGISTRY.register("pine_cone", () -> new DropItem(new Item.Properties().food(TCRFoods.COMMON)));
	public static final RegistryObject<Item> RED_WINE = REGISTRY.register("red_wine", () -> new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> WANG_MING_PEARL = REGISTRY.register("wang_ming_pearl", () -> new DropItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

//	public static final RegistryObject<Item> JELLY_CAT_SPAWN_EGG = registerEgg("jelly_cat_spawn_egg", TCRModEntities.JELLY_CAT);
	public static final RegistryObject<Item> JELLY_CAT_SPAWN_EGG = REGISTRY.register("jelly_cat_spawn_egg",
		() -> new ForgeSpawnEggItem(TCRModEntities.JELLY_CAT, 0xD57E36, 0x1D0D00,
			new Item.Properties()));
	public static final RegistryObject<Item> CATNIP = REGISTRY.register("catnip",  () -> new DropItem(new Item.Properties().food(Foods.APPLE)));
	public static final RegistryObject<Item> SQUIRREL_SPAWN_EGG = REGISTRY.register("squirrel_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SQUIRREL, 0xD57E36, 0x1D0D00,
					new Item.Properties()));
	public static final RegistryObject<Item> CRAB_SPAWN_EGG = REGISTRY.register("crab_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.CRAB, 0xD57E36, 0x1D0D00,
					new Item.Properties()));
	public static final RegistryObject<Item> MIDDLE_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("middle_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.MIDDLE_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SMALL_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("small_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SMALL_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> TREE_GUARDIAN_SPAWN_EGG = REGISTRY.register("tree_guardian_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.TREE_GUARDIAN, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SPRITE_SPAWN_EGG = REGISTRY.register("sprite_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SPRITE, 1, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> TIGER_SPAWN_EGG = REGISTRY.register("tiger_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.TIGER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> BOXER_SPAWN_EGG = REGISTRY.register("boxer_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.BOXER, 2, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> BIG_HAMMER_EGG = REGISTRY.register("big_hammer_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.BIG_HAMMER, 3, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SNOW_SWORDMAN_SPAWN_EGG = REGISTRY.register("snow_swordman_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SNOW_SWORDMAN, 26, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SWORD_CONTROLLER_SPAWN_EGG = REGISTRY.register("sword_controller_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SWORD_CONTROLLER, 50, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SECOND_BOSS_SPAWN_EGG = REGISTRY.register("second_boss_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SECOND_BOSS, 800, 50,
					new Item.Properties()));

	public static final RegistryObject<Item> CANG_LAN_SPAWN_EGG = REGISTRY.register("cang_lan_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.CANG_LAN, 6000, 50,
					new Item.Properties()));

	public static final RegistryObject<Item> YGGDRASIL_SPAWN_EGG = REGISTRY.register("yggdrasil_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.YGGDRASIL, 1, 0x1D0D00,
					new Item.Properties()));
	public static final RegistryObject<Item> PASTORAL_PLAIN_VILLAGER_SPAWN_EGG = REGISTRY.register("pastoral_plain_villager_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 0xD99999, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG = REGISTRY.register("pastoral_plain_villager_elder_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 0xD99999, 0x1D0D00,
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
