package com.gaboj1.tcr.block.entity;

import com.gaboj1.tcr.init.TCRModBlockEntities;
import com.gaboj1.tcr.init.TCRModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class PortalBedEntity extends BlockEntity {
    public PortalBedEntity(BlockPos pPos, BlockState pBlockState) {
        super(TCRModBlockEntities.PORTAL_BED.get(),pPos, pBlockState);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
