package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
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
	public static final RegistryObject<SoundEvent> YGGDRASIL_AMBIENT_SOUND = createEvent("entity.tcr.first_boss_ambient_sound");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HELLO = createEvent("entity.tcr.female_villager_hello");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_EI = createEvent("entity.tcr.female_villager_ei");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HI = createEvent("entity.tcr.female_villager_hi");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HI_THERE = createEvent("entity.tcr.female_villager_hi_there");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HENG = createEvent("entity.tcr.female_villager_heng");
	public static final RegistryObject<SoundEvent> JELLY_CAT_AMBIENT = createEvent("entity.jelly_cat.ambient");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_DEATH = createEvent("entity.tcr.female_villager_die");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HURT = createEvent("entity.tcr.female_villager_hurt");
	public static final RegistryObject<SoundEvent> JELLY_CAT_DEATH = createEvent("entity.jelly_cat.die");
	public static final RegistryObject<SoundEvent> JELLY_CAT_HURT = createEvent("entity.jelly_cat.hurt");
	private static RegistryObject<SoundEvent> createEvent(String sound) {
		return REGISTRY.register(sound, () -> SoundEvent.createVariableRangeEvent(TheCasketOfReveriesMod.prefix(sound)));
	}

}
