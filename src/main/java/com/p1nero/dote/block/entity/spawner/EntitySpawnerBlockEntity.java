package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.entity.HomePointEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class EntitySpawnerBlockEntity<T extends Entity> extends BlockEntity {
	protected final EntityType<T> entityType;
	protected Entity myEntity;
	@Nullable
	protected Player currentPlayer;//限制仅能一个人挑战
	public EntityType<T> getEntityType() {
		return entityType;
	}
	public @Nullable Entity getMyEntity() {
		return myEntity;
	}

	public void setCurrentPlayer(@Nullable Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public @Nullable Player getCurrentPlayer() {
		return currentPlayer;
	}

	protected EntitySpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.entityType = entityType;
	}

	public void spawnMyBoss(ServerLevelAccessor accessor) {
		myEntity = this.makeMyCreature();
		if(myEntity instanceof HomePointEntity homePointEntity){
			homePointEntity.setHomePos(getBlockPos());
		}
		BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().above()).getCollisionShape(accessor, this.getBlockPos().above()).isEmpty() ? this.getBlockPos().above() : this.getBlockPos();
		myEntity.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360F, 0.0F);
		if(myEntity instanceof Mob mob){
			ForgeEventFactory.onFinalizeSpawn(mob, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null, null);
		}
		accessor.addFreshEntity(myEntity);
	}

	public abstract ParticleOptions getSpawnerParticle();
	@Nullable
	public ParticleOptions getBorderParticle(){
		return ParticleTypes.DRAGON_BREATH;
	};

	@NotNull
	protected T makeMyCreature() {
		return Objects.requireNonNull(this.entityType.create(Objects.requireNonNull(this.getLevel())));
	}

}
