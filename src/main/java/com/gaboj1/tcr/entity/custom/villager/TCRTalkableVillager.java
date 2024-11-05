package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

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
        tag.putBoolean("alreadyAddTrade", alreadyAddTrade);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        alreadyAddTrade = tag.getBoolean("alreadyAddTrade");
        super.load(tag);
    }

    /**
     * 会讲话的NPC应该显示名字
     */
    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if(TCRConfig.NO_PLOT_MODE.get()){
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if(player.isCreative() ){//潜行右键切换村民种类，客户端服务端都需要改变。单单右键则输出当前id
            if(player.isShiftKeyDown()){
                setSkinID(getSkinID()+1);
                if(getSkinID() >= getMaleTypeCnt()){
                    setSkinID(-getFemaleTypeCnt());//无法区分0 和 -0
                }
                player.sendSystemMessage(Component.literal("skin ID has changed to：" + getSkinID() + "; name:" + getDisplayName().getString()));
            } else {
                player.sendSystemMessage(Component.literal("current skin ID：" + getSkinID() + "; name:" + getDisplayName().getString()));
            }
            return InteractionResult.SUCCESS;
        }
        if (hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide()) {
                this.lookAt(player, 180.0F, 180.0F);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), DataManager.getTCRPlayer(serverPlayer).getData()), serverPlayer);
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

    /**
     * 开始贸易。
     * 首先会清空所有交易物品，再进行添加。为的是防止村民碰到工作方块而自动添加交易物品
     * @param player 交易对象
     * @param merchantOffers 交易货物表
     */
    public void startCustomTrade(Player player, MerchantOffer... merchantOffers){
        this.getOffers().clear();
        for(MerchantOffer offer:merchantOffers){
            this.getOffers().add(offer);
        }

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
        return Component.translatable(entityType.getDescriptionId() + getSkinID());
    }

}
