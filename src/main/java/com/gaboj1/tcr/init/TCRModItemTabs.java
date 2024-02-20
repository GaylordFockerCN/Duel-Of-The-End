package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TCRModItemTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<CreativeModeTab> MODE_TAB = REGISTRY.register(TheCasketOfReveriesMod.MOD_ID,
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.the_casket_of_reveries."+ TheCasketOfReveriesMod.MOD_ID)).icon(() -> new ItemStack(TCRModBlocks.PORTAL_BED.get())).displayItems((parameters, tabData) -> {
				tabData.accept(TCRModItems.DESERT_EAGLE_AMMO.get());
				tabData.accept(TCRModItems.DESERT_EAGLE.get());

				tabData.accept(TCRModItems.TIGER_SPAWN_EGG.get());

				tabData.accept(TCRModItems.BASIC_RESIN.get());
				tabData.accept(TCRModItems.INTERMEDIATE_RESIN.get());
				tabData.accept(TCRModItems.ADVANCED_RESIN.get());
				tabData.accept(TCRModItems.SUPER_RESIN.get());
				tabData.accept(TCRModItems.COPY_RESIN.get());

				tabData.accept(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get());
				tabData.accept(TCRModBlocks.PORTAL_BED.get());

				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get());

				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get());
				tabData.accept(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get());
				tabData.accept(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get());


				tabData.accept(TCRModItems.TREE_SPIRIT_WAND.get());

			}).build());
}
