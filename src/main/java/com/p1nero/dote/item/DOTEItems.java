package com.p1nero.dote.item;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.item.custom.*;
import com.p1nero.dote.worldgen.biome.BiomeMap;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DOTEItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DuelOfTheEndMod.MOD_ID);
	public static final RegistryObject<Item> M_KEY = REGISTRY.register("mkey", () -> new MKeyItem(() -> BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter1(), 170).offset(0, 0, 400)));//圣堂
	public static final RegistryObject<Item> P_KEY = REGISTRY.register("pkey", () -> new TeleportKeyItem(() -> BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter2(), 170).offset(0, 0, -400)));//炼狱
	public static final RegistryObject<Item> U_KEY = REGISTRY.register("ukey", () -> new TeleportKeyItem(() -> BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter(), 170)));//终焉
	public static final RegistryObject<Item> ADGRAIN = REGISTRY.register("adgrain", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.LIANG_PIN)));
	public static final RegistryObject<Item> ADVENTURESPAR = REGISTRY.register("adventurespar", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));
	public static final RegistryObject<Item> IMMORTALESSENCE = REGISTRY.register("immortalessence", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));
	public static final RegistryObject<Item> HOLY_RADIANCE_SEED = REGISTRY.register("holy_radiance_seed", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));
	public static final RegistryObject<Item> CORE_OF_HELL = REGISTRY.register("core_of_hell", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.TE_PIN)));
	public static final RegistryObject<Item> BOOK_OF_ENDING = REGISTRY.register("book_of_ending", () -> new BookOfEndingItem(new Item.Properties().fireResistant().rarity(DOTERarities.XIAN_PIN)));
	public static final RegistryObject<Item> TIESTONEH = REGISTRY.register("tiestoneh",
			() -> new TieStoneArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> TIESTONEC = REGISTRY.register("tiestonec",
			() -> new TieStoneArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> TIESTONEL = REGISTRY.register("tiestonel",
			() -> new TieStoneArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> TIESTONES = REGISTRY.register("tiestones",
			() -> new TieStoneArmorItem(ArmorItem.Type.BOOTS));
	public static final RegistryObject<Item> WKNIGHT_INGOT = REGISTRY.register("wknight_ingot", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));
	public static final RegistryObject<Item> WKNIGHT_HELMET = REGISTRY.register("whiteknight_helmet",
			() -> new WKnightArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> WKNIGHT_CHESTPLATE = REGISTRY.register("whiteknight_chestplate",
			() -> new WKnightArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> WKNIGHT_LEGGINGS = REGISTRY.register("whiteknight_leggings",
			() -> new WKnightArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> WKNIGHT_BOOTS = REGISTRY.register("whiteknight_boots",
			() -> new WKnightArmorItem(ArmorItem.Type.BOOTS));
	public static final RegistryObject<Item> NETHERROT_INGOT = REGISTRY.register("netherrot_ingot", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.TE_PIN)));
	public static final RegistryObject<Item> NETHERITESS = REGISTRY.register("netheritess", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.TE_PIN)));
	public static final RegistryObject<Item> NETHERITEROT_HELMET = REGISTRY.register("netheriterot_helmet",
			() -> new NetherRotArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> NETHERITEROT_CHESTPLATE = REGISTRY.register("netheriterot_chestplate",
			() -> new NetherRotArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> NETHERITEROT_LEGGINGS = REGISTRY.register("netheriterot_leggings",
			() -> new NetherRotArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> NETHERITEROT_BOOTS = REGISTRY.register("netheriterot_boots",
			() -> new NetherRotArmorItem(ArmorItem.Type.BOOTS));
	public static final RegistryObject<Item> BALMUNG = REGISTRY.register("balmung", ()-> new BalmungItem(Tiers.NETHERITE, -3, -2.4F,(new Item.Properties()).defaultDurability(2777).rarity(DOTERarities.SHEN_ZHEN)));
	public static final RegistryObject<Item> WITHERC = REGISTRY.register("witherc", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.XIAN_PIN)));
	public static final RegistryObject<Item> DRAGONSTEEL_INGOT = REGISTRY.register("dragonsteel_ingot", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHEN_ZHEN)));
	public static final RegistryObject<Item> GOLDEN_DRAGON_HELMET = REGISTRY.register("goldendragon_helmet", () -> new GoldenDragonItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> GOLDEN_DRAGON_CHESTPLATE = REGISTRY.register("goldendragon_chestplate", () -> new GoldenDragonItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> GOLDEN_DRAGON_LEGGINGS = REGISTRY.register("goldendragon_leggings", () -> new GoldenDragonItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> GOLDEN_DRAGON_BOOTS = REGISTRY.register("goldendragon_boots", () -> new GoldenDragonItem(ArmorItem.Type.BOOTS));

}
