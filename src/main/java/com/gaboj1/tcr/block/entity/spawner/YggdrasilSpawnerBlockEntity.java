package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.init.TCRModBlockEntities;
import com.gaboj1.tcr.init.TCRModEntities;
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
	public boolean anyPlayerInRange() {
		Player closestPlayer = this.getLevel().getNearestPlayer(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D, this.getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > this.getBlockPos().getY() - 4;
	}

	@Override
	protected boolean spawnMyBoss(ServerLevelAccessor accessor) {

		YggdrasilEntity myCreature = this.makeMyCreature();

		myCreature.moveTo(this.getBlockPos(), accessor.getLevel().random.nextFloat() * 360F, 0.0F);
		ForgeEventFactory.onFinalizeSpawn(myCreature, accessor, accessor.getCurrentDifficultyAt(this.getBlockPos()), MobSpawnType.SPAWNER, null, null);

		// set creature's home to this
		this.initializeCreature(myCreature);

		// spawn it
		return accessor.addFreshEntity(myCreature);
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ParticleTypes.ANGRY_VILLAGER;
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
}
