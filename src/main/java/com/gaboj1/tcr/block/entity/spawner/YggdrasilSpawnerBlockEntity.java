package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.block.TCRModBlockEntities;
import com.gaboj1.tcr.entity.TCRModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class YggdrasilSpawnerBlockEntity extends BossSpawnerBlockEntity<YggdrasilEntity> implements GeoAnimatable {
	public YggdrasilSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TCRModBlockEntities.YGGDRASIL_SPAWNER_BLOCK_ENTITY.get(),TCRModEntities.YGGDRASIL.get(), pos, state);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return null;
	}

	@Override
	public double getTick(Object o) {
		return 0;
	}

	@Override
	public boolean canSpawnShadow() {
		return false;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return null;
	}
}
