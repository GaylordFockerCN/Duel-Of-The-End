package com.gaboj1.tcr.client;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class DOTESounds {

	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DuelOfTheEndMod.MOD_ID);
	public static final RegistryObject<SoundEvent> LOTUSHEAL = createEvent("bgm.dote.lotusheal");
	public static final RegistryObject<SoundEvent> SENBAI_BGM = createEvent("bgm.dote.senbai_fight");
	public static final RegistryObject<SoundEvent> GOLDEN_FLAME_BGM = createEvent("bgm.dote.goldenflame_fight");
	private static RegistryObject<SoundEvent> createEvent(String sound) {
		return REGISTRY.register(sound, () -> SoundEvent.createVariableRangeEvent(DuelOfTheEndMod.prefix(sound)));
	}

}
