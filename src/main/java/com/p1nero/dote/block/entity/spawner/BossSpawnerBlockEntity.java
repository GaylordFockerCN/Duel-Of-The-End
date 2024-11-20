package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.HomePointEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public abstract class BossSpawnerBlockEntity<T extends Mob & HomePointEntity> extends EntitySpawnerBlockEntity<T> {
	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, entityType, pos, state);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState state, BlockEntity blockEntity) {
		if(blockEntity instanceof BossSpawnerBlockEntity<?> spawnerBlockEntity){
			if(pLevel instanceof ServerLevel serverLevel){
				if(spawnerBlockEntity.getSpawnerParticle() != null){
					double rx = pPos.getX() + pLevel.getRandom().nextFloat();
					double ry = pPos.getY() + pLevel.getRandom().nextFloat();
					double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
					serverLevel.sendParticles(spawnerBlockEntity.getSpawnerParticle(), rx, ry, rz ,1, 0.0D, 0.0D, 0.0D, 0);
				}
				if(spawnerBlockEntity.getBorderParticle() != null && spawnerBlockEntity.myBoss != null){
					for (int angle = 0; angle < 360; angle += 2) {
						double radians = Math.toRadians(angle);
						int xOffset = (int) Math.round(spawnerBlockEntity.myBoss.getHomeRadius() * Math.cos(radians));
						int zOffset = (int) Math.round(spawnerBlockEntity.myBoss.getHomeRadius() * Math.sin(radians));
						serverLevel.sendParticles(spawnerBlockEntity.getBorderParticle(), pPos.getX() + xOffset, pPos.getY() + 1, pPos.getZ() + zOffset, 1, 0.0D, 0.1D, 0.0D, 0.01);
					}
				}
				//击败boss清空状态
				if(spawnerBlockEntity.myBoss == null || spawnerBlockEntity.myBoss.isDeadOrDying()){
					spawnerBlockEntity.myBoss = null;
					spawnerBlockEntity.currentPlayer = null;
					return;
				}

				int r = DOTEConfig.SPAWNER_BLOCK_PROTECT_RADIUS.get() + 3;//要比实际的大一点点，防止在边缘偷刀
				//弹开怪物和多余玩家
				for(LivingEntity livingEntity : pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos.offset(-r, -r, -r), pPos.offset(r, r, r)))){
                    if(spawnerBlockEntity.currentPlayer != null && livingEntity.getUUID().equals(spawnerBlockEntity.currentPlayer.getUUID()) || (spawnerBlockEntity.entityType.equals(livingEntity.getType()))){
						continue;
					}
					livingEntity.setDeltaMovement(livingEntity.position().subtract(pPos.getCenter()).normalize());
				}
				//防止玩家逃跑
				if(spawnerBlockEntity.getCurrentPlayer() == null){
					pLevel.explode(spawnerBlockEntity.myBoss, spawnerBlockEntity.myBoss.damageSources().explosion(spawnerBlockEntity.myBoss, spawnerBlockEntity.myBoss), null, spawnerBlockEntity.myBoss.position(), 3F, false, Level.ExplosionInteraction.NONE);
					spawnerBlockEntity.myBoss.discard();
					spawnerBlockEntity.myBoss = null;
				} else if(spawnerBlockEntity.getCurrentPlayer().position().distanceTo(pPos.getCenter()) > spawnerBlockEntity.myBoss.getHomeRadius()
				 			&& spawnerBlockEntity.getCurrentPlayer().position().distanceTo(pPos.getCenter()) < spawnerBlockEntity.myBoss.getHomeRadius() + 3){
					spawnerBlockEntity.getCurrentPlayer().hurt(spawnerBlockEntity.myBoss.damageSources().magic(), 5);
					spawnerBlockEntity.getCurrentPlayer().displayClientMessage(DuelOfTheEndMod.getInfo("tip9"), true);
				}
			}
		}

	}

}
