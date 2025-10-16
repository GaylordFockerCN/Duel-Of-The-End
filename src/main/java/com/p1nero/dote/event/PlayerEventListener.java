package com.p1nero.dote.event;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.SyncArchivePacket;
import com.p1nero.dote.network.packet.clientbound.SyncUuidPacket;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
public class PlayerEventListener {

    // 存储玩家进入维度前的位置
    private static final Map<UUID, ReturnPosition> playerReturnPositions = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().dimension().equals(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY)) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PacketRelay.sendToPlayer(DOTEPacketHandler.INSTANCE, new SyncArchivePacket(DOTEArchiveManager.toNbt()), serverPlayer);
            DOTEBoss.SERVER_BOSSES.forEach(((uuid, integer) -> PacketRelay.sendToPlayer(DOTEPacketHandler.INSTANCE, new SyncUuidPacket(uuid, integer), serverPlayer)));
        } else {
            if (DOTEArchiveManager.isAlreadyInit()) {
                PacketRelay.sendToServer(DOTEPacketHandler.INSTANCE, new SyncArchivePacket(DOTEArchiveManager.toNbt()));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {

    }

    @SubscribeEvent
    public static void enterBiome(TickEvent.PlayerTickEvent event) {

    }

    @SubscribeEvent
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event) {

    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = player.level();
        ItemStack item = event.getItemStack();

        if (item.is(DOTEItems.DUEL_KEY.get()) && !level.dimension().equals(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY)) {
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                ServerLevel targetLevel = serverPlayer.server.getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY);

                if (targetLevel != null) {
                    // 记录玩家当前位置和维度
                    ReturnPosition returnPos = new ReturnPosition(
                            serverPlayer.getX(),
                            serverPlayer.getY(),
                            serverPlayer.getZ(),
                            serverPlayer.getYRot(),
                            serverPlayer.getXRot(),
                            serverPlayer.level().dimension()
                    );
                    playerReturnPositions.put(serverPlayer.getUUID(), returnPos);

                    serverPlayer.teleportTo(
                            targetLevel,
                            597.0,
                            217.0,
                            64.0,
                            serverPlayer.getYRot(),
                            serverPlayer.getXRot()
                    );
                }
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        BlockPos pos = event.getPos();

        if (level.getBlockState(pos).is(Blocks.BEACON) && level.dimension().equals(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY)) {
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                ReturnPosition returnPos = playerReturnPositions.get(serverPlayer.getUUID());

                if (returnPos != null) {
                    ServerLevel targetLevel = serverPlayer.server.getLevel(returnPos.dimension);

                    if (targetLevel != null) {
                        event.setCanceled(true);

                        serverPlayer.teleportTo(
                                targetLevel,
                                returnPos.x,
                                returnPos.y,
                                returnPos.z,
                                returnPos.yRot,
                                returnPos.xRot
                        );

                        // 移除记录的位置
                        playerReturnPositions.remove(serverPlayer.getUUID());
                    }
                } else {

                    ServerLevel overworld = serverPlayer.server.getLevel(Level.OVERWORLD);

                    if (overworld != null) {
                        event.setCanceled(true);
                        BlockPos spawnPos = overworld.getSharedSpawnPos();

                        serverPlayer.teleportTo(
                                overworld,
                                spawnPos.getX() + 0.5,
                                spawnPos.getY() + 1,
                                spawnPos.getZ() + 0.5,
                                serverPlayer.getYRot(),
                                serverPlayer.getXRot()
                        );
                    }
                }
            } else {
                event.setCanceled(true);
            }
        }
    }

        private record ReturnPosition(double x, double y, double z, float yRot, float xRot, ResourceKey<Level> dimension) {
    }
}

