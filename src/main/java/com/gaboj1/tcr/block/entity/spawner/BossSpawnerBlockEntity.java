package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.entity.ShadowableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;


import java.util.Objects;

public abstract class BossSpawnerBlockEntity<T extends Mob & ShadowableEntity> extends BlockEntity {

	protected static final int SHORT_RANGE = 9, LONG_RANGE = 50;

	protected final EntityType<T> entityType;
	protected boolean spawnedBoss = false;
	protected boolean isReady = false;

	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.entityType = entityType;
	}

	public boolean anyPlayerInRange() {
		return Objects.requireNonNull(this.getLevel()).hasNearbyAlivePlayer(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D, this.getRange());
	}

	/**
	 * 召唤历战版
	 */
	public void tryToSpawnShadow(ServerLevel level){
		if(!canSpawnShadow()){
			return;
		}
		if(!isReady){
			isReady = true;
			if(level.isClientSide){
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.displayClientMessage(Component.literal("info.the_casket_of_reveries.sureToSpawn"), true);
			}
		}
		isReady = false;
		spawnMyShadowBoss(level);
	}

	/**
	 * boss击败后才可以召唤历战
	 */
	public abstract boolean canSpawnShadow();

	public static void tick(Level level, BlockPos pos,  BlockState state, BossSpawnerBlockEntity<?> blockEntity) {
		if (blockEntity.spawnedBoss || !blockEntity.anyPlayerInRange()) {
			return;
		}
		if (level.isClientSide()) {
			// particles
			double rx = pos.getX() + level.getRandom().nextFloat();
			double ry = pos.getY() + level.getRandom().nextFloat();
			double rz = pos.getZ() + level.getRandom().nextFloat();
			level.addParticle(blockEntity.getSpawnerParticle(), rx, ry, rz, 0.0D, 0.0D, 0.0D);
		} else {
			if (level.getDifficulty() != Difficulty.PEACEFUL) {
				if (blockEntity.spawnMyBoss((ServerLevel) level)) {
					level.destroyBlock(pos, false);
					blockEntity.spawnedBoss = true;
				}
			}
		}
	}

	protected boolean spawnMyShadowBoss(ServerLevelAccessor accessor) {
		// create creature
		T myCreature = this.makeMyCreature();
		myCreature.setShadow();
		BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().above()).getCollisionShape(accessor, this.getBlockPos().above()).isEmpty() ? this.getBlockPos().above() : this.getBlockPos();
		myCreature.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360F, 0.0F);
		ForgeEventFactory.onFinalizeSpawn(myCreature, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null, null);

		// spawn it
		return accessor.addFreshEntity(myCreature);
	}

	protected boolean spawnMyBoss(ServerLevelAccessor accessor) {
		// create creature
		T myCreature = this.makeMyCreature();

		BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().above()).getCollisionShape(accessor, this.getBlockPos().above()).isEmpty() ? this.getBlockPos().above() : this.getBlockPos();
		myCreature.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360F, 0.0F);
		ForgeEventFactory.onFinalizeSpawn(myCreature, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null, null);

		// spawn it
		return accessor.addFreshEntity(myCreature);
	}

	public abstract ParticleOptions getSpawnerParticle();

	protected int getRange() {
		return SHORT_RANGE;
	}

	protected T makeMyCreature() {
		return Objects.requireNonNull(this.entityType.create(Objects.requireNonNull(this.getLevel())));
	}

}
