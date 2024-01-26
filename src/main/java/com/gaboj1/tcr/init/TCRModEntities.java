package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveries;
import com.gaboj1.tcr.entity.DesertEagleBulletEntity;

import com.gaboj1.tcr.entity.custom.TigerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheCasketOfReveries.MOD_ID);
	public static final RegistryObject<EntityType<DesertEagleBulletEntity>> DESERT_EAGLE_BULLET = register("projectile_desert_eagle_bullet", EntityType.Builder.<DesertEagleBulletEntity>of(DesertEagleBulletEntity::new, MobCategory.MISC)
			.setCustomClientFactory(DesertEagleBulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

	public static final RegistryObject<EntityType<TigerEntity>> TIGER =
			REGISTRY.register("tiger",
					() -> EntityType.Builder.of(TigerEntity::new, MobCategory.CREATURE)
							.sized(1.5f, 1.75f)
							.build(new ResourceLocation(TheCasketOfReveries.MOD_ID, "tiger").toString()));


	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
	}
}
