package com.p1nero.dote.worldgen.portal;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
public class DOTETeleporter implements ITeleporter {
    @NotNull
    public BlockPos thisPos;

    public DOTETeleporter(@NotNull BlockPos pos) {
        thisPos = pos;
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
        while (!destinationLevel.getBlockState(thisPos).is(Blocks.AIR)){
            thisPos = thisPos.above();
        }
        if(entity instanceof ServerPlayer player){
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 1, false, true));
        }
        pos = new PortalInfo(thisPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
        return pos;
    }
}