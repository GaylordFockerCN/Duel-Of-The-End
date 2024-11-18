package com.gaboj1.tcr.item;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.item.custom.*;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DOTEItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DuelOfTheEndMod.MOD_ID);
	public static final RegistryObject<Item> M_KEY = REGISTRY.register("mkey", () -> new TeleportKeyItem(() -> BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter1(), 225)));//圣堂
	public static final RegistryObject<Item> P_KEY = REGISTRY.register("pkey", () -> new TeleportKeyItem(() -> BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter2(), 225)));//炼狱
	public static final RegistryObject<Item> U_KEY = REGISTRY.register("ukey", () -> new TeleportKeyItem(() -> BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter(), 225)));//终焉
	public static final RegistryObject<Item> ADGRAIN = REGISTRY.register("adgrain", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.LIANG_PIN)));
	public static final RegistryObject<Item> ADVENTURESPAR = REGISTRY.register("adventurespar", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));
	public static final RegistryObject<Item> IMMORTALESSENCE = REGISTRY.register("immortalessence", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));
	public static final RegistryObject<Item> TIESTONEH = REGISTRY.register("tiestoneh",
			() -> new TieStoneArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> TIESTONEC = REGISTRY.register("tiestonec",
			() -> new TieStoneArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> TIESTONEL = REGISTRY.register("tiestonel",
			() -> new TieStoneArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> TIESTONES = REGISTRY.register("tiestones",
			() -> new TieStoneArmorItem(ArmorItem.Type.BOOTS));
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
	public static final RegistryObject<Item> BALMUNG = REGISTRY.register("balmung", ()-> new BalmungItem(Tiers.NETHERITE, 2, -2.4F,(new Item.Properties()).defaultDurability(2777).rarity(DOTERarities.SHEN_ZHEN)));
	public static final RegistryObject<Item> WITHERC = REGISTRY.register("witherc", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.XIAN_PIN)));
	public static final RegistryObject<Item> GOLDEN_DRAGON_HELMET = REGISTRY.register("goldendragon_helmet", () -> new GoldenDragonItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> GOLDEN_DRAGON_CHESTPLATE = REGISTRY.register("goldendragon_chestplate", () -> new GoldenDragonItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> GOLDEN_DRAGON_LEGGINGS = REGISTRY.register("goldendragon_leggings", () -> new GoldenDragonItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> GOLDEN_DRAGON_BOOTS = REGISTRY.register("goldendragon_boots", () -> new GoldenDragonItem(ArmorItem.Type.BOOTS));

}
