package com.p1nero.dote.entity;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.capability.efpatch.*;
import com.p1nero.dote.entity.custom.*;
import com.p1nero.dote.entity.custom.npc.GuideNpc;
import com.p1nero.dote.entity.custom.npc.KnightCommander;
import com.p1nero.dote.entity.custom.npc.ScarletHighPriest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;


@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTEEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DuelOfTheEndMod.MOD_ID);
	public static final RegistryObject<EntityType<BlackHoleEntity>> BLACK_HOLE = register("black_hole",
			EntityType.Builder.of(BlackHoleEntity::new, MobCategory.AMBIENT).sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<FlameCircleEntity>> FLAME_CIRCLE = register("flame_circle",
			EntityType.Builder.<FlameCircleEntity>of(FlameCircleEntity::new, MobCategory.AMBIENT).sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<DOTEZombie>> DOTE_ZOMBIE = register("zombie",
			EntityType.Builder.of(DOTEZombie::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DOTEZombie>> DOTE_ZOMBIE_2 = register("zombie_2",
			EntityType.Builder.of(DOTEZombie::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DOTEPiglin>> DOTE_PIGLIN = register("piglin",
			EntityType.Builder.of(DOTEPiglin::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<StarChaser>> STAR_CHASER = register("star_chaser",
			EntityType.Builder.of(StarChaser::new, MobCategory.CREATURE).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SenbaiDevil>> SENBAI_DEVIL = register("senbai_devil",
			EntityType.Builder.of(SenbaiDevil::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GoldenFlame>> GOLDEN_FLAME = register("golden_flame",
			EntityType.Builder.of(GoldenFlame::new, MobCategory.MONSTER).sized(0.8f, 2.5f));
	public static final RegistryObject<EntityType<TheArbiterOfRadiance>> THE_ARBITER_OF_RADIANCE = register("the_arbiter_of_radiance",
			EntityType.Builder.of(TheArbiterOfRadiance::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ThePyroclasOfPurgatory>> THE_PYROCLAS_OF_PURGATORY = register("the_pyroclas_of_purgatory",
			EntityType.Builder.of(ThePyroclasOfPurgatory::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TheShadowOfTheEnd>> THE_SHADOW_OF_THE_END = register("the_shadow_of_the_end",
			EntityType.Builder.of(TheShadowOfTheEnd::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GuideNpc>> GUIDE_NPC = register("guide_npc",
			EntityType.Builder.of(GuideNpc::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<KnightCommander>> KNIGHT_COMMANDER = register("knight_commander",
			EntityType.Builder.of(KnightCommander::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ScarletHighPriest>> SCARLET_HIGH_PRIEST = register("scarlet_high_priest",
			EntityType.Builder.of(ScarletHighPriest::new, MobCategory.MONSTER).sized(0.6f, 1.8f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(name, () -> entityTypeBuilder.build(new ResourceLocation(DuelOfTheEndMod.MOD_ID, name).toString()));
	}

	/**
	 * setPatch完还需要去绑定Renderer {@link com.p1nero.dote.event.listeners.ClientModEvents#onRenderPatched(PatchedRenderersEvent.Add)}
	 */
	@SubscribeEvent
	public static void setPatch(EntityPatchRegistryEvent event) {
		event.getTypeEntry().put(SENBAI_DEVIL.get(), (entity) -> SenbaiDevilPatch::new);
		event.getTypeEntry().put(GOLDEN_FLAME.get(), (entity) -> GoldenFlamePatch::new);
		event.getTypeEntry().put(DOTE_ZOMBIE.get(), (entity) -> DOTEZombiePatch::new);
		event.getTypeEntry().put(DOTE_PIGLIN.get(), (entity) -> DOTEZombiePatch::new);
		event.getTypeEntry().put(STAR_CHASER.get(), (entity) -> StarChaserPatch::new);
		event.getTypeEntry().put(GUIDE_NPC.get(), (entity) -> ()-> new NPCPatch(()-> Animations.BIPED_IDLE, null, null, null));
		event.getTypeEntry().put(KNIGHT_COMMANDER.get(), (entity) -> ()-> new NPCPatch(()-> WOMAnimations.RUINE_IDLE, null, null, null));
		event.getTypeEntry().put(SCARLET_HIGH_PRIEST.get(), (entity) -> ()-> new NPCPatch(()-> Animations.BIPED_IDLE, null, null, null));
	}

	/**
	 * setArmature完还需要去绑定Renderer {@link com.p1nero.dote.event.listeners.ClientModEvents#onRenderPatched(PatchedRenderersEvent.Add)}
	 */
	@SubscribeEvent
	public static void setArmature(ModelBuildEvent.ArmatureBuild event) {
		Armatures.registerEntityTypeArmature(SENBAI_DEVIL.get(), Armatures.SKELETON);
		Armatures.registerEntityTypeArmature(GOLDEN_FLAME.get(), Armatures.SKELETON);
		Armatures.registerEntityTypeArmature(DOTE_ZOMBIE.get(), Armatures.BIPED);
		Armatures.registerEntityTypeArmature(DOTE_PIGLIN.get(), Armatures.PIGLIN);
		Armatures.registerEntityTypeArmature(STAR_CHASER.get(), Armatures.BIPED);

		Armatures.registerEntityTypeArmature(GUIDE_NPC.get(), Armatures.BIPED);
		Armatures.registerEntityTypeArmature(KNIGHT_COMMANDER.get(), Armatures.BIPED);
		Armatures.registerEntityTypeArmature(SCARLET_HIGH_PRIEST.get(), Armatures.BIPED);
	}

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(SENBAI_DEVIL.get(), SenbaiDevil.setAttributes());
		event.put(GOLDEN_FLAME.get(), GoldenFlame.setAttributes());
		event.put(DOTE_ZOMBIE.get(), DOTEZombie.setAttributes());
		event.put(DOTE_ZOMBIE_2.get(), DOTEZombie.setAttributes());
		event.put(DOTE_PIGLIN.get(), DOTEPiglin.setAttributes());
		event.put(STAR_CHASER.get(), StarChaser.setAttributes());
		event.put(THE_ARBITER_OF_RADIANCE.get(), TheArbiterOfRadiance.setAttributes());
		event.put(THE_PYROCLAS_OF_PURGATORY.get(), ThePyroclasOfPurgatory.setAttributes());
		event.put(THE_SHADOW_OF_THE_END.get(), TheShadowOfTheEnd.setAttributes());
		event.put(GUIDE_NPC.get(), GuideNpc.setAttributes());
		event.put(KNIGHT_COMMANDER.get(), KnightCommander.setAttributes());
		event.put(SCARLET_HIGH_PRIEST.get(), ScarletHighPriest.setAttributes());
	}

	@SubscribeEvent
	public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
		event.register(DOTE_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				DOTEZombie::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(DOTE_ZOMBIE_2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				DOTEZombie::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(DOTE_PIGLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				DOTEZombie::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(STAR_CHASER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				StarChaser::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(GUIDE_NPC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				GuideNpc::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

}
