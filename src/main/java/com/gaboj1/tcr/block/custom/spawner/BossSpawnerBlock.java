package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.entity.spawner.BossSpawnerBlockEntity;
import com.gaboj1.tcr.client.DOTESounds;
import com.gaboj1.tcr.item.DOTEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class BossSpawnerBlock extends BaseEntityBlock {
    Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType;
    protected BossSpawnerBlock(Properties pProperties, Supplier<BlockEntityType<? extends BossSpawnerBlockEntity<?>>> blockEntityType) {
        super(pProperties);
        this.blockEntityType = blockEntityType;
    }

    /**
     * 用往生精华右键以召唤boss
     */
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof BossSpawnerBlockEntity<?> bossSpawnerBlockEntity){
            if(!bossSpawnerBlockEntity.isSpawned() && pPlayer.getItemInHand(pHand).is(DOTEItems.IMMORTALESSENCE.get())){
                pPlayer.getItemInHand(pHand).shrink(1);
                if(pLevel instanceof ServerLevel serverLevel){
                    bossSpawnerBlockEntity.spawnMyBoss(serverLevel);
                    serverLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), DOTESounds.LOTUSHEAL.get(), SoundSource.BLOCKS, 1, 1);
                }
                for(int i = 0; i < 10; i++){
                    double rx = pPos.getX() + pLevel.getRandom().nextFloat();
                    double ry = pPos.getY() + pLevel.getRandom().nextFloat();
                    double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
                    pLevel.addParticle(ParticleTypes.SOUL, rx, ry + 1.2F, rz, 0.0D, 0.01D, 0.0D);
                }
            } else {
                pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip1").append(bossSpawnerBlockEntity.getEntityType().getDescription()), true);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    /**
     * 重置可召唤状态用
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, blockEntityType.get(), BossSpawnerBlockEntity::tick);
    }

}
