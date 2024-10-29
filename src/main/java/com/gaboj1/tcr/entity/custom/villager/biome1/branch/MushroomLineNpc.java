package com.gaboj1.tcr.entity.custom.villager.biome1.branch;

import com.gaboj1.tcr.entity.custom.villager.TCRTalkableVillager;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MushroomLineNpc extends TCRTalkableVillager {
    public MushroomLineNpc(EntityType<? extends TCRTalkableVillager> pEntityType, Level pLevel, int skinID) {
        super(pEntityType, pLevel, skinID);
    }

    /**
     * 不会动
     */
    @Override
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            this.getLookControl().setLookAt(player);
            if (player instanceof ServerPlayer serverPlayer) {
                if (this.getConversingPlayer() == null) {
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), SaveUtil.biome1.toNbt()), serverPlayer);
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
