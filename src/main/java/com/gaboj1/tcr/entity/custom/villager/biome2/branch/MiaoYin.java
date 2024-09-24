package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.BookManager;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class MiaoYin extends YueShiLineNpc {
    public static final EntityDataAccessor<Boolean> IS_SITTING = SynchedEntityData.defineId(MiaoYin.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> SIT_ROT = SynchedEntityData.defineId(MiaoYin.class, EntityDataSerializers.FLOAT);
    public MiaoYin(EntityType<? extends MiaoYin> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, -1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(IS_SITTING, false);
        getEntityData().define(SIT_ROT, 0F);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        getEntityData().set(IS_SITTING, tag.getBoolean("is_sitting"));
        getEntityData().set(SIT_ROT, tag.getFloat("sit_rot"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("is_sitting", getEntityData().get(IS_SITTING));
        tag.putFloat("sit_rot", getEntityData().get(SIT_ROT));
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public String getResourceName() {
        return "miao_yin";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.MIAO_YIN.get().getDescriptionId());
    }

    public void setIsSitting(boolean sit) {
        getEntityData().set(IS_SITTING, sit);
        if(sit){
            getEntityData().set(SIT_ROT, yBodyRot);
        }
    }

    public boolean isSitting(){
        return getEntityData().get(IS_SITTING);
    }

    @Override
    public void tick() {
        super.tick();
        getNavigation().stop();//盲人行动不便很合理吧
        if(!level().isClientSide){
            //泄密后把乐师换成流浪者
            if(SaveUtil.biome2.talkToMaster){
                Wanderer wanderer = TCREntities.WANDERER.get().create(level());
                assert wanderer != null;
                wanderer.setPos(this.position());
                level().addFreshEntity(wanderer);
                this.discard();
            }
        } else {
            if(isSitting()){
                setYBodyRot(getEntityData().get(SIT_ROT));
            }
        }
    }

    /**
     * 企图防止头旋转360
     */
    @Override
    public void setYHeadRot(float rot) {
        if(Math.abs(rot -yBodyRot) > 90){
            return;
        }
        super.setYHeadRot(rot);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);

        TreeNode sameNode1 = new TreeNode(BUILDER.buildDialogueAnswer(entityType,12),BUILDER.buildDialogueOption(entityType,14))
                .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,13),BUILDER.buildDialogueOption(entityType,15))
                        .addLeaf(BUILDER.buildDialogueOption(entityType,16), (byte) 5)//接取任务
                );

        if(senderData.getBoolean("isBranchEnd")){
            builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(entityType,81))
                    .addLeaf(BUILDER.buildDialogueOption(entityType,70), (byte) 13)//借钱
                    .addLeaf(BUILDER.buildDialogueOption(entityType,71), (byte) 14)//问候
                    .addLeaf(BUILDER.buildDialogueOption(entityType,72), (byte) -114514));//离去
        } else if(senderData.getBoolean("stolenMiaoYin")){
            //盗窃惩罚。如果盗窃则不能再接任务只能施舍
            builder.start(0)
                    .addFinalChoice(1, (byte) -2);
        } else if(!senderData.getBoolean("miaoYinTalked1")){
            //初次对话
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,0))
                            .addLeaf(BUILDER.buildDialogueOption(entityType,1), (byte) 1)//施舍
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,2),BUILDER.buildDialogueOption(entityType,0))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,2))
                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4),BUILDER.buildDialogueOption(entityType,3))
                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,4), (byte) 2)//偷钱
                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,5), BUILDER.buildDialogueOption(entityType,5))
                                                            .execute((byte) -1)
                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,6),BUILDER.buildDialogueOption(entityType,6))
                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,7),BUILDER.buildDialogueOption(entityType,7))
                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,8),BUILDER.buildDialogueOption(entityType,8))
                                                                                    .addLeaf(BUILDER.buildDialogueOption(entityType,9), (byte) 3)//离去
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,9),BUILDER.buildDialogueOption(entityType,10))
                                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,11), (byte) 4)//我喜欢你
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,11),BUILDER.buildDialogueOption(entityType,12))
                                                                                                    .addChild(sameNode1)
                                                                                            )
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,11),BUILDER.buildDialogueOption(entityType,13))
                                                                                                    .addChild(sameNode1)
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        } else if(!senderData.getBoolean("shangRenTalked") && !senderData.getBoolean("afterTrial")){
            //接任务后再次对话，但还没和商人对话也没通过试炼
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,11))
                            .addChild(sameNode1)
            );
        } else if(senderData.getBoolean("shangRenTalked") && !senderData.getBoolean("trialTalked1")){
            //和商人对话后
            builder.start(16)
                    .addChoice(17, 17)
                    .addChoice(18, 18)
                    .addFinalChoice(19, (byte) 6);
        } else if(senderData.getBoolean("trialTalked1") && !senderData.getBoolean("miaoYinTalked2")){
            //取得夜明珠后
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,20))
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,21),BUILDER.buildDialogueOption(entityType,20))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,22),BUILDER.buildDialogueOption(entityType,21))
                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,23),BUILDER.buildDialogueOption(entityType,22))
                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,24),BUILDER.buildDialogueOption(entityType,23))
                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,25),BUILDER.buildDialogueOption(entityType,24))
                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,26),BUILDER.buildDialogueOption(entityType,25))
                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,27),BUILDER.buildDialogueOption(entityType,26))
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,28),BUILDER.buildDialogueOption(entityType,27))
                                                                                            .execute((byte) 7)//记录选择
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,29),BUILDER.buildDialogueOption(entityType,29))
                                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,30),BUILDER.buildDialogueOption(entityType,30))
                                                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,31), (byte) 9)
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,62),BUILDER.buildDialogueOption(entityType,28))
                                                                                            .execute((byte) 8)//记录选择
                                                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,63),BUILDER.buildDialogueOption(entityType,53))
                                                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,54), (byte) 12)
                                                                                                    )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        } else if(senderData.getBoolean("miaoYinTalked2") && senderData.getBoolean("chooseEnd2")){
            //第二段对话（隔了一天，已经是月圆之夜了）
            builder.start(33)
                    .addChoice(32, 34)
                    .addChoice(33, 35)
                    .addChoice(34, 36)
                    .addChoice(35, 37)
                    .addChoice(36, 38)
                    .addChoice(37, 39)
                    .addChoice(38, 40)
                    .addChoice(39, 41)
                    .addChoice(40, 42)
                    .addChoice(41, 43)
                    .addChoice(42, 44)
                    .addChoice(43, 45)
                    .addChoice(44, 46)
                    .addChoice(45, 47)
                    .addFinalChoice(46, (byte) 10);
        } else if(senderData.getBoolean("trialTalked2")){
            //剧情结束后
            builder.start(56)
                    .addChoice(47, 57)
                    .addChoice(48, 58)
                    .addChoice(49, 59)
                    .addChoice(50, 60)
                    .addChoice(51, 61)
                    .addFinalChoice(52, (byte) 11);
        } else {
            //选择了结局3，在试炼主人家的对话
            builder.start(65)
                    .addChoice(56, 66)
                    .addChoice(57, 67)
                    .addChoice(58, 68)
                    .addChoice(59, 69)
                    .addChoice(59, 70)
                    .addChoice(60, 71)
                    .addChoice(61, 72)
                    .addChoice(62, 73)
                    .addChoice(63, 74)
                    .addChoice(64, 75)
                    .addChoice(59, 76)
                    .addChoice(65, 77)
                    .addChoice(66, 78)
                    .addChoice(67, 79)
                    .addChoice(68, 80)
                    .addFinalChoice(69, (byte) 111);
        }

        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case -2:
                //偷后的赎罪
                ItemUtil.searchAndConsumeItem(player, TCRItems.DREAMSCAPE_COIN.get(), 17);
                DataManager.stolenMiaoYin.put(player, false);
                break;
            case -1:
                //对话过程中施舍
                ItemUtil.searchAndConsumeItem(player, TCRItems.DREAMSCAPE_COIN.get(), 17);
                return;
            case 1:
                chat(BUILDER.buildDialogueAnswer(entityType, 1));
                ItemUtil.searchAndConsumeItem(player, TCRItems.DREAMSCAPE_COIN.get(), 17);
                break;
            case 2:
                if(!DataManager.stolenMiaoYin.get(player)){
                    DataManager.stolenMiaoYin.put(player, true);
                    player.addItem(TCRItems.DREAMSCAPE_COIN.get().getDefaultInstance().copyWithCount(17));
                    chat(BUILDER.buildDialogueAnswer(entityType, 0));
                } else {
                    player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, -1), true);
                }
                break;
            case 0, 3:
                chat(BUILDER.buildDialogueAnswer(entityType, 0));
                break;
            case 4:
                chat(BUILDER.buildDialogueAnswer(entityType, 10));
                break;
            case 5:
                chat(BUILDER.buildDialogueAnswer(entityType, 14));
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 15), false);//任务提示
                SaveUtil.biome2.miaoYinTalked1 = true;
                break;
            case 6:
                chat(BUILDER.buildDialogueAnswer(entityType, 19));
                if(!DataManager.isMiaoYinGifted.get(player)){
                    DataManager.isMiaoYinGifted.put(player, true);
                    player.addItem(TCRItems.GOLDEN_WIND_AND_DEW.get().getDefaultInstance());
                }
                break;
            case 7:
                SaveUtil.biome2.chooseEnd2 = true;
                return;
            case 8:
                SaveUtil.biome2.chooseEnd2 = false;
                return;
            case 9:
                //初段对话，等隔天再来（即下次对话）
                chat(BUILDER.buildDialogueAnswer(entityType,31, false));
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType,32, true), false);
                SaveUtil.biome2.miaoYinTalked2 = true;
                break;
            case 10:
                //出发
                chat(BUILDER.buildDialogueAnswer(entityType,48, false));
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType,49, true), false);
                discard();
                break;
            case 11:
                //给予琵琶，结局2
                if(!player.addItem(TCRItems.PI_PA.get().getDefaultInstance())){
                    ItemUtil.addItemEntity(player, TCRItems.PI_PA.get(), 1);
                }
                TCRAdvancementData.getAdvancement("kill_shu_fu", ((ServerPlayer) player));
                SaveUtil.biome2.isBranchEnd = true;
                break;
            case 111:
                //给予琵琶，结局3
                if(!player.addItem(TCRItems.PI_PA.get().getDefaultInstance())){
                    ItemUtil.addItemEntity(player, TCRItems.PI_PA.get(), 1);
                }
                TCRAdvancementData.getAdvancement("kill_shu_fu2", ((ServerPlayer) player));
                SaveUtil.biome2.isBranchEnd = true;
                break;
            case 12:
                chat(BUILDER.buildDialogueAnswer(entityType,64));
                SaveUtil.biome2.miaoYinTalked2 = true;
                discard();
                ItemEntity item = new ItemEntity(level(), getX(), getY(), getZ(), BookManager.MIAO_YIN_MESSAGE.get());
                level().addFreshEntity(item);
                break;
            case 13:
                if(DataManager.miaoYinMoney1.get(player) && DataManager.miaoYinMoney1.isLocked(player)){
                    chat(BUILDER.buildDialogueAnswer(entityType,84));
                } else {
                    if(DataManager.miaoYinMoney1.get(player)){
                        DataManager.miaoYinMoney1.lock(player);
                        player.addItem(new ItemStack(TCRItems.DREAMSCAPE_COIN_PLUS.get(), 14));
                    } else {
                        player.addItem(new ItemStack(TCRItems.DREAMSCAPE_COIN_PLUS.get(), 13));
                    }
                    DataManager.miaoYinMoney1.put(player, true);
                    chat(BUILDER.buildDialogueAnswer(entityType,82));
                }
                break;
            case 14:
                chat(BUILDER.buildDialogueAnswer(entityType,83));
                break;
        }
        setConversingPlayer(null);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(player.isCreative() && player.isShiftKeyDown()){
            setIsSitting(!isSitting());
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        if(DataManager.stolenMiaoYin.get(player)){
            if (hand == InteractionHand.MAIN_HAND) {
                this.getLookControl().setLookAt(player);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        CompoundTag data = new CompoundTag();
                        data.putBoolean("stolenMiaoYin", true);
                        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), data), serverPlayer);
                        this.setConversingPlayer(serverPlayer);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Guodu", 10, state -> PlayState.STOP)
                .triggerableAnim("guodu", RawAnimation.begin().thenPlay("animation.unknown.miao_yin_guodu")));
    }

    protected <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.move", Animation.LoopType.LOOP));
        } else {
            if(isSitting()){
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.unknown.miao_yin_idle", Animation.LoopType.LOOP));
            } else {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.unknown.miao_yin_idle2", Animation.LoopType.LOOP));
            }
        }

        return PlayState.CONTINUE;
    }

}
