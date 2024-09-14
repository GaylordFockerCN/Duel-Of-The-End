package com.gaboj1.tcr.worldgen.portal;

import com.gaboj1.tcr.util.DataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
    public BlockPos thisPos;
    public boolean insideDimension;

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
//        BiomeMap biomeMap = BiomeMap.getInstance();
        int y = 100;
//        BlockPos thisPos = biomeMap.getBlockPos(biomeMap.getVillage1(), y);
        while (!destinationLevel.getBlockState(thisPos).is(Blocks.AIR)){
            thisPos = thisPos.above();
        }
        thisPos = thisPos.offset(5,70,0);//偏移一下不然会诞生在房子里（
        if(entity instanceof ServerPlayer player){
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 220, 1, false, true));
            if(DataManager.isFirstEnter.getBool(player)){
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.first_enter"), true);
                DataManager.isFirstEnter.putBool(player, false);
            }
        }
        pos = new PortalInfo(thisPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
        //NOTE不要相信IDE，这里判空是必须的！
        return pos == null ? ITeleporter.super.getPortalInfo(entity, destinationLevel, defaultPortalInfo) : pos;
    }
}