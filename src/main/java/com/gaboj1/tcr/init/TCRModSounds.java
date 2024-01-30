package com.gaboj1.tcr.init;


import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

public class TCRModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<SoundEvent> DESERT_EAGLE_FIRE = REGISTRY.register("deserteaglecrcfire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "deserteaglecrcfire")));
	public static final RegistryObject<SoundEvent> DESERT_EAGLE_RELOAD = REGISTRY.register("deserteaglecrcreload", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "deserteaglecrcreload")));
}
