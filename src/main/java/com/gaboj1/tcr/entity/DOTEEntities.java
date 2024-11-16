package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.entity.custom.SenbaiDevil;
import com.gaboj1.tcr.entity.custom.SenbaiDevilPatch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
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

	public static final RegistryObject<EntityType<TCRFakePlayer>> FAKE_PLAYER = register("fake_player",
			EntityType.Builder.of(TCRFakePlayer::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<SenbaiDevil>> SENBAI_DEVIL = register("senbai_devil",
			EntityType.Builder.of(SenbaiDevil::new, MobCategory.MONSTER).sized(0.6f, 1.8f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(name, () -> entityTypeBuilder.build(new ResourceLocation(DuelOfTheEndMod.MOD_ID, name).toString()));
	}

	@SubscribeEvent
	public static void setPatch(EntityPatchRegistryEvent event) {
		event.getTypeEntry().put(SENBAI_DEVIL.get(), (entity) -> SenbaiDevilPatch::new);
	}

	@SubscribeEvent
	public static void setArmature(ModelBuildEvent.ArmatureBuild event) {
		Armatures.registerEntityTypeArmature(SENBAI_DEVIL.get(), Armatures.SKELETON);
	}

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(FAKE_PLAYER.get(), TCRFakePlayer.setAttributes());
		event.put(SENBAI_DEVIL.get(), SenbaiDevil.setAttributes());
	}

	@SubscribeEvent
	public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {

	}

}
