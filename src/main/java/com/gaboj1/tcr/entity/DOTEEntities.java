package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.gameasset.Armatures;


@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTEEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DuelOfTheEndMod.MOD_ID);
	public static final RegistryObject<EntityType<DOTEZombie>> DOTE_ZOMBIE = register("zombie",
			EntityType.Builder.of(DOTEZombie::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DOTEPiglin>> DOTE_PIGLIN = register("piglin",
			EntityType.Builder.of(DOTEPiglin::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<StarChaser>> STAR_CHASER = register("star_chaser",
			EntityType.Builder.of(StarChaser::new, MobCategory.CREATURE).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SenbaiDevil>> SENBAI_DEVIL = register("senbai_devil",
			EntityType.Builder.of(SenbaiDevil::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GoldenFlame>> GOLDEN_FLAME = register("golden_flame",
			EntityType.Builder.of(GoldenFlame::new, MobCategory.MONSTER).sized(0.6f, 1.8f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(name, () -> entityTypeBuilder.build(new ResourceLocation(DuelOfTheEndMod.MOD_ID, name).toString()));
	}

	@SubscribeEvent
	public static void setPatch(EntityPatchRegistryEvent event) {
		event.getTypeEntry().put(SENBAI_DEVIL.get(), (entity) -> SenbaiDevilPatch::new);
		event.getTypeEntry().put(GOLDEN_FLAME.get(), (entity) -> GoldenFlamePatch::new);
		event.getTypeEntry().put(DOTE_ZOMBIE.get(), (entity) -> DOTEZombiePatch::new);
		event.getTypeEntry().put(DOTE_PIGLIN.get(), (entity) -> DOTEZombiePatch::new);
		event.getTypeEntry().put(STAR_CHASER.get(), (entity) -> StarChaserPatch::new);
	}

	@SubscribeEvent
	public static void setArmature(ModelBuildEvent.ArmatureBuild event) {
		Armatures.registerEntityTypeArmature(SENBAI_DEVIL.get(), Armatures.SKELETON);
		Armatures.registerEntityTypeArmature(GOLDEN_FLAME.get(), Armatures.SKELETON);
		Armatures.registerEntityTypeArmature(DOTE_ZOMBIE.get(), Armatures.BIPED);
		Armatures.registerEntityTypeArmature(DOTE_PIGLIN.get(), Armatures.PIGLIN);
		Armatures.registerEntityTypeArmature(STAR_CHASER.get(), Armatures.BIPED);
	}

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(SENBAI_DEVIL.get(), SenbaiDevil.setAttributes());
		event.put(GOLDEN_FLAME.get(), GoldenFlame.setAttributes());
		event.put(DOTE_ZOMBIE.get(), DOTEZombie.setAttributes());
		event.put(DOTE_PIGLIN.get(), DOTEPiglin.setAttributes());
		event.put(STAR_CHASER.get(), StarChaser.setAttributes());
	}

	@SubscribeEvent
	public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
		event.register(DOTE_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				DOTEZombie::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(DOTE_PIGLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				DOTEPiglin::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(STAR_CHASER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				StarChaser::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

}
