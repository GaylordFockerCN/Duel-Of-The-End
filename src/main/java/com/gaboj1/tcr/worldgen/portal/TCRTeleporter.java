package com.gaboj1.tcr.worldgen.portal;

import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

    /**
     * 在这里实现出生点。
     * 不要相信IDE，pos==null判空是必须的！排查了很久！
     * @param entity The entity teleporting before the teleport
     * @param destinationLevel The world the entity is teleporting to
     * @param defaultPortalInfo A reference to the vanilla method for getting portal info. You should implement your own logic instead of using this
     *
     * @return 更新后的传送信息。
     */
    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        PortalInfo pos;
        BiomeMap biomeMap = BiomeMap.getInstance();
        int y = 100;
        BlockPos destinationPos = biomeMap.getBlockPos(biomeMap.getVillage1(),y);
        while (!destinationLevel.getBlockState(destinationPos).is(Blocks.AIR)){
            destinationPos = destinationPos.above();
        }
        destinationPos = destinationPos.offset(20,70,0);//偏移一下不然会诞生在房子里（
        if(entity instanceof ServerPlayer player){
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 700, 1, false, true));
        }
        pos = new PortalInfo(destinationPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
        //NOTE不要相信IDE，这里判空是必须的！
        return pos == null ? ITeleporter.super.getPortalInfo(entity, destinationLevel, defaultPortalInfo) : pos;
    }
}