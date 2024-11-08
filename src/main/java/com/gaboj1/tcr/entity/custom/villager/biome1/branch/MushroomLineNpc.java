package com.gaboj1.tcr.entity.custom.villager.biome1.branch;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainStationaryVillager;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MushroomLineNpc extends PastoralPlainStationaryVillager {
    public MushroomLineNpc(EntityType<? extends PastoralPlainStationaryVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if(TCRConfig.NO_PLOT_MODE.get()){
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (hand == InteractionHand.MAIN_HAND) {
            if (player instanceof ServerPlayer serverPlayer) {
                this.lookAt(player, 180.0F, 180.0F);
                if (this.getConversingPlayer() == null) {
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), TCRArchiveManager.biome1.toNbt()), serverPlayer);
                    this.setConversingPlayer(serverPlayer);
                }
            }
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    /**
     * 关键npc可不能揍
     */
    @Override
    public boolean hurt(DamageSource source, float v) {
        return false;
    }

    public boolean realHurt(DamageSource source, float v) {
        return super.hurt(source, v);
    }

}
