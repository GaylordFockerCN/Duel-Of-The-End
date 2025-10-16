package com.p1nero.dote.item;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.DOTEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DOTEItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DuelOfTheEndMod.MOD_ID);

	public static final RegistryObject<CreativeModeTab> DIMENSION = REGISTRY.register("all",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("item_group.duel_of_the_end.all"))
					.icon(() -> new ItemStack(DOTEItems.DUEL_KEY.get()))
					.displayItems((parameters, tabData) -> {
						tabData.accept(DOTEItems.IMMORTALESSENCE.get());
						tabData.accept(DOTEBlocks.M_BLOCK.get());
						tabData.accept(DOTEBlocks.P_BLOCK.get());
						tabData.accept(DOTEBlocks.U_BLOCK.get());
						tabData.accept(DOTEBlocks.SENBAI_SPAWNER.get());
						tabData.accept(DOTEBlocks.GOLDEN_FLAME_SPAWNER.get());
						tabData.accept(DOTEBlocks.SAND_CAPTAIN_SPAWNER.asStack(1));
						tabData.accept(DOTEBlocks.MS_ABYSS_SPAWNER.asStack(1));
						tabData.accept(DOTEItems.DUEL_KEY.get());
					}).build());

}
