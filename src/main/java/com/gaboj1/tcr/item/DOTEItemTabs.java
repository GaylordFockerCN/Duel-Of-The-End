package com.gaboj1.tcr.item;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.DOTEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DOTEItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DuelOfTheEndMod.MOD_ID);

	public static final RegistryObject<CreativeModeTab> ALL = REGISTRY.register("all",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("item_group.duel_of_the_end.all"))
					.icon(() -> new ItemStack(DOTEItems.IMMORTALESSENCE.get()))
					.displayItems((parameters, tabData) -> {
						tabData.accept(DOTEItems.P_KEY.get());
						tabData.accept(DOTEItems.M_KEY.get());
						tabData.accept(DOTEItems.U_KEY.get());
						tabData.accept(DOTEItems.ADGRAIN.get());
						tabData.accept(DOTEItems.ADVENTURESPAR.get());
						tabData.accept(DOTEItems.IMMORTALESSENCE.get());
						tabData.accept(DOTEItems.TIESTONEH.get());
						tabData.accept(DOTEItems.TIESTONEC.get());
						tabData.accept(DOTEItems.TIESTONEL.get());
						tabData.accept(DOTEItems.TIESTONES.get());
						tabData.accept(DOTEItems.NETHERITESS.get());
						tabData.accept(DOTEItems.NETHERITEROT_HELMET.get());
						tabData.accept(DOTEItems.NETHERITEROT_CHESTPLATE.get());
						tabData.accept(DOTEItems.NETHERITEROT_LEGGINGS.get());
						tabData.accept(DOTEItems.NETHERITEROT_BOOTS.get());
						tabData.accept(DOTEItems.BALMUNG.get());
						tabData.accept(DOTEItems.WITHERC.get());
						tabData.accept(DOTEItems.GOLDEN_DRAGON_HELMET.get());
						tabData.accept(DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get());
						tabData.accept(DOTEItems.GOLDEN_DRAGON_LEGGINGS.get());
						tabData.accept(DOTEItems.GOLDEN_DRAGON_BOOTS.get());
						tabData.accept(DOTEBlocks.SENBAI_SPAWNER.get());
						tabData.accept(DOTEBlocks.GOLDEN_FLAME_SPAWNER.get());
					}).build());

}
