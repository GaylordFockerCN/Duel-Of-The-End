package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.entity.HomePointEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class EntitySpawnerBlockEntity<T extends Mob & HomePointEntity> extends BlockEntity {
	protected final EntityType<T> entityType;
	@Nullable
	protected T myBoss;
	@Nullable
	protected Player currentPlayer;//限制仅能一个人挑战
	public EntityType<T> getEntityType() {
		return entityType;
	}
	public @Nullable T getMyBoss() {
		return myBoss;
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

	/**
	 * FIXME 无法触发
	 */
	public static void tick(Level pLevel, BlockPos pPos,  BlockState state, EntitySpawnerBlockEntity<?> blockEntity) {
		System.out.println("111");
		if(pLevel instanceof ServerLevel serverLevel){
			if(blockEntity.getSpawnerParticle() != null){
				double rx = pPos.getX() + pLevel.getRandom().nextFloat();
				double ry = pPos.getY() + pLevel.getRandom().nextFloat();
				double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
				serverLevel.sendParticles(blockEntity.getSpawnerParticle(), rx, ry, rz ,1, 0.0D, 0.0D, 0.0D, 0);
			}
			if(blockEntity.getBorderParticle() != null && blockEntity.myBoss != null){
				for (int angle = 0; angle < 360; angle += 5) {
					double radians = Math.toRadians(angle);
					int xOffset = (int) Math.round(blockEntity.myBoss.getHomeRadius() * Math.cos(radians));
					int zOffset = (int) Math.round(blockEntity.myBoss.getHomeRadius() * Math.sin(radians));
					serverLevel.sendParticles(blockEntity.getBorderParticle(), pPos.getX() + xOffset, pPos.getY() + 1, pPos.getZ() + zOffset, 1, 0.0D, 0.1D, 0.0D, 0.01);
				}
			}
			//清空状态
			if(blockEntity.myBoss == null || blockEntity.myBoss.isDeadOrDying()){
				blockEntity.myBoss = null;
				blockEntity.currentPlayer = null;
			}

			int r = DOTEConfig.SPAWNER_BLOCK_PROTECT_RADIUS.get() + 3;//要比实际的大一点点，防止在边缘偷刀
			//弹开怪物和多余玩家
			for(LivingEntity livingEntity : pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos.offset(-r, -r, -r), pPos.offset(r, r, r)))){
				if(livingEntity != blockEntity.currentPlayer && livingEntity != blockEntity.myBoss){
//					livingEntity.setDeltaMovement(livingEntity.position().subtract(pPos.getCenter()).normalize());//无效？
					Vec3 dir = livingEntity.position().subtract(pPos.getCenter()).normalize();
					livingEntity.setPos(livingEntity.position().add(dir.scale(2)));
				}
			}
		}

	}

	public void spawnMyBoss(ServerLevelAccessor accessor) {
		myBoss = this.makeMyCreature();
		myBoss.setHomePos(getBlockPos());
		BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().above()).getCollisionShape(accessor, this.getBlockPos().above()).isEmpty() ? this.getBlockPos().above() : this.getBlockPos();
		myBoss.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360F, 0.0F);
		ForgeEventFactory.onFinalizeSpawn(myBoss, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null, null);
		accessor.addFreshEntity(myBoss);
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
