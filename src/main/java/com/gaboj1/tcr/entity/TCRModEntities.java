package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.TreeClawEntity;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.dreamspirit.JellyCat;
import com.gaboj1.tcr.entity.custom.dreamspirit.Squirrel;
import com.gaboj1.tcr.entity.custom.sword.RainCutterSwordEntity;
import com.gaboj1.tcr.entity.custom.sword.RainScreenSwordEntity;
import com.gaboj1.tcr.entity.custom.sword.SwordEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.MiddleTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.SmallTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.TreeGuardianEntity;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainStationaryVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.entity.custom.*;

import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

public class TCRModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<EntityType<DesertEagleBulletEntity>> DESERT_EAGLE_BULLET = register("projectile_desert_eagle_bullet", EntityType.Builder.<DesertEagleBulletEntity>of(DesertEagleBulletEntity::new, MobCategory.MISC)
			.setCustomClientFactory(DesertEagleBulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.1f, 0.1f));

	public static final RegistryObject<EntityType<SwordEntity>> SWORD = register("sword",
			EntityType.Builder.of(SwordEntity::new, MobCategory.CREATURE));


	public static final RegistryObject<EntityType<RainScreenSwordEntity>> RAIN_SCREEN_SWORD = register("rain_screen_sword",
			EntityType.Builder.of(RainScreenSwordEntity::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<RainCutterSwordEntity>> RAIN_CUTTER_SWORD = register("rain_cutter_sword", EntityType.Builder.<RainCutterSwordEntity>of(RainCutterSwordEntity::new, MobCategory.MISC)
			.setCustomClientFactory(RainCutterSwordEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1f, 0.2f));


	public static final RegistryObject<EntityType<SmallTreeMonsterEntity>> SMALL_TREE_MONSTER =
			REGISTRY.register("small_tree_monster",
					() -> EntityType.Builder.of(SmallTreeMonsterEntity::new, MobCategory.CREATURE)
							.sized(0.4f, 0.4f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "small_tree_monster").toString()));

	public static final RegistryObject<EntityType<MiddleTreeMonsterEntity>> MIDDLE_TREE_MONSTER =
			REGISTRY.register("middle_tree_monster",
					() -> EntityType.Builder.of(MiddleTreeMonsterEntity::new, MobCategory.CREATURE)
							.sized(1f, 1.8f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "middle_tree_monster").toString()));

	public static final RegistryObject<EntityType<TreeGuardianEntity>> TREE_GUARDIAN =
			REGISTRY.register("tree_guardian",
					() -> EntityType.Builder.of(TreeGuardianEntity::new, MobCategory.CREATURE)
							.sized(0.78f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_guardian").toString()));

	public static final RegistryObject<EntityType<JellyCat>> JELLY_CAT = register("jelly_cat",
			EntityType.Builder.of(JellyCat::new, MobCategory.CREATURE).sized(2.0f,1.75f));
	public static final RegistryObject<EntityType<Squirrel>> SQUIRREL = register("squirrel",
			EntityType.Builder.of(Squirrel::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<PastoralPlainVillager>> PASTORAL_PLAIN_VILLAGER = register("pastoral_plain_villager",
			EntityType.Builder.of(PastoralPlainVillager::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<PastoralPlainTalkableVillager>> PASTORAL_PLAIN_TALKABLE_VILLAGER = register("pastoral_plain_talkable_villager",
			EntityType.Builder.of(PastoralPlainTalkableVillager::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<PastoralPlainStationaryVillager>> PASTORAL_PLAIN_STATIONARY_VILLAGER = register("pastoral_plain_stationary_villager",
			EntityType.Builder.of(PastoralPlainStationaryVillager::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<PastoralPlainVillagerElder>> PASTORAL_PLAIN_VILLAGER_ELDER = register("pastoral_plain_villager_elder",
			EntityType.Builder.of(PastoralPlainVillagerElder::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<YggdrasilEntity>> YGGDRASIL =
			REGISTRY.register("yggdrasil",
					() -> EntityType.Builder.of(YggdrasilEntity::new, MobCategory.CREATURE)
							.sized(1f,3f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "yggdrasil").toString()));

	public static final RegistryObject<EntityType<TreeClawEntity>> TREE_CLAW =
			REGISTRY.register("tree_claw",
					() -> EntityType.Builder.<TreeClawEntity>of(TreeClawEntity::new, MobCategory.CREATURE)
							.sized(1.5f,5f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_claw").toString()));


	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> entityTypeBuilder.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, registryname).toString()));
	}

}
