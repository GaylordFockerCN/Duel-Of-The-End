package com.p1nero.dote.worldgen.structure;

import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

/**
 *
 * @author P1nero
 */
public enum DOTEStructurePoses {

    START_POINT(0, 0, 0);

    final Vec3i pos;

    DOTEStructurePoses(int x, int y, int z) {
        this.pos = new Vec3i(x, y, z);
    }

    public Vec3i getPos() {
        return pos;
    }

    public void teleportTo(ServerPlayer serverPlayer){
        if(serverPlayer.serverLevel().dimension() != DOTEDimension.P_SKY_ISLAND_LEVEL_KEY) {
            serverPlayer.changeDimension(Objects.requireNonNull(serverPlayer.serverLevel().getServer().getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY)), new DOTETeleporter(new BlockPos(this.pos)));
            return;
        }
        serverPlayer.teleportTo(pos.getX(), pos.getY(), pos.getZ());
    }

}
