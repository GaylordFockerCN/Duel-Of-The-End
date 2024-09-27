package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.villager.biome2.TalkableVillager2;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrialMaster extends YueShiLineNpc {

    DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(entityType);
    @Nullable
    private MiaoYin miaoYin;
    private boolean isAngry;

    public TrialMaster(EntityType<? extends TrialMaster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public boolean isAngry() {
        return isAngry;
    }

    @Override
    public String getResourceName() {
        return "trial_master";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.TRIAL_MASTER.get().getDescriptionId());
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide){
            if(SaveUtil.biome2.miaoYinTalked2 && SaveUtil.biome2.chooseEnd2){//给妙音万明珠而且选择了结局2
                //把叔父替换为妙音（妙音独自杀了叔父）TODO
            }
        }
        if(!isAngry){
            getNavigation().stop();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(senderData.getBoolean("whenDie")){
            builder.start(21)
                    .addChoice(18, 22)
                    .addChoice(19, 23)
                    .addFinalChoice(20, (byte) 4);
        } else if(!senderData.getBoolean("trialTalked1")){
            //初次对话
            builder.setAnswerRoot(
                    new TreeNode(dBuilder.buildDialogueAnswer(0))
                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(1),dBuilder.buildDialogueOption(0))
                                    .addChild(new TreeNode(dBuilder.buildDialogueAnswer(2),dBuilder.buildDialogueOption(1))
                                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(3),dBuilder.buildDialogueOption(2))
                                                    .addChild(new TreeNode(dBuilder.buildDialogueAnswer(4), dBuilder.buildDialogueOption(3))
                                                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(5),dBuilder.buildDialogueOption(4))
                                                                    .addChild(new TreeNode(dBuilder.buildDialogueAnswer(6),dBuilder.buildDialogueOption(5))
                                                                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(7),dBuilder.buildDialogueOption(6))
                                                                                    .execute((byte) -1)
                                                                                    .addChild(new TreeNode(dBuilder.buildDialogueAnswer(8),dBuilder.buildDialogueOption(7))
                                                                                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(9),dBuilder.buildDialogueOption(10))
                                                                                                    .addChild(new TreeNode(dBuilder.buildDialogueAnswer(10),dBuilder.buildDialogueOption(11))
                                                                                                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(11),dBuilder.buildDialogueOption(12))
                                                                                                                    .addChild(new TreeNode(dBuilder.buildDialogueAnswer(12),dBuilder.buildDialogueOption(13))
                                                                                                                            .addChild(new TreeNode(dBuilder.buildDialogueAnswer(13),dBuilder.buildDialogueOption(14))
                                                                                                                                    .addLeaf(dBuilder.buildDialogueOption(15), (byte) 1)
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                                    .addLeaf(dBuilder.buildDialogueOption(8), (byte) 2)
                                                                                    .addLeaf(dBuilder.buildDialogueOption(9), (byte) 2)
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        } else if(senderData.getBoolean("chooseEnd2")){
            builder.start(16)
                    .addChoice(16, 17)
                    .addFinalChoice(17, (byte) 3);
        }else {
            //初次对话后如果想再bb几句
            builder.start(14).addFinalChoice(6, (byte) -2);
        }

        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        EntityType<MiaoYin> miaoYinType = TCREntities.MIAO_YIN.get();
        switch (interactionID){
            case -2:
                break;
            case -1:
                if(!DataManager.wanMingPearlGot.get(player)) {
                    DataManager.wanMingPearlGot.put(player, true);
                    ItemUtil.addItem(player,TCRItems.WAN_MING_PEARL.get(),1);
                }
                return;
            case 1:
                //选择告诉试炼主盲女的位置
                SaveUtil.biome2.talkToMaster = true;
                talk(player, dBuilder.buildDialogueAnswer(14));
                discard();
                break;
            case 2:
                talk(player, dBuilder.buildDialogueAnswer(15));
                ItemUtil.addItem(player,TCRItems.DREAMSCAPE_COIN_PLUS.get(), 16);
                SaveUtil.biome2.trialTalked1 = true;
                break;
            case 3:
                miaoYin = TCREntities.MIAO_YIN.get().create(level());
                if(miaoYin != null){
                    miaoYin.setPos(this.position().add(player.position()).scale(0.5));
                    level().addFreshEntity(miaoYin);
                    miaoYin.getLookControl().setLookAt(this);
                    DialogueComponentBuilder.displayClientMessages(player, 2000, false,
                            ()->{
                                setConversingPlayer(null);
                                TalkableVillager2 guard1 = TCREntities.VILLAGER2_TALKABLE.get().create(level());
                                TalkableVillager2 guard2 = TCREntities.VILLAGER2_TALKABLE.get().create(level());
                                if(guard1 != null && guard2 != null){
                                    guard1.setSkinID(0);
                                    guard2.setSkinID(0);
                                    guard1.setPos(this.position());
                                    guard2.setPos(this.position());
                                    guard1.setTarget(player);
                                    guard2.setTarget(player);
                                }
                                isAngry = true;
                                setTarget(player);
                            },
                            dBuilder.buildDialogue(this, dBuilder.buildDialogueAnswer(18)),
                            dBuilder.buildDialogue(miaoYinType, dBuilder.buildDialogueAnswer(miaoYinType, 50)),
                            dBuilder.buildDialogue(this, dBuilder.buildDialogueAnswer(19)),
                            dBuilder.buildDialogue(miaoYinType, dBuilder.buildDialogueAnswer(miaoYinType, 51)),
                            dBuilder.buildDialogue(this, dBuilder.buildDialogueAnswer(20)));
                }
                return;
            case 4:
                DialogueComponentBuilder.displayClientMessages(player, 2000, false,
                        ()->{
                            SaveUtil.biome2.isBranchEnd = true;
                            setConversingPlayer(null);
                            player.displayClientMessage(dBuilder.buildDialogueAnswer(27), false);
                            SaveUtil.biome2.trialTalked2 = true;
                            if(miaoYin != null){
                                miaoYin.discard();
                            }
                            discard();
                        },
                        dBuilder.buildDialogue(this, dBuilder.buildDialogueAnswer(24)),
                        dBuilder.buildDialogue(miaoYinType, dBuilder.buildDialogueAnswer(miaoYinType, 53)),
                        dBuilder.buildDialogue(this, dBuilder.buildDialogueAnswer(25)),
                        dBuilder.buildDialogue(miaoYinType, dBuilder.buildDialogueAnswer(miaoYinType, 54)),
                        dBuilder.buildDialogue(this, dBuilder.buildDialogueAnswer(26)),
                        dBuilder.buildDialogue(miaoYinType, dBuilder.buildDialogueAnswer(miaoYinType, 55)));
                return;
        }
        setConversingPlayer(null);
    }

    @Override
    public boolean hurt(DamageSource source, float v) {
        if(SaveUtil.biome2.talkToMaster || isAngry){
            return super.realHurt(source, v);
        }
        return false;
    }

    @Override
    public void die(@NotNull DamageSource source) {
        setHealth(1);
        CompoundTag data = new CompoundTag();
        data.putBoolean("whenDie", true);
        isAngry = false;
        if(source.getEntity() instanceof ServerPlayer player){
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), data), player);

        } else if(!level().isClientSide){
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), data), ((ServerPlayer) level().getNearestPlayer(this, 32)));
        }
    }
}
