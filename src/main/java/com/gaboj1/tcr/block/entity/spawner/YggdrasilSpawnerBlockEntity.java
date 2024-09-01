package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.block.TCRModBlockEntities;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

public class YggdrasilSpawnerBlockEntity extends BossSpawnerBlockEntity<YggdrasilEntity> implements GeoAnimatable {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public YggdrasilSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TCRModBlockEntities.YGGDRASIL_SPAWNER_BLOCK_ENTITY.get(),TCRModEntities.YGGDRASIL.get(), pos, state);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public double getTick(Object o) {
		return RenderUtils.getCurrentTick();
	}

	/**
	 * 群系事件结束后才可以召唤历战
	 */
	@Override
	public boolean canSpawnShadow() {
		return SaveUtil.biome1.isFinished();
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ParticleTypes.PORTAL;
	}
}
