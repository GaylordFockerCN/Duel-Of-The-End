package com.gaboj1.tcr.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BetterStructureBlock extends StructureBlock {

    public BetterStructureBlock(Properties pProperties) {
        super(BlockBehaviour.Properties.of());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BetterStructureBlockEntity(pPos, pState);
    }
}
