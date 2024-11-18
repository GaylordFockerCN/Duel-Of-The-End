package com.p1nero.dote.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class EntitySpawnerBlockEntity<T extends Mob> extends BlockEntity {
	protected final EntityType<T> entityType;
	protected boolean spawned = false;
	protected LivingEntity myBoss;

	public boolean isSpawned() {
		return spawned;
	}

	public EntityType<T> getEntityType() {
		return entityType;
	}

	protected EntitySpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.entityType = entityType;
	}

	public static void tick(Level level, BlockPos pos,  BlockState state, EntitySpawnerBlockEntity<?> blockEntity) {
		if (level.isClientSide) {
			if(blockEntity.getSpawnerParticle() != null){
				double rx = pos.getX() + level.getRandom().nextFloat();
				double ry = pos.getY() + level.getRandom().nextFloat();
				double rz = pos.getZ() + level.getRandom().nextFloat();
				level.addParticle(blockEntity.getSpawnerParticle(), rx, ry, rz, 0.0D, 0.0D, 0.0D);
			}
		}
		if(!level.isClientSide){
			if(blockEntity.spawned && (blockEntity.myBoss == null || blockEntity.myBoss.isDeadOrDying())){
				blockEntity.spawned = false;
			}
		}

	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		tag.putBoolean("spawned", spawned);
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		spawned = tag.getBoolean("spawned");
		super.load(tag);
	}

	public void spawnMyBoss(ServerLevelAccessor accessor) {
		T myCreature = this.makeMyCreature();
		BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().above()).getCollisionShape(accessor, this.getBlockPos().above()).isEmpty() ? this.getBlockPos().above() : this.getBlockPos();
		myCreature.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360F, 0.0F);
		ForgeEventFactory.onFinalizeSpawn(myCreature, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null, null);
		accessor.addFreshEntity(myCreature);
	}

	public abstract ParticleOptions getSpawnerParticle();

	protected T makeMyCreature() {
		return Objects.requireNonNull(this.entityType.create(Objects.requireNonNull(this.getLevel())));
	}

}
