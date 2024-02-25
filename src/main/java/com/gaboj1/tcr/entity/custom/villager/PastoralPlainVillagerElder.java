package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.gui.screen.PastoralPlainVillagerElderDialogueScreen;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.packet.NPCDialoguePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PastoralPlainVillagerElder extends TCRVillager implements NpcDialogue {

    @Nullable
    private Player conversingPlayer;

    public PastoralPlainVillagerElder(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public String getResourceName() {
//        return "pastoral_plain_villager_elder";
        return "pastoral_plain_villager";
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 6.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new NpcDialogueGoal<>(this));//防乱跑
        super.registerGoals();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if ( !this.level().isClientSide()) {
                if (TCRConfig.IS_WHITE.get()) {
                    this.lookAt(player, 180.0F, 180.0F);
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (this.getConversingPlayer() == null) {
                            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId()), serverPlayer);
                            this.setConversingPlayer(serverPlayer);
                        }
                    }
                } else {
                    talk(player,Component.translatable(""));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen() {
        Minecraft.getInstance().setScreen(new PastoralPlainVillagerElderDialogueScreen(this));
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID) {
            case 0: //白方未击败boss
                this.chat(Component.translatable("0"));
                break;
            case 1: //白方 击败boss
                this.chat(Component.translatable("1"));
                break;
            case 2: //黑方 未击败boss
                this.chat(Component.translatable("2"));
                break;
            case 3: //黑方 击败boss
                this.chat(Component.translatable("3"));
                break;
        }
        this.setConversingPlayer(null);
    }

    public void chat(Component component){
        this.talk(conversingPlayer,component);
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

}
