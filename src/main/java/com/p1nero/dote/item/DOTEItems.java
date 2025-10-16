package com.p1nero.dote.item;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.item.custom.DuelKeyItem;
import com.p1nero.dote.item.custom.GoldenDragonArmorItem;
import com.p1nero.dote.item.custom.SimpleDescriptionFoilItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DOTEItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DuelOfTheEndMod.MOD_ID);

	public static final RegistryObject<Item> IMMORTALESSENCE = REGISTRY.register("immortalessence", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));

	public static final RegistryObject<Item> GOLDEN_DRAGON_HELMET = REGISTRY.register("goldendragon_helmet", () -> new GoldenDragonArmorItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> GOLDEN_DRAGON_CHESTPLATE = REGISTRY.register("goldendragon_chestplate", () -> new GoldenDragonArmorItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> GOLDEN_DRAGON_LEGGINGS = REGISTRY.register("goldendragon_leggings", () -> new GoldenDragonArmorItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> GOLDEN_DRAGON_BOOTS = REGISTRY.register("goldendragon_boots", () -> new GoldenDragonArmorItem(ArmorItem.Type.BOOTS));

	public static final RegistryObject<Item> DUEL_KEY = REGISTRY.register("duel_key", DuelKeyItem::new);

}
