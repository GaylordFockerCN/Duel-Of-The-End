package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<Item> DESERT_EAGLE_AMMO = REGISTRY.register("desert_eagle_ammo", () -> new DesertEagleAmmoItem());
	public static final RegistryObject<Item> DESERT_EAGLE = REGISTRY.register("desert_eagle", () -> new DesertEagleItem());
	public static final RegistryObject<Item> DESERT_EAGLE_BULLET = REGISTRY.register("desert_eagle_bullet", () -> new DesertEagleBulletItem());

	public static final RegistryObject<Item> BASIC_RESIN = REGISTRY.register("basic_resin", () -> new BasicResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> INTERMEDIATE_RESIN = REGISTRY.register("intermediate_resin", () -> new IntermediateResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> ADVANCED_RESIN = REGISTRY.register("advanced_resin", () -> new AdvancedResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SUPER_RESIN = REGISTRY.register("super_resin", () -> new SuperResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> COPY_RESIN = REGISTRY.register("copy_resin", () -> new CopyResin(new Item.Properties().setNoRepair().stacksTo(64).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> TREE_SPIRIT_WAND = REGISTRY.register("tree_spirit_wand",() ->  new TreeSpiritWand());

	public static final RegistryObject<Item> MIDDLE_TREE_MONSTER_SPAWN_EGG = REGISTRY.register("middle_tree_monster_spawn_egg",
			() -> new ForgeSpawnEggItem(TCRModEntities.MIDDLE_TREE_MONSTER, 0xD57E36, 0x1D0D00,
					new Item.Properties()));

}
