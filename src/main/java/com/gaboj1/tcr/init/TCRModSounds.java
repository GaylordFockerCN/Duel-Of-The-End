package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TCRModSounds {

	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<SoundEvent> DESERT_EAGLE_FIRE = createEvent("item.tcr.desert_eagle_fire");
	public static final RegistryObject<SoundEvent> DESERT_EAGLE_RELOAD = createEvent("item.tcr.desert_eagle_reload");
	public static final RegistryObject<SoundEvent> TREE_MONSTERS_DEATH = createEvent("entity.tcr.tree_monsters_death");
	public static final RegistryObject<SoundEvent> TREE_MONSTERS_HURT = createEvent("entity.tcr.tree_monsters_hurt");
	public static final RegistryObject<SoundEvent> BOSS_ONE_AMBIENT_SOUND = createEvent("entity.tcr.first_boss_ambient_sound");

	private static RegistryObject<SoundEvent> createEvent(String sound) {
		return REGISTRY.register(sound, () -> SoundEvent.createVariableRangeEvent(TheCasketOfReveriesMod.prefix(sound)));
	}

}
