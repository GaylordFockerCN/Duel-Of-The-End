package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveries;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class TCRModItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCasketOfReveries.MOD_ID);
	public static final RegistryObject<CreativeModeTab> SIMPLE_DESERT_EAGLE = REGISTRY.register("holy_ding_legend",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.simpledeserteagle.simple_desert_eagle")).icon(() -> new ItemStack(TCRModItems.DESERT_EAGLE.get())).displayItems((parameters, tabData) -> {
				tabData.accept(TCRModItems.DESERT_EAGLE_AMMO.get());
				tabData.accept(TCRModItems.DESERT_EAGLE.get());
			}).build());
}
