package com.gaboj1.tcr.item;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.custom.*;
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
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DuelOfTheEndMod.MOD_ID);

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
