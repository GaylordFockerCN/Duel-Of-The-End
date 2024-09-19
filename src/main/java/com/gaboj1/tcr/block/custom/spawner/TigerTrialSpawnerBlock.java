package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.block.entity.spawner.TigerTrialSpawnerBlockEntity;
import com.gaboj1.tcr.entity.custom.biome2.TigerEntity;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TigerTrialSpawnerBlock extends EntitySpawnerBlock {
    public TigerTrialSpawnerBlock(Properties pProperties) {
        super(pProperties, TCRBlockEntities.TIGER_TRIAL_SPAWNER_BLOCK_ENTITY::get);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(!pLevel.isClientSide){
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(SaveUtil.biome2.miaoYinTalked1 && entity instanceof TigerTrialSpawnerBlockEntity blockEntity && !blockEntity.isTrialing() && !blockEntity.isTrialed()){
                for(int i = 0; i < 5 ; i++){
                    TigerEntity tiger = blockEntity.spawnMyBoss(((ServerLevel) pLevel));
                    pLevel.addFreshEntity(tiger);
                    tiger.setTarget(pPlayer);
                    blockEntity.addTiger(tiger);
                }
                pPlayer.displayClientMessage(Component.translatable("info.the_casket_of_reveries.trial_start"), true);
            } else {
                pPlayer.displayClientMessage(Component.translatable("info.the_casket_of_reveries.cannot_trial"), true);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, blockEntityType.get(), (
                (level1, blockPos, blockState, entitySpawnerBlockEntity) ->
                        TigerTrialSpawnerBlockEntity.tick(level1, blockPos, (TigerTrialSpawnerBlockEntity) entitySpawnerBlockEntity))
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new TigerTrialSpawnerBlockEntity(blockPos, blockState);
    }

}
