package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.server.NPCDialoguePacketWithSkinID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 可对话且可贸易的村民，贸易无法升级（懒得研究升级系统，直接在要交易时插入自己的货物表，导致ban掉贸易升级机制）
 * @author LZY
 */
public class TCRTalkableVillager extends TCRVillager implements NpcDialogue {

    @Nullable
    protected Player conversingPlayer;

    protected boolean alreadyAddTrade = false;

    public TCRTalkableVillager(EntityType<? extends TCRTalkableVillager> pEntityType, Level pLevel, int skinID) {
        super(pEntityType, pLevel,skinID);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putBoolean("alreadyAddTrade",alreadyAddTrade);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        alreadyAddTrade = tag.getBoolean("alreadyAddTrade");
        super.load(tag);
    }

    /**
     * 带大脑的生物Goal会失效，所以只能在tick中实现这个操作
     */
    @Override
    public void tick() {
        super.tick();
        if(this.conversingPlayer!=null){
            this.navigation.stop();
        }
    }

    /**
     * 会讲话的NPC应该显示名字
     */
    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(player.isCreative() ){//潜行右键切换村民种类，客户端服务端都需要改变。单单右键则输出当前id
            if(player.isShiftKeyDown()){
                skinID++;
                if(skinID >= TCRVillager.MAX_TYPES){
                    skinID = -TCRVillager.MAX_FEMALE_TYPES;//无法区分0 和 -0
                }
            }
            player.sendSystemMessage(Component.literal("current skin ID "+(level().isClientSide?"Client:":"Server:")+ skinID));
            return InteractionResult.SUCCESS;
        }
        if (hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide()) {
                this.lookAt(player, 180.0F, 180.0F);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacketWithSkinID(this.getId(),serverPlayer.getPersistentData().copy(),skinID), serverPlayer);
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
            conversingPlayer.displayClientMessage(DialogueComponentBuilder.BUILDER.buildDialogue(this, component),false);
        }
    }

    public void startCustomTrade(Player player, MerchantOffer... merchantOffers){
        if(!alreadyAddTrade){
            for(MerchantOffer offer:merchantOffers){
                this.getOffers().add(offer);
            }
        }
        alreadyAddTrade = true;

        this.setTradingPlayer(player);
        this.openTradingScreen(player, this.getDisplayName(), this.getVillagerData().getLevel());
    }

    /**
     * 为了防止交易被中断而强制不让它取消。。
     */
    @Override
    protected void stopTrading() {

    }

    /**
     * 需要带上skinID
     * @return 带skinID的名称（译名）
     */
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get().getDescriptionId()+skinID);
    }

}
