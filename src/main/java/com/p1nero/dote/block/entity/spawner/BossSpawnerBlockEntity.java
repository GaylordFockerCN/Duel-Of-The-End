package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.HomePointEntity;
import com.p1nero.dote.entity.custom.DOTEBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public abstract class BossSpawnerBlockEntity<T extends DOTEBoss> extends EntitySpawnerBlockEntity<T> {
	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, entityType, pos, state);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState state, BlockEntity blockEntity) {
		if(blockEntity instanceof BossSpawnerBlockEntity<?> spawnerBlockEntity){
			if(pLevel instanceof ServerLevel serverLevel){
				if(spawnerBlockEntity.getSpawnerParticle() != null){
					double rx = pPos.getX() + pLevel.getRandom().nextFloat();
					double ry = pPos.getY() + 1 + pLevel.getRandom().nextFloat();
					double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
					serverLevel.sendParticles(spawnerBlockEntity.getSpawnerParticle(), rx, ry, rz ,1, 0.0D, 0.0D, 0.0D, 0);
				}
				if(spawnerBlockEntity.myEntity instanceof HomePointEntity homePointEntity){
					if(spawnerBlockEntity.getBorderParticle() != null && spawnerBlockEntity.myEntity != null){
						for (int angle = 0; angle < 360; angle += 2) {
							double radians = Math.toRadians(angle);
							int xOffset = (int) Math.round(homePointEntity.getHomeRadius() * Math.cos(radians));
							int zOffset = (int) Math.round(homePointEntity.getHomeRadius() * Math.sin(radians));
							serverLevel.sendParticles(spawnerBlockEntity.getBorderParticle(), pPos.getX() + xOffset, pPos.getY() + 1, pPos.getZ() + zOffset, 1, 0.0D, 0.1D, 0.0D, 0.01);
						}
					}

					int r = (int) (homePointEntity.getHomeRadius() + 3);//要比实际的大一点点，防止在边缘偷刀
					//弹开怪物和多余玩家
					for(LivingEntity livingEntity : pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos.offset(-r, -r, -r), pPos.offset(r, r, r)))){
						if(spawnerBlockEntity.currentPlayer != null && livingEntity.getUUID().equals(spawnerBlockEntity.currentPlayer.getUUID())){
							continue;
						}
						//同步boss，以防死后boss对象丢失
						if((spawnerBlockEntity.entityType.equals(livingEntity.getType())) && livingEntity instanceof DOTEBoss){
							spawnerBlockEntity.myEntity = livingEntity;
							continue;
						}
						livingEntity.setDeltaMovement(livingEntity.position().subtract(pPos.getCenter()).normalize());
					}
					//防止玩家逃跑
					if(spawnerBlockEntity.getCurrentPlayer() == null){
						serverLevel.sendParticles(ParticleTypes.EXPLOSION, spawnerBlockEntity.myEntity.getX(), spawnerBlockEntity.myEntity.getY(), spawnerBlockEntity.myEntity.getZ(), 1, 0.0D, 0.1D, 0.0D, 0.01);
						spawnerBlockEntity.myEntity.discard();
						spawnerBlockEntity.myEntity = null;
					} else if(spawnerBlockEntity.getCurrentPlayer().position().distanceTo(pPos.getCenter()) > homePointEntity.getHomeRadius()
							&& spawnerBlockEntity.getCurrentPlayer().position().distanceTo(pPos.getCenter()) < homePointEntity.getHomeRadius() + 3){
						spawnerBlockEntity.getCurrentPlayer().hurt(spawnerBlockEntity.myEntity.damageSources().magic(), 1);
						spawnerBlockEntity.getCurrentPlayer().displayClientMessage(DuelOfTheEndMod.getInfo("tip9"), true);
					}
				}

				//击败boss清空状态
				if(spawnerBlockEntity.myEntity == null || !spawnerBlockEntity.myEntity.isAlive()){
					spawnerBlockEntity.myEntity = null;
					spawnerBlockEntity.currentPlayer = null;
                }
			}
		}

	}

}
