package com.gaboj1.tcr.item;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.TCRBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TCRItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DuelOfTheEndMod.MOD_ID);

	public static final RegistryObject<CreativeModeTab> ALL = REGISTRY.register("all",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("item_group.duel_of_the_end.all"))
					.icon(() -> new ItemStack(Items.AIR))
					.displayItems((parameters, tabData) -> {

					}).build());

}
