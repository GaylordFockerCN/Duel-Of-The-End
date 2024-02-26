package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.gui.screen.PastoralPlainVillagerElderDialogueScreen;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.NPCDialoguePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

/**
 * 固定位置NPC，能对话但是不移动
 */
public class Stationary extends PathfinderMob implements NpcDialogue {

    @Nullable
    Player conversingPlayer;

    protected Stationary(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 0)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 0)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0)//移速
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
        switch (interactionID) {
            case 0:
                this.chat(Component.translatable("0"));
                break;
            case 1:
                this.chat(Component.translatable("1"));
                break;
            case 2:
                this.chat(Component.translatable("2"));
                break;
            case 3:
                this.chat(Component.translatable("3"));
                break;
        }
        this.setConversingPlayer(null);
    }


    @Override
    public void setConversingPlayer(@org.jetbrains.annotations.Nullable Player player) {
        this.conversingPlayer = player;
    }

    @javax.annotation.Nullable
    @Override
    public Player getConversingPlayer() {
        return this.conversingPlayer;
    }

    @Nullable
    @Override
    public void chat(Component component) {

    }
}
