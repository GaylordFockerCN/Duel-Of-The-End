package com.gaboj1.tcr.block.entity.spawner;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.ShadowableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;


import java.util.Objects;

public abstract class BossSpawnerBlockEntity<T extends Mob & ShadowableEntity> extends EntitySpawnerBlockEntity<T> {

	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, entityType, pos, state);
	}

    /**
	 * 召唤历战版
	 */
	public void tryToSpawnShadow(ServerPlayer player){
		if(!canSpawnShadow()){
			player.displayClientMessage(Component.literal("info.the_casket_of_reveries.cannot_spawn"), true);
			return;
		}
		if(!isReady){
			isReady = true;
			player.displayClientMessage(Component.literal("info.the_casket_of_reveries.sure_to_spawn"), true);
			return;
		}
		isReady = !spawnMyShadowBoss(player.serverLevel());
	}

	/**
	 * boss击败后才可以召唤历战
	 */
	public abstract boolean canSpawnShadow();

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

}
