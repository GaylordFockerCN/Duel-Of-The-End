package com.gaboj1.tcr.block.custom;

import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import com.gaboj1.tcr.worldgen.portal.TCRTeleporter;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
public class PortalBed extends BedBlock {
    public PortalBed(DyeColor pColor, Properties pProperties) {
        super(pColor, pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            if (pPlayer.level() instanceof ServerLevel serverlevel && canSetSpawn(serverlevel) && !(Boolean)pState.getValue(OCCUPIED) ) {

//                pPlayer.startSleepInBed(pPos);//TODO: 不知道会不会报错

                MinecraftServer minecraftserver = serverlevel.getServer();
                ResourceKey<Level> resourcekey = pPlayer.level().dimension() == TCRDimension.SKY_ISLAND_LEVEL_KEY ?
                        Level.OVERWORLD : TCRDimension.SKY_ISLAND_LEVEL_KEY;

                ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
                if (portalDimension != null && !pPlayer.isPassenger()) {
                    if(resourcekey == TCRDimension.SKY_ISLAND_LEVEL_KEY) {
                        pPlayer.changeDimension(portalDimension, new TCRTeleporter(pPos, true));
                    } else {
                        pPlayer.changeDimension(portalDimension, new TCRTeleporter(pPos, false));
                    }
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

}
