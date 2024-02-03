package com.gaboj1.tcr.worldgen.portal;

import com.gaboj1.tcr.block.custom.PortalBed;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;
import java.util.function.Predicate;

public class TCRTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public TCRTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        int y = 61;

        if (!insideDimension) {
            y = thisPos.getY();
        }


        //destinationWorld.findNearestMapStructure();//TODO 出生在第一群系村庄

        //出生在第一群系
        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());
//        Predicate<Holder<Biome>> destinationBiome = biomeHolder -> biomeHolder.is(TCRBiomes.biome1);
//        BlockPos pos = destinationWorld.findClosestBiome3d(destinationBiome,destinationPos,1000,1000,1000).getFirst();
//        destinationPos.offset(pos);

        int tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getBlock() != Blocks.AIR) &&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                (destinationWorld.getBlockState(destinationPos.above()).getBlock()  != Blocks.AIR) &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)) {
            destinationPos = destinationPos.above(2);
            tries++;
        }

        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        if (insideDimension) {
            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(10).west(10),
                    destinationPos.above(10).east(10))) {
                if (destinationWorld.getBlockState(checkPos).getBlock() instanceof PortalBed) {
                    doSetBlock = false;
                    break;
                }
            }
            if (doSetBlock) {
                destinationWorld.setBlock(destinationPos, TCRModBlocks.PORTAL_BED.get().defaultBlockState(), 3);//TODO: 要不要在传送过去后生成传回来的床
            }
        }

        return entity;
    }
}