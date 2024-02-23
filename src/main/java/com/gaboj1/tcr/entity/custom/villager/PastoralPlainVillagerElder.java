package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.gui.screen.PastoralPlainVillagerElderDialogueScreen;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.packet.PastoralPlainVillagerElderDialoguePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PastoralPlainVillagerElder extends TCRVillager implements NpcDialogue {

    @Nullable
    private Player conversingPlayer;

    public PastoralPlainVillagerElder(EntityType<? extends Villager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public String getResourceName() {
        return "pastoral_plain_villager_elder";
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if ( !this.level().isClientSide()) {
                if (TCRConfig.IS_WHITE.get()) {
//                    if(TCRConfig.KILLED_BOSS1.get()){
//                        talk(player,Component.translatable(this.getDisplayName()+".hello1"));
//                    }else {
//                        talk(player,Component.translatable(this.getDisplayName()+".hello1"));
//                    }
                    this.lookAt(player, 180.0F, 180.0F);
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (this.getConversingPlayer() == null) {
                            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PastoralPlainVillagerElderDialoguePacket(this.getId()), serverPlayer);
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

//    @Override
//    public void talkFuck(Player player) {
//        player.sendSystemMessage(Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get().getDescriptionId()+".fuck_chat"));
//    }


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


//            case 0: // Responds to the player's question of where they are.
//                this.chat(player, Component.translatable("gui.aether.queen.dialog.answer"));
//                break;
//            case 1: // Tells the players nearby to ready up for a fight.
//                if (this.level().getDifficulty() == Difficulty.PEACEFUL) { // Check for peaceful mode.
//                    this.chat(player, Component.translatable("gui.aether.queen.dialog.peaceful"));
//                } else {
//                    if (player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get()) >= 10) { // Checks for Victory Medals.
//                        this.readyUp();
//                        int count = 10;
//                        for (ItemStack item : player.inventoryMenu.getItems()) {
//                            if (item.is(AetherItems.VICTORY_MEDAL.get())) {
//                                if (item.getCount() > count) {
//                                    item.shrink(count);
//                                    break;
//                                } else {
//                                    count -= item.getCount();
//                                    item.setCount(0);
//                                }
//                            }
//                            if (count <= 0) break;
//                        }
//                    } else {
//                        this.chat(player, Component.translatable("gui.aether.queen.dialog.no_medals"));
//                    }
//                }
//                break;
//            case 2: // Deny fight.
//                this.chat(player, Component.translatable("gui.aether.queen.dialog.deny_fight"));
//                break;
//            case 3:
//            default: // Goodbye.
//                this.chat(player, Component.translatable("gui.aether.queen.dialog.goodbye"));
//                break;
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
