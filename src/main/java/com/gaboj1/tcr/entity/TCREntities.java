package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.*;
import com.gaboj1.tcr.entity.custom.biome2.BigHammerEntity;
import com.gaboj1.tcr.entity.custom.boss.second_boss.ScreenSwordEntityForBoss;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.entity.custom.boss.second_boss.StellarSwordEntity;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SwordConvergenceEntity;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.MagicProjectile;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.TreeClawEntity;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.biome2.BoxerEntity;
import com.gaboj1.tcr.entity.custom.dreamspirit.CrabCrabYou;
import com.gaboj1.tcr.entity.custom.dreamspirit.JellyCat;
import com.gaboj1.tcr.entity.custom.dreamspirit.Squirrel;
import com.gaboj1.tcr.entity.custom.biome2.SnowSwordmanEntity;
import com.gaboj1.tcr.entity.custom.dreamspirit.WindFeatherFalconEntity;
import com.gaboj1.tcr.entity.custom.sword.RainCutterSwordEntity;
import com.gaboj1.tcr.entity.custom.sword.ScreenSwordEntity;
import com.gaboj1.tcr.entity.custom.sword.SwordEntity;
import com.gaboj1.tcr.entity.custom.biome2.SwordControllerEntity;
import com.gaboj1.tcr.entity.custom.biome2.TigerEntity;
import com.gaboj1.tcr.entity.custom.villager.P1nero;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainStationaryVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.entity.custom.*;

import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import com.gaboj1.tcr.entity.custom.villager.biome2.*;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.*;
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


@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCREntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheCasketOfReveriesMod.MOD_ID);
	public static final RegistryObject<EntityType<BulletEntity>> DESERT_EAGLE_BULLET = register("projectile_desert_eagle_bullet", EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
			.setCustomClientFactory(BulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.1f, 0.1f));
	public static final RegistryObject<EntityType<IceThornEntity>> ICE_THORN_ENTITY = register("ice_thorn_entity", EntityType.Builder.<IceThornEntity>of(IceThornEntity::new, MobCategory.MISC)
			.setCustomClientFactory(IceThornEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.1f, 0.1f));
	public static final RegistryObject<EntityType<MagicProjectile>> MAGIC_PROJECTILE = register("magic_projectile", EntityType.Builder.<MagicProjectile>of(MagicProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

	public static final RegistryObject<EntityType<SwordEntity>> SWORD = register("sword",
			EntityType.Builder.of(SwordEntity::new, MobCategory.CREATURE));


	public static final RegistryObject<EntityType<ScreenSwordEntity>> RAIN_SCREEN_SWORD = register("rain_screen_sword",
			EntityType.Builder.of(ScreenSwordEntity::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<ScreenSwordEntityForBoss>> RAIN_SCREEN_SWORD_FOR_BOSS2 = register("rain_screen_sword_for_boss2",
			EntityType.Builder.of(ScreenSwordEntityForBoss::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<StellarSwordEntity>> STELLAR_SWORD = register("stellar_sword", EntityType.Builder.<StellarSwordEntity>of(StellarSwordEntity::new, MobCategory.MISC)
			.setCustomClientFactory(StellarSwordEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1f, 0.2f));
	public static final RegistryObject<EntityType<SwordConvergenceEntity>> CONVERGENCE_SWORD = register("convergence_sword", EntityType.Builder.<SwordConvergenceEntity>of(SwordConvergenceEntity::new, MobCategory.MISC)
			.setCustomClientFactory(SwordConvergenceEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1f, 0.2f));

	public static final RegistryObject<EntityType<RainCutterSwordEntity>> RAIN_CUTTER_SWORD = register("rain_cutter_sword", EntityType.Builder.<RainCutterSwordEntity>of(RainCutterSwordEntity::new, MobCategory.MISC)
			.setCustomClientFactory(RainCutterSwordEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1f, 0.2f));

	public static final RegistryObject<EntityType<SmallTreeMonsterEntity>> SMALL_TREE_MONSTER =
			REGISTRY.register("small_tree_monster",
					() -> EntityType.Builder.of(SmallTreeMonsterEntity::new, MobCategory.MONSTER)
							.sized(0.4f, 0.4f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "small_tree_monster").toString()));

	public static final RegistryObject<EntityType<MiddleTreeMonsterEntity>> MIDDLE_TREE_MONSTER =
			REGISTRY.register("middle_tree_monster",
					() -> EntityType.Builder.of(MiddleTreeMonsterEntity::new, MobCategory.MONSTER)
							.sized(1f, 1.8f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "middle_tree_monster").toString()));

	public static final RegistryObject<EntityType<TreeGuardianEntity>> TREE_GUARDIAN =
			REGISTRY.register("tree_guardian",
					() -> EntityType.Builder.of(TreeGuardianEntity::new, MobCategory.MONSTER)
							.sized(0.78f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_guardian").toString()));

	public static final RegistryObject<EntityType<UnknownEntity>> UNKNOWN =
			REGISTRY.register("unknown",
					() -> EntityType.Builder.of(UnknownEntity::new, MobCategory.MONSTER)
							.sized(1.3f, 1f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "unknow").toString()));

	public static final RegistryObject<EntityType<WindFeatherFalconEntity>> WIND_FEATHER_FALCON =
			REGISTRY.register("wind_feather_falcon",
					() -> EntityType.Builder.of(WindFeatherFalconEntity::new, MobCategory.MONSTER)
							.sized(0.55f, 1.3f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "wind_feather_falcon").toString()));

	public static final RegistryObject<EntityType<SpriteEntity>> SPRITE =
			REGISTRY.register("sprite",
					() -> EntityType.Builder.of(SpriteEntity::new, MobCategory.CREATURE)
							.sized(0.78f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "sprite").toString()));

	public static final RegistryObject<EntityType<TigerEntity>> TIGER =
			REGISTRY.register("tiger",
					() -> EntityType.Builder.of(TigerEntity::new, MobCategory.CREATURE)
							.sized(2.2f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tiger").toString()));

	public static final RegistryObject<EntityType<BoxerEntity>> BOXER =
			REGISTRY.register("boxer",
					() -> EntityType.Builder.of(BoxerEntity::new, MobCategory.CREATURE)
							.sized(0.78f, 1.6f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "boxer").toString()));

	public static final RegistryObject<EntityType<BigHammerEntity>> BIG_HAMMER =
			REGISTRY.register("big_hammer",
					() -> EntityType.Builder.of(BigHammerEntity::new, MobCategory.CREATURE)
							.sized(2f, 3f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "big_hammer").toString()));

	public static final RegistryObject<EntityType<SnowSwordmanEntity>> SNOW_SWORDMAN =
			REGISTRY.register("snow_swordman",
					() -> EntityType.Builder.of(SnowSwordmanEntity::new, MobCategory.CREATURE)
							.sized(0.78f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "snow_swordman").toString()));

	public static final RegistryObject<EntityType<SwordControllerEntity>> SWORD_CONTROLLER =
			REGISTRY.register("sword_controller",
					() -> EntityType.Builder.of(SwordControllerEntity::new, MobCategory.CREATURE)
							.sized(0.78f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "sword_controller").toString()));

	public static final RegistryObject<EntityType<SecondBossEntity>> SECOND_BOSS =
			REGISTRY.register("second_boss",
					() -> EntityType.Builder.of(SecondBossEntity::new, MobCategory.CREATURE)
							.sized(0.78f, 2f)
							.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "second_boss").toString()));
	public static final RegistryObject<EntityType<JellyCat>> JELLY_CAT = register("jelly_cat",
			EntityType.Builder.of(JellyCat::new, MobCategory.CREATURE).sized(2.0f,1.75f));
	public static final RegistryObject<EntityType<Squirrel>> SQUIRREL = register("squirrel",
			EntityType.Builder.of(Squirrel::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<CrabCrabYou>> CRAB = register("crab",
			EntityType.Builder.of(CrabCrabYou::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<P1nero>> P1NERO = register("p1nero",
			EntityType.Builder.of(P1nero::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<TCRFakePlayer>> FAKE_PLAYER = register("fake_player",
			EntityType.Builder.of(TCRFakePlayer::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<PastoralPlainVillager>> PASTORAL_PLAIN_VILLAGER = register("pastoral_plain_villager",
			EntityType.Builder.of(PastoralPlainVillager::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<PastoralPlainTalkableVillager>> PASTORAL_PLAIN_TALKABLE_VILLAGER = register("pastoral_plain_talkable_villager",
			EntityType.Builder.of(PastoralPlainTalkableVillager::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<PastoralPlainStationaryVillager>> PASTORAL_PLAIN_STATIONARY_VILLAGER = register("pastoral_plain_stationary_villager",
			EntityType.Builder.of(PastoralPlainStationaryVillager::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<PastoralPlainVillagerElder>> PASTORAL_PLAIN_VILLAGER_ELDER = register("pastoral_plain_villager_elder",
			EntityType.Builder.of(PastoralPlainVillagerElder::new, MobCategory.CREATURE));

	public static final RegistryObject<EntityType<Villager2>> VILLAGER2 = register("villager2",
			EntityType.Builder.of(Villager2::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<TalkableVillager2>> VILLAGER2_TALKABLE = register("villager2_talkable",
			EntityType.Builder.of(TalkableVillager2::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<StationaryVillager2>> VILLAGER2_STATIONARY = register("villager2_stationary",
			EntityType.Builder.of(StationaryVillager2::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<MiaoYin>> MIAO_YIN = register("miao_yin",
			EntityType.Builder.of(MiaoYin::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<ShangRen>> SHANG_REN = register("shang_ren",
			EntityType.Builder.of(ShangRen::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<Receptionist>> RECEPTIONIST = register("receptionist",
			EntityType.Builder.of(Receptionist::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<Wanderer>> WANDERER = register("wanderer",
			EntityType.Builder.of(Wanderer::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<TrialMaster>> TRIAL_MASTER = register("trial_master",
			EntityType.Builder.of(TrialMaster::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<FuryTideCangLan>> CANG_LAN = register("cang_lan",
			EntityType.Builder.of(FuryTideCangLan::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<ThunderclapZhenYu>> ZHEN_YU = register("zhen_yu",
			EntityType.Builder.of(ThunderclapZhenYu::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<IronfistDuanShan>> DUAN_SHAN = register("duan_shan",
			EntityType.Builder.of(IronfistDuanShan::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<SerpentWhispererCuiHua>> CUI_HUA = register("cui_hua",
			EntityType.Builder.of(SerpentWhispererCuiHua::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<WindwalkerYunYi>> YUN_YI = register("yun_yi",
			EntityType.Builder.of(WindwalkerYunYi::new, MobCategory.CREATURE));
	public static final RegistryObject<EntityType<BlazingFlameYanXin>> YAN_XIN = register("yan_xin",
			EntityType.Builder.of(BlazingFlameYanXin::new, MobCategory.CREATURE));

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


	private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(name, () -> entityTypeBuilder.build(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, name).toString()));
	}

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {

		event.put(SMALL_TREE_MONSTER.get(), SmallTreeMonsterEntity.setAttributes());
		event.put(TREE_GUARDIAN.get(), TreeGuardianEntity.setAttributes());//设置生物属性功能在此被调用
		event.put(MIDDLE_TREE_MONSTER.get(), MiddleTreeMonsterEntity.setAttributes());
		event.put(UNKNOWN.get(), UnknownEntity.setAttributes());
		event.put(WIND_FEATHER_FALCON.get(), WindFeatherFalconEntity.setAttributes());
		event.put(JELLY_CAT.get(), JellyCat.setAttributes());
		event.put(SQUIRREL.get(), Squirrel.setAttributes());
		event.put(CRAB.get(), CrabCrabYou.setAttributes());
		event.put(P1NERO.get(), TCRVillager.setAttributes());
		event.put(FAKE_PLAYER.get(), TCRVillager.setAttributes());
		event.put(PASTORAL_PLAIN_VILLAGER.get(), TCRVillager.setAttributes());
		event.put(PASTORAL_PLAIN_TALKABLE_VILLAGER.get(), TCRVillager.setAttributes());
		event.put(PASTORAL_PLAIN_STATIONARY_VILLAGER.get(), TCRVillager.setAttributes());
		event.put(PASTORAL_PLAIN_VILLAGER_ELDER.get(),PastoralPlainVillagerElder.setAttributes());
		event.put(YGGDRASIL.get(), YggdrasilEntity.setAttributes());
		event.put(TREE_CLAW.get(), TreeClawEntity.setAttributes());
		event.put(SPRITE.get(),SpriteEntity.setAttributes());

		event.put(VILLAGER2.get(), TCRVillager.setAttributes());
		event.put(VILLAGER2_TALKABLE.get(), TCRVillager.setAttributes());
		event.put(VILLAGER2_STATIONARY.get(), TCRVillager.setAttributes());
		event.put(MIAO_YIN.get(),TCRVillager.setAttributes());
		event.put(SHANG_REN.get(),TCRVillager.setAttributes());
		event.put(RECEPTIONIST.get(),TCRVillager.setAttributes());
		event.put(WANDERER.get(),TCRVillager.setAttributes());
		event.put(TRIAL_MASTER.get(),TCRVillager.setAttributes());
		event.put(CANG_LAN.get(),FuryTideCangLan.setAttributes());
		event.put(DUAN_SHAN.get(),IronfistDuanShan.setAttributes());
		event.put(ZHEN_YU.get(),ThunderclapZhenYu.setAttributes());
		event.put(CUI_HUA.get(),SerpentWhispererCuiHua.setAttributes());
		event.put(YUN_YI.get(),WindwalkerYunYi.setAttributes());
		event.put(YAN_XIN.get(),BlazingFlameYanXin.setAttributes());
		event.put(TIGER.get(),TigerEntity.setAttributes());
		event.put(BOXER.get(), BoxerEntity.setAttributes());
		event.put(BIG_HAMMER.get(),BigHammerEntity.setAttributes());
		event.put(SNOW_SWORDMAN.get(),SnowSwordmanEntity.setAttributes());
		event.put(SWORD_CONTROLLER.get(),SwordControllerEntity.setAttributes());
		event.put(TCREntities.SECOND_BOSS.get(),SecondBossEntity.setAttributes());
	}

	@SubscribeEvent
	public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {

		event.register(P1NERO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				P1nero::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
//		event.register(FAKE_PLAYER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
//				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(PASTORAL_PLAIN_STATIONARY_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainTalkableVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(PASTORAL_PLAIN_TALKABLE_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainTalkableVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(PASTORAL_PLAIN_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(PASTORAL_PLAIN_VILLAGER_ELDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainVillagerElder::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(VILLAGER2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainTalkableVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(VILLAGER2_STATIONARY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainTalkableVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(VILLAGER2_TALKABLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PastoralPlainVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(MIAO_YIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FuryTideCangLan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(SHANG_REN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FuryTideCangLan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(RECEPTIONIST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FuryTideCangLan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(WANDERER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FuryTideCangLan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(TRIAL_MASTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FuryTideCangLan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(CANG_LAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FuryTideCangLan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(DUAN_SHAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				IronfistDuanShan::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(ZHEN_YU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ThunderclapZhenYu::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(CUI_HUA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				SerpentWhispererCuiHua::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(YUN_YI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				WindwalkerYunYi::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(YAN_XIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				BlazingFlameYanXin::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(JELLY_CAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				JellyCat::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(SQUIRREL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Squirrel::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				CrabCrabYou::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(SMALL_TREE_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(MIDDLE_TREE_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(TREE_GUARDIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(WIND_FEATHER_FALCON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(UNKNOWN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(SPRITE.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(TIGER.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				TigerEntity::checkTigerSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(BOXER.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				BoxerEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(BIG_HAMMER.get(),SpawnPlacements.Type.ON_GROUND,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				BigHammerEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(SNOW_SWORDMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				SnowSwordmanEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(SWORD_CONTROLLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				SwordControllerEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(TCREntities.SECOND_BOSS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				SecondBossEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(TREE_CLAW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> true), SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

}
