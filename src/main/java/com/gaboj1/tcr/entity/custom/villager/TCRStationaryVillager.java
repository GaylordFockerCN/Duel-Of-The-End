package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.NPCDialoguePacket;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class TCRStationaryVillager extends TCRVillager implements NpcDialogue {

    @Nullable
    protected Player conversingPlayer;
    public TCRStationaryVillager(EntityType<? extends Villager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel,1);
    }

    @Override
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {

    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)//最大血量
                .build();
    }
    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new NpcDialogueGoal<>(this));

    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide()) {
                this.lookAt(player, 180.0F, 180.0F);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(),serverPlayer.getPersistentData().copy()), serverPlayer);
                        this.setConversingPlayer(serverPlayer);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * 重写这里以调用对应的对话框
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverPlayerData) {

    }

    /**
     * 重写这里以处理对应的对话结束代码
     */
    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        this.setConversingPlayer(null);
    }


    @Override
    public void setConversingPlayer(@org.jetbrains.annotations.Nullable Player player) {
        this.conversingPlayer = player;
    }

    @Nullable
    @Override
    public Player getConversingPlayer() {
        return this.conversingPlayer;
    }

    @Override
    public void chat(Component component) {
        if(conversingPlayer != null) {
            conversingPlayer.sendSystemMessage(DialogueComponentBuilder.BUILDER.buildDialogue(this, component));
        }
    }
}
