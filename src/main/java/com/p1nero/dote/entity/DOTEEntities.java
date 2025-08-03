package com.p1nero.dote.entity;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.custom.boss.SimpleBoss;
import com.p1nero.dote.entity.custom.boss.dark_advance.DarkAdvance;
import com.p1nero.dote.entity.custom.boss.goldenflame.BlackHoleEntity;
import com.p1nero.dote.entity.custom.boss.goldenflame.GoldenFlame;
import com.p1nero.dote.entity.custom.boss.liu_guang.LiuGuangEntity;
import com.p1nero.dote.entity.custom.boss.ms_abyss.MsAbyssEntity;
import com.p1nero.dote.entity.custom.boss.reaper.ReaperEntity;
import com.p1nero.dote.entity.custom.boss.sand_captain.SandCaptainEntity;
import com.p1nero.dote.entity.custom.boss.senbai.SenbaiDevil;
import com.p1nero.dote.entity.custom.boss.slaughter_general.SlaughterGeneralEntity;
import com.p1nero.dote.entity.custom.npc.abyss_dweller.AbyssDwellerEntity;
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


@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTEEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DuelOfTheEndMod.MOD_ID);

	public static final RegistryObject<EntityType<AbyssDwellerEntity>> ABYSS_DWELLER = register("abyss_dweller",
			EntityType.Builder.of(AbyssDwellerEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<ReaperEntity>> REAPER = register("reaper",
			EntityType.Builder.of(ReaperEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DarkAdvance>> DARK_ADVANCE = register("dark_advance",
			EntityType.Builder.of(DarkAdvance::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SandCaptainEntity>> SAND_CAPTAIN = register("sand_captain",
			EntityType.Builder.of(SandCaptainEntity::new, MobCategory.MONSTER).sized(0.8F, 2.5F));
	public static final RegistryObject<EntityType<SenbaiDevil>> SENBAI_DEVIL = register("senbai_devil",
			EntityType.Builder.of(SenbaiDevil::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SlaughterGeneralEntity>> SLAUGHTER_GENERAL = register("slaughter_general",
			EntityType.Builder.of(SlaughterGeneralEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<BlackHoleEntity>> BLACK_HOLE = register("black_hole",
			EntityType.Builder.of(BlackHoleEntity::new, MobCategory.MISC).sized(1.0f, 1.0f));
	public static final RegistryObject<EntityType<GoldenFlame>> GOLDEN_FLAME = register("golden_flame",
			EntityType.Builder.of(GoldenFlame::new, MobCategory.MONSTER).sized(0.8f, 2.5f));
	public static final RegistryObject<EntityType<MsAbyssEntity>> MS_ABYSS = register("ms_abyss",
			EntityType.Builder.of(MsAbyssEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<LiuGuangEntity>> LIU_GUANG = register("liu_guang",
			EntityType.Builder.of(LiuGuangEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> FALLEN_JUDGE = register("fallen_judge",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> SHAO_QIN = register("shao_qin",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> SAGE = register("sage",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> SINCER_WARRIOR = register("sincer_warrior",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> THEBANCHENG = register("thebancheng",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> THECOWCOWCOW7 = register("thecowcowcow7",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> THEHOTSUMMER = register("thehotsummer",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> THESIXGOOGLE = register("thesixgoogle",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> THESUNWUKONG = register("thesunwukong",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SimpleBoss>> THEZHAOZILONG = register("thezhaozilong",
			EntityType.Builder.of(SimpleBoss::new, MobCategory.MONSTER).sized(0.6f, 1.8f));


	private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(name, () -> entityTypeBuilder.build(new ResourceLocation(DuelOfTheEndMod.MOD_ID, name).toString()));
	}

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
		//BOSS
		event.put(REAPER.get(), ReaperEntity.setAttributes());
		event.put(DARK_ADVANCE.get(), DarkAdvance.setAttributes());
		event.put(SAND_CAPTAIN.get(), SandCaptainEntity.setAttributes());
		event.put(SENBAI_DEVIL.get(), SenbaiDevil.setAttributes());
		event.put(SLAUGHTER_GENERAL.get(), SlaughterGeneralEntity.setAttributes());
		event.put(GOLDEN_FLAME.get(), GoldenFlame.setAttributes());
		event.put(MS_ABYSS.get(), MsAbyssEntity.setAttributes());
		event.put(LIU_GUANG.get(), LiuGuangEntity.setAttributes());

		event.put(FALLEN_JUDGE.get(), SimpleBoss.setAttributes());
		event.put(SAGE.get(), SimpleBoss.setAttributes());
		event.put(SHAO_QIN.get(), SimpleBoss.setAttributes());
		event.put(SINCER_WARRIOR.get(), SimpleBoss.setAttributes());
		event.put(THEBANCHENG.get(), SimpleBoss.setAttributes());
		event.put(THECOWCOWCOW7.get(), SimpleBoss.setAttributes());
		event.put(THEHOTSUMMER.get(), SimpleBoss.setAttributes());
		event.put(THESIXGOOGLE.get(), SimpleBoss.setAttributes());
		event.put(THESUNWUKONG.get(), SimpleBoss.setAttributes());
		event.put(THEZHAOZILONG.get(), SimpleBoss.setAttributes());

		//NPC
		event.put(ABYSS_DWELLER.get(), AbyssDwellerEntity.setAttributes());
	}

	@SubscribeEvent
	public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
		event.register(ABYSS_DWELLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				AbyssDwellerEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

}
