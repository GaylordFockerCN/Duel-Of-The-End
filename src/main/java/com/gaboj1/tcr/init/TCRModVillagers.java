package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, TheCasketOfReveriesMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, TheCasketOfReveriesMod.MOD_ID);

    //找个不可能的方块，让他不能自然生成。
    public static final RegistryObject<PoiType> TCR_MERCHANT_POI = POI_TYPES.register("tcr_merchant_poi",
            () -> new PoiType(ImmutableSet.copyOf(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get().getStateDefinition().getPossibleStates()),
                    5, 5));

    public static final RegistryObject<VillagerProfession> TCR_MERCHANT =
            VILLAGER_PROFESSIONS.register("tcr_merchant", () -> new VillagerProfession("tcr_merchant",
                    holder -> holder.get() == TCR_MERCHANT_POI.get(), holder -> holder.get() == TCR_MERCHANT_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));



    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}