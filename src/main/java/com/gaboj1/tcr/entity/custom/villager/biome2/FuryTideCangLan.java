package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

/**
 * 苍澜：放水球
 */
public class FuryTideCangLan extends Master implements NpcDialogue {
    protected Player conversingPlayer;
    public static final EntityDataAccessor<Integer> BOSS_ID = SynchedEntityData.defineId(FuryTideCangLan.class, EntityDataSerializers.INT);

    public FuryTideCangLan(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .build();
    }

    public void sendDialoguePacket(ServerPlayer serverPlayer){
        CompoundTag serverData = new CompoundTag();
        serverData.putBoolean("isElderTalked",SaveUtil.biome2.isElderTalked);
        serverData.putBoolean("isBossDie",SaveUtil.biome2.isBossDie);
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), serverData), serverPlayer);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openDialogueScreen(CompoundTag serverData) {
        if(this.getTarget() != null){
            return;
        }
        BiomeMap biomeMap = BiomeMap.getInstance();
        BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
        BlockPos biome3Center = biomeMap.getBlockPos(biomeMap.getCenter3(),0);
        String position1 = "("+ biome1Center.getX()+", "+biome1Center.getZ()+")";
        String position3 = "("+ biome3Center.getX()+", "+biome3Center.getZ()+")";


        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(serverData.getBoolean("isElderTalked")){
            //初次见面
            builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(entityType,1))
                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,2),BUILDER.buildDialogueOption(entityType,0))
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,0))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4),BUILDER.buildDialogueOption(entityType,1))
                                            .addLeaf(BUILDER.buildDialogueOption(entityType,2),(byte)1)
                                            .addLeaf(BUILDER.buildDialogueOption(entityType,3),(byte)2)))
                                            ));
        } else if(serverData.getBoolean("isBossDie")){
            //击败boss
            builder.start(BUILDER.buildDialogueAnswer(entityType,7))
                    .addChoice(BUILDER.buildDialogueOption(entityType,4),BUILDER.buildDialogueAnswer(entityType,8))
                    .addChoice(BUILDER.buildDialogueOption(entityType,5),BUILDER.buildDialogueAnswer(entityType,9))
                    .addChoice(BUILDER.buildDialogueOption(entityType,6),Component.literal("\n").append(Component.translatable(entityType + ".dialog10",position1,position3)))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)6666);
        } else {
            //打断boss说话
            builder.start(11)
                    .addFinalChoice(0, (byte) 3);
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

        BiomeMap biomeMap = BiomeMap.getInstance();
        BlockPos biome2Center = biomeMap.getBlockPos(biomeMap.getCenter2(),0);
        String position = "("+ biome2Center.getX()+", "+biome2Center.getZ()+")";

        switch(interactionID) {
            case 1:
                player.displayClientMessage(Component.literal("\n").append(Component.translatable(entityType + ".dialog5",position)),false);
                break;
            case 2:
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType,6),false);
                break;
            case 3:
                //继续boss的对话
                int bossId = getEntityData().get(BOSS_ID);
                if(level().getEntity(getEntityData().get(BOSS_ID)) instanceof SecondBossEntity){
                    CompoundTag serverData = new CompoundTag();
                    serverData.putBoolean("fromCangLan", true);
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(bossId, serverData), ((ServerPlayer) player));
                }
                break;
        }
        this.setConversingPlayer(null);

    }


    @Override
    public void setConversingPlayer(@Nullable Player player) {
            this.conversingPlayer = player;
    }

    @Nullable
    @Override
    public Player getConversingPlayer() {
        return this.conversingPlayer;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand pHand) {
        if (pHand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide()) {
                this.lookAt(player, 180.0F, 180.0F);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        this.setConversingPlayer(serverPlayer);
                        sendDialoguePacket(serverPlayer);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void chat(Component component) {
        if(conversingPlayer != null) {
            conversingPlayer.sendSystemMessage(BUILDER.buildDialogue(this, component));
        }
    }

    @Override
    public String getResourceName() {
        return "cang_lan";
    }

    @Override
    public void playAttackAnim() {
        triggerAnim("Summon","summon");
    }

}
