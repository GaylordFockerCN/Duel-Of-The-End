package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.util.BookManager;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

public class PastoralPlainVillagerElder extends TCRVillager implements NpcDialogue {

    @Nullable
    private Player conversingPlayer;
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    public PastoralPlainVillagerElder(EntityType<? extends PastoralPlainVillagerElder> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, -114514);
//        this.setItemInHand(InteractionHand.MAIN_HAND,TCRModItems.ELDER_STAFF.get().getDefaultInstance());
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 10.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
        super.registerGoals();
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
     * 实现血条显示控制
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide()) {
            this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossInfo.removePlayer(serverPlayer);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        this.setItemInHand(InteractionHand.MAIN_HAND, TCRModItems.ELDER_STAFF.get().getDefaultInstance());
//        System.out.println(this.getItemInHand(InteractionHand.MAIN_HAND)+"client?"+this.isClientSide());
        if (hand == InteractionHand.MAIN_HAND) {
            if ( !this.level().isClientSide()) {
//                if (DataManager.isWhite.getBool((ServerPlayer)player)) {
                    this.lookAt(player, 180.0F, 180.0F);
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (this.getConversingPlayer() == null) {
                            CompoundTag serverData = new CompoundTag();
                            serverData.putBoolean("isElderTalked", SaveUtil.biome1.isElderTalked);
                            serverData.putBoolean("canGetElderReward", SaveUtil.biome1.canGetElderReward());
                            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), serverData), serverPlayer);
                            this.setConversingPlayer(serverPlayer);
                        }
                    }
//                } else {
//                    talk(player,Component.translatable(""));
//                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        //击杀完boss1后回来见长老
        if(serverData.getBoolean("canGetElderReward")){
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome2Center = biomeMap.getBlockPos(biomeMap.getCenter2(),0);
            BlockPos biome3Center = biomeMap.getBlockPos(biomeMap.getCenter3(),0);
            String position2 = "("+ biome2Center.getX()+","+biome2Center.getZ()+")";
            String position3 = "("+ biome3Center.getX()+","+biome3Center.getZ()+")";
            builder.start(3)
                    .addChoice(3,4)
                    .addChoice(4,5)
                    .addChoice(BUILDER.buildDialogueOption(entityType,5),Component.literal("\n").append(Component.translatable(entityType + ".dialog6",position2,position3)))
                    .addFinalChoice(6,(byte)1);
        //初次与长老对话
        } else if(!serverData.getBoolean("isElderTalked")){
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
            String position = "("+biome1Center.getX()+","+biome1Center.getZ()+")";
            builder.start(0)
                    .addChoice(1,1)
                    .addChoice(BUILDER.buildDialogueOption(entityType,0),BUILDER.buildDialogueAnswer(entityType,2,position))
                    .addFinalChoice(-2,(byte)-1);
        //非初次对话
        } else {
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
            String position = "("+biome1Center.getX()+","+biome1Center.getZ()+")";
            builder.start(BUILDER.buildDialogueAnswer(entityType,2,position))
                    .addFinalChoice(-2,(byte)114514);
        }

        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID) {
            //接取任务
            case -1:
                //这个可以保留，每个玩家可以领一份
                if(!DataManager.elderLoot1Got.getBool(player)){
                    player.addItem(TCRModItems.ELDER_CAKE.get().getDefaultInstance());
                    player.addItem(Items.DIAMOND.getDefaultInstance().copyWithCount(5));
                    DataManager.elderLoot1Got.putBool(player,true);
                }else {
                    chat(111);
                }
                this.chat(BUILDER.buildDialogueAnswer(entityType,7));
                SaveUtil.biome1.isElderTalked = true;
                SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskKillBoss);
                break;
            case 0: //对话中断的代码！
//                this.chat(Component.translatable("刚刚说到哪儿来着？"));
                break;
            case 1: //回来领奖
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskBackToElder);
                this.chat(BUILDER.buildDialogueAnswer(entityType,10));//再会，勇者！=
                //TODO 获得进度，给予奖励
                break;
        }
        this.setConversingPlayer(null);
    }

    public void chat(Component component){
        this.talk(conversingPlayer,component);
    }
    public void chat(int index){
        this.talk(conversingPlayer,BUILDER.buildDialogueAnswer(entityType,index));
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
    public boolean hurt(DamageSource source, float v) {
        if(!SaveUtil.biome1.canAttackElder()){
            return false;
        }
        if(source.getEntity() instanceof ServerPlayer serverPlayer){
            bossInfo.addPlayer(serverPlayer);//亮血条！
            talk(serverPlayer, Component.translatable(entityType.getDescriptionId()+".fuck_chat"+(r.nextInt(whatCanISay))));//长老被打也是会生气滴！
        }
        return super.hurt(source, v);
    }

    /**
     * 濒死则进入对话
     * @param source
     */
    @Override
    public void die(DamageSource source) {

        //TODO say遗言
        SaveUtil.biome1.isElderDie = true;
        SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskKillElder);
        SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskBackToBoss);
        if(source.getEntity() instanceof Player player){
            player.addItem(BookManager.BIOME1_ELDER_DIARY_3.get());
        }
        super.die(source);
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    //不用加id
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(entityType.getDescriptionId());
    }

}
