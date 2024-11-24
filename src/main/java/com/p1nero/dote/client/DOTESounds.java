package com.p1nero.dote.client;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class DOTESounds {

	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DuelOfTheEndMod.MOD_ID);
	public static final RegistryObject<SoundEvent> LOTUSHEAL = createEvent("bgm.dote.lotusheal");
	public static final RegistryObject<SoundEvent> DODGE = createEvent("skill.dote.dodge");
	public static final RegistryObject<SoundEvent> SENBAI_BGM = createEvent("bgm.dote.senbai_fight");
	public static final RegistryObject<SoundEvent> GOLDEN_FLAME_BGM = createEvent("bgm.dote.goldenflame_fight");
	public static final RegistryObject<SoundEvent> BIOME_BGM = createEvent("bgm.dote.biome_bgm");
	public static final RegistryObject<SoundEvent> BOSS_FIGHT1 = createEvent("bgm.dote.boss_fight1");
	public static final RegistryObject<SoundEvent> BOSS_FIGHT2 = createEvent("bgm.dote.boss_fight2");
	private static RegistryObject<SoundEvent> createEvent(String sound) {
		return REGISTRY.register(sound, () -> SoundEvent.createVariableRangeEvent(DuelOfTheEndMod.prefix(sound)));
	}
}
