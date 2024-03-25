package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.*;
import com.gaboj1.tcr.item.custom.boss_loot.HolySword;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<Item> DESERT_EAGLE_AMMO = REGISTRY.register("desert_eagle_ammo", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> DESERT_EAGLE = REGISTRY.register("desert_eagle", () -> new DesertEagleItem());
	public static final RegistryObject<Item> DESERT_EAGLE_BULLET = REGISTRY.register("desert_eagle_bullet", () -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> BASIC_RESIN = REGISTRY.register("basic_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.COMMON),1));
	public static final RegistryObject<Item> INTERMEDIATE_RESIN = REGISTRY.register("intermediate_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.UNCOMMON),BasicResin.INTERMEDIATE_RESIN));
	public static final RegistryObject<Item> ADVANCED_RESIN = REGISTRY.register("advanced_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.RARE),BasicResin.ADVANCED_RESIN));
	public static final RegistryObject<Item> SUPER_RESIN = REGISTRY.register("super_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.EPIC),BasicResin.SUPER_RESIN));
	public static final RegistryObject<Item> COPY_RESIN = REGISTRY.register("copy_resin", () -> new CopyResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> TREE_SPIRIT_WAND = REGISTRY.register("tree_spirit_wand",() ->  new TreeSpiritWand());
	public static final RegistryObject<Item> TREE_DEMON_HORN = REGISTRY.register("tree_demon_horn",() ->  new DropItem());
	public static final RegistryObject<Item> ELDER_STAFF = REGISTRY.register("elder_staff",() ->  new DropItem());
	public static final RegistryObject<Item> HOLY_SWORD = REGISTRY.register("holy_sword",() -> new HolySword());

	public static final RegistryObject<Item> BLUE_BANANA = REGISTRY.register("blue_banana",() ->  new DropItem(new Item.Properties().food(TCRFoods.BLUE_BANANA)));
	public static final RegistryObject<Item> DREAM_DA = REGISTRY.register("dream_da",() ->  new DrinkItem(new Item.Properties().food(TCRFoods.DREAM_DA)));
	public static final RegistryObject<Item> BOOK1 = REGISTRY.register("book1",() ->  new Book("book1"));

	public static final RegistryObject<Item> BOOK2 = REGISTRY.register("book2",() ->  new Book("book2"));

	public static final RegistryObject<Item> MIDDLE_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("middle_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.MIDDLE_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> SMALL_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("small_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.SMALL_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> TREE_GUARDIAN_SPAWN_EGG = REGISTRY.register("tree_guardian_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.TREE_GUARDIAN, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> PASTORAL_PLAIN_VILLAGER_SPAWN_EGG = REGISTRY.register("pastoral_plain_villager_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

	public static final RegistryObject<Item> PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG = REGISTRY.register("pastoral_plain_villager_elder_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));


}
