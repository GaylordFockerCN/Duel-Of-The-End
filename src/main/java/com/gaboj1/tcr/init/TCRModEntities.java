package com.gaboj1.tcr.init;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.Yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.Yggdrasil.tree_clawEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.MiddleTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.SmallTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.TreeGuardianEntity;
import com.gaboj1.tcr.entity.custom.villager.PastoralPlainTalkableVillager1;
import com.gaboj1.tcr.entity.custom.villager.PastoralPlainVillager1;
import com.gaboj1.tcr.entity.custom.villager.PastoralPlainVillagerElder;
import com.gaboj1.tcr.entity.custom.*;

import com.gaboj1.tcr.entity.custom.villager.PastoralPlainVillager;
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
			.setCustomClientFactory(DesertEagleBulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

	public static final RegistryObject<EntityType<SmallTreeMonsterEntity>> SMALL_TREE_MONSTER =
			REGISTRY.register("small_tree_monster",
					() -> EntityType.Builder.of(SmallTreeMonsterEntity::new, MobCategory.CREATURE)
							.sized(1f, 1.17f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "small_tree_monster").toString()));

	public static final RegistryObject<EntityType<MiddleTreeMonsterEntity>> MIDDLE_TREE_MONSTER =
			REGISTRY.register("middle_tree_monster",
					() -> EntityType.Builder.of(MiddleTreeMonsterEntity::new, MobCategory.CREATURE)
							.sized(3f, 3.5f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "middle_tree_monster").toString()));

	public static final RegistryObject<EntityType<TreeGuardianEntity>> TREE_GUARDIAN =
			REGISTRY.register("tree_guardian",
					() -> EntityType.Builder.of(TreeGuardianEntity::new, MobCategory.CREATURE)
							.sized(1.5f, 1.75f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_guardian").toString()));

	public static final RegistryObject<EntityType<PastoralPlainVillager>> PASTORAL_PLAIN_VILLAGER =
			REGISTRY.register("pastoral_plain_villager",
					() -> EntityType.Builder.of(PastoralPlainVillager::new, MobCategory.CREATURE)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager").toString()));

	public static final RegistryObject<EntityType<PastoralPlainVillager1>> PASTORAL_PLAIN_VILLAGER1 =
			REGISTRY.register("pastoral_plain_villager1",
					() -> EntityType.Builder.of(PastoralPlainVillager1::new, MobCategory.CREATURE)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager1").toString()));

	public static final RegistryObject<EntityType<PastoralPlainTalkableVillager1>> PASTORAL_PLAIN_TALKABLE_VILLAGER_1 =
			REGISTRY.register("pastoral_plain_talkable_villager_1",
					() -> EntityType.Builder.of(PastoralPlainTalkableVillager1::new, MobCategory.CREATURE)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_talkable_villager_1").toString()));

	public static final RegistryObject<EntityType<PastoralPlainVillagerElder>> PASTORAL_PLAIN_VILLAGER_ELDER =
			REGISTRY.register("pastoral_plain_villager_elder",
					() -> EntityType.Builder.of(PastoralPlainVillagerElder::new, MobCategory.CREATURE)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager_elder").toString()));

	public static final RegistryObject<EntityType<YggdrasilEntity>> YGGDRASIL =
			REGISTRY.register("yggdrasil",
					() -> EntityType.Builder.of(YggdrasilEntity::new, MobCategory.CREATURE)
							.sized(1.5f,5f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "yggdrasil").toString()));

	public static final RegistryObject<EntityType<tree_clawEntity>> TREE_CLAW =
			REGISTRY.register("tree_claw",
					() -> EntityType.Builder.of(tree_clawEntity::new, MobCategory.CREATURE)
							.sized(1.5f,5f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_claw").toString()));


	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

}
