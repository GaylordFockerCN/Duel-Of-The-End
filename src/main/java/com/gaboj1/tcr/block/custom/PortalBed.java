package com.gaboj1.tcr.block.custom;

import com.gaboj1.tcr.block.entity.PortalBedEntity;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import com.gaboj1.tcr.worldgen.portal.TCRTeleporter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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

    //TODO 抄凋零玫瑰的，到时候换自己的特效
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        VoxelShape $$4 = this.getShape(pState, pLevel, pPos, CollisionContext.empty());
        Vec3 $$5 = $$4.bounds().getCenter();
        double $$6 = (double)pPos.getX() + $$5.x;
        double $$7 = (double)pPos.getZ() + $$5.z;

        for(int $$8 = 0; $$8 < 3; ++$$8) {
            if (pRandom.nextBoolean()) {
                pLevel.addParticle(ParticleTypes.PORTAL, $$6 + pRandom.nextDouble() / 5.0, (double)pPos.getY() + (0.5 - pRandom.nextDouble()), $$7 + pRandom.nextDouble() / 5.0, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PortalBedEntity(pos, state);
    }

}
