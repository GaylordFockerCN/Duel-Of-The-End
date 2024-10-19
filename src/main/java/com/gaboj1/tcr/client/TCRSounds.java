package com.gaboj1.tcr.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TCRSounds {

	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<SoundEvent> PIPA = createEvent("item.tcr.pipa");
	public static final RegistryObject<SoundEvent> DESERT_EAGLE_FIRE = createEvent("item.tcr.desert_eagle_fire");
	public static final RegistryObject<SoundEvent> DESERT_EAGLE_RELOAD = createEvent("item.tcr.desert_eagle_reload");
	public static final RegistryObject<SoundEvent> TREE_MONSTERS_DEATH = createEvent("entity.tcr.tree_monsters_death");
	public static final RegistryObject<SoundEvent> TREE_MONSTERS_HURT = createEvent("entity.tcr.tree_monsters_hurt");
	public static final RegistryObject<SoundEvent> YGGDRASIL_AMBIENT_SOUND = createEvent("entity.tcr.first_boss_ambient_sound");
	public static final RegistryObject<SoundEvent> YGGDRASIL_ATTACK_ONE = createEvent("entity.tcr.first_boss_attack1");
	public static final RegistryObject<SoundEvent> YGGDRASIL_ATTACK_TWO = createEvent("entity.tcr.first_boss_attack2");
	public static final RegistryObject<SoundEvent> YGGDRASIL_CRY = createEvent("entity.tcr.first_boss_cry");
	public static final RegistryObject<SoundEvent> YGGDRASIL_RECOVER_LAUGHTER = createEvent("entity.tcr.first_boss_recover_laughter");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HELLO = createEvent("entity.tcr.female_villager_hello");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_EI = createEvent("entity.tcr.female_villager_ei");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HI = createEvent("entity.tcr.female_villager_hi");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HI_THERE = createEvent("entity.tcr.female_villager_hi_there");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HENG = createEvent("entity.tcr.female_villager_heng");
	public static final RegistryObject<SoundEvent> JELLY_CAT_AMBIENT = createEvent("entity.jelly_cat.ambient");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_DEATH = createEvent("entity.tcr.female_villager_die");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_HURT = createEvent("entity.tcr.female_villager_hurt");
	public static final RegistryObject<SoundEvent> FEMALE_VILLAGER_OHAYO = createEvent("entity.tcr.female_villager_ohayo");
	public static final RegistryObject<SoundEvent> FEMALE_HENG = createEvent("entity.tcr.female_villager.female_heng");
	public static final RegistryObject<SoundEvent> FEMALE_SIGH = createEvent("entity.tcr.female_villager.female_sigh");
	public static final RegistryObject<SoundEvent> JELLY_CAT_DEATH = createEvent("entity.tcr.jelly_cat.die");
	public static final RegistryObject<SoundEvent> JELLY_CAT_HURT = createEvent("entity.tcr.jelly_cat.hurt");
	public static final RegistryObject<SoundEvent> MALE_DEATH = createEvent("entity.tcr.male_villager.male_death");
	public static final RegistryObject<SoundEvent> MALE_EYO = createEvent("entity.tcr.male_villager.male_eyo");
	public static final RegistryObject<SoundEvent> MALE_GET_HURT = createEvent("entity.tcr.male_villager.male_get_hurt");
	public static final RegistryObject<SoundEvent> MALE_HELLO = createEvent("entity.tcr.male_villager.male_hello");
	public static final RegistryObject<SoundEvent> MALE_HENG = createEvent("entity.tcr.male_villager.male_heng");
	public static final RegistryObject<SoundEvent> MALE_HI = createEvent("entity.tcr.male_villager.male_hi");
	public static final RegistryObject<SoundEvent> MALE_SIGH = createEvent("entity.tcr.male_villager.male_sigh");
	public static final RegistryObject<SoundEvent> UNKNOWN_AMBIENT = createEvent("entity.tcr.unknown.ambient");

	//BGM
	public static final RegistryObject<SoundEvent> BIOME1VILLAGE = createEvent("bgm.tcr.biome1.plain");
	public static final RegistryObject<SoundEvent> BIOME1FOREST = createEvent("bgm.tcr.biome1.forest");
	public static final RegistryObject<SoundEvent> BIOME1BOSS_FIGHT = createEvent("bgm.tcr.biome1.boss_fight");

	private static RegistryObject<SoundEvent> createEvent(String sound) {
		return REGISTRY.register(sound, () -> SoundEvent.createVariableRangeEvent(TheCasketOfReveriesMod.prefix(sound)));
	}

}
