package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class TCRModItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<CreativeModeTab> MODE_TAB = REGISTRY.register(TheCasketOfReveriesMod.MOD_ID,
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.the_casket_of_reveries."+ TheCasketOfReveriesMod.MOD_ID)).icon(() -> new ItemStack(TCRModItems.DESERT_EAGLE.get())).displayItems((parameters, tabData) -> {
				tabData.accept(TCRModItems.DESERT_EAGLE_AMMO.get());
				tabData.accept(TCRModItems.DESERT_EAGLE.get());
				tabData.accept(TCRModItems.TIGER_SPAWN_EGG.get());
				tabData.accept(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get());
			}).build());
}
