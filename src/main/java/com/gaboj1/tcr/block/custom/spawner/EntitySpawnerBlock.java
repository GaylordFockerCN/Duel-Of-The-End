package com.gaboj1.tcr.block.custom.spawner;

import com.gaboj1.tcr.block.entity.spawner.BossSpawnerBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.EntitySpawnerBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class EntitySpawnerBlock extends BaseEntityBlock {
    Supplier<BlockEntityType<? extends EntitySpawnerBlockEntity<?>>> blockEntityType;
    protected EntitySpawnerBlock(Properties pProperties, Supplier<BlockEntityType<? extends EntitySpawnerBlockEntity<?>>> blockEntityType) {
        super(pProperties);
        this.blockEntityType = blockEntityType;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, blockEntityType.get(), BossSpawnerBlockEntity::tick);
    }

}