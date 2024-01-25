package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveries;

import net.minecraft.client.multiplayer.ClientRegistryLayer;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class TCRModItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCasketOfReveries.MOD_ID);
	public static final RegistryObject<CreativeModeTab> SIMPLE_DESERT_EAGLE = REGISTRY.register(TheCasketOfReveries.MOD_ID,
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.the_casket_of_reveries."+TheCasketOfReveries.MOD_ID)).icon(() -> new ItemStack(TCRModItems.DESERT_EAGLE.get())).displayItems((parameters, tabData) -> {
				tabData.accept(TCRModItems.DESERT_EAGLE_AMMO.get());
				tabData.accept(TCRModItems.DESERT_EAGLE.get());
				tabData.accept(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get());
			}).build());
}
