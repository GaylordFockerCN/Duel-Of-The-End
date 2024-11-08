package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class YggdrasilSpawnerBlockEntity extends BossSpawnerBlockEntity<YggdrasilEntity> implements GeoBlockEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public YggdrasilSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TCRBlockEntities.YGGDRASIL_SPAWNER_BLOCK_ENTITY.get(), TCREntities.YGGDRASIL.get(), pos, state);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	/**
	 * 群系事件结束后才可以召唤历战
	 */
	@Override
	public boolean canSpawnShadow() {
		return TCRArchiveManager.biome1.isFinished();
	}

	@Override
	public boolean shouldDestroySelf() {
		return false;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ParticleTypes.PORTAL;
	}
}
