package com.gaboj1.tcr.worldgen.portal;

import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
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
        return repositionEntity.apply(false);
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        PortalInfo pos;
        BiomeMap biomeMap = BiomeMap.getInstance();
        int y = 100;
        BlockPos destinationPos = biomeMap.getBlockPos(biomeMap.getVillage1(),y);
        while (!destinationLevel.getBlockState(destinationPos).is(Blocks.AIR)){
            destinationPos = destinationPos.above();
        }
        destinationPos = destinationPos.offset(18,0,0);//不然会诞生在房子里（
        pos = new PortalInfo(destinationPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
        return pos == null ? ITeleporter.super.getPortalInfo(entity, destinationLevel, defaultPortalInfo) : pos;
    }
}