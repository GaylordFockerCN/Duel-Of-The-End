package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.util.BookManager;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class PastoralPlainVillagerElder extends TCRVillager implements NpcDialogue {

    @Nullable
    private Player conversingPlayer;
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    public PastoralPlainVillagerElder(EntityType<? extends PastoralPlainVillagerElder> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, -114514);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public boolean isElder() {
        return true;
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 10.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速
                .build();
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
    public void stopSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossInfo.removePlayer(serverPlayer);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        this.setItemInHand(InteractionHand.MAIN_HAND, TCRItems.ELDER_STAFF.get().getDefaultInstance());

        if (hand == InteractionHand.MAIN_HAND) {
            if (player instanceof ServerPlayer serverPlayer) {
                //无剧情模式
                if(SaveUtil.isNoPlotMode()){
                    if(SaveUtil.biome1.isBossDie && !DataManager.elderLoot2Got.get(player)){
                        ItemUtil.addItem(player,TCRItems.DENSE_FOREST_CERTIFICATE.get(),1);
                        ItemUtil.addItem(player,TCRItems.GOD_INGOT.get(),4);
                        DataManager.elderLoot2Got.put(player, true);
                    }
                    return InteractionResult.SUCCESS;
                }
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_FIND_ELDER1);
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_BACK_TO_ELDER);//是Set，没有也不影响
                this.lookAt(player, 180.0F, 180.0F);
                if (this.getConversingPlayer() == null) {
                    CompoundTag serverData = new CompoundTag();
                    serverData.putBoolean("canAttackElder", SaveUtil.biome1.canAttackElder());
                    serverData.putBoolean("isElderTalked", SaveUtil.biome1.isElderTalked);
                    serverData.putBoolean("canGetElderReward", SaveUtil.biome1.canGetElderReward());
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), serverData), serverPlayer);
                    this.setConversingPlayer(serverPlayer);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverData) {
        if(serverData.getBoolean("canAttackElder")){
            return;
        }
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        //击杀完boss1后回来见长老
        if(serverData.getBoolean("canGetElderReward")){
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome2Center = biomeMap.getBlockPos(biomeMap.getCenter2(),0);
            BlockPos biome3Center = biomeMap.getBlockPos(biomeMap.getCenter3(),0);
            String position2 = "("+ biome2Center.getX()+", "+biome2Center.getZ()+")";
            String position3 = "("+ biome3Center.getX()+", "+biome3Center.getZ()+")";
            builder.start(3)
                    .addChoice(3,4)
                    .addChoice(4,5)
                    .addChoice(BUILDER.buildDialogueOption(entityType,5), Component.literal("\n").append(Component.translatable(entityType + ".dialog6", position2, position3)))
                    .addFinalChoice(6,(byte)1);
        //初次与长老对话
        } else if(!serverData.getBoolean("isElderTalked")){
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
            String position = "("+biome1Center.getX()+", "+biome1Center.getZ()+")";
            builder.start(0)
                    .addChoice(1,1)
                    .addChoice(BUILDER.buildDialogueOption(entityType,0),BUILDER.buildDialogueAnswer(entityType,2,position))
                    .addFinalChoice(-2,(byte)-1);
        //非初次对话
        } else {
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
            String position = "("+biome1Center.getX()+", "+biome1Center.getZ()+")";
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
                //这个DataManager可以保留，每个玩家可以领一份
                if(!DataManager.elderLoot1Got.get(player)){
                    ItemUtil.addItem(player,TCRItems.ELDER_CAKE.get(),1);
                    ItemUtil.addItem(player,TCRItems.PURIFICATION_TALISMAN.get(),1);
                    ItemUtil.addItem(player,Items.DIAMOND.getDefaultInstance().getItem(),1);
                    DataManager.elderLoot1Got.put(player,true);
                }else {
                    chat(111);
                }
                this.chat(BUILDER.buildDialogueAnswer(entityType,7));
                SaveUtil.biome1.isElderTalked = true;
                SaveUtil.TASK_SET.add(SaveUtil.Biome1ProgressData.TASK_KILL_BOSS);
                break;
            case 0: //对话中断的代码！
//                this.chat(Component.translatable("刚刚说到哪儿来着？"));
                break;
            case 1: //回来领奖
                this.chat(BUILDER.buildDialogueAnswer(entityType,10));//再会，勇者！
                SaveUtil.biome1.finish(SaveUtil.BiomeProgressData.VILLAGER, ((ServerLevel) level()));
                if(!DataManager.elderLoot2Got.get(player)){
                    ItemUtil.addItem(player,TCRItems.DENSE_FOREST_CERTIFICATE.get(),1);
                    ItemUtil.addItem(player,TCRItems.GOD_INGOT.get(),6);
                    DataManager.elderLoot2Got.put(player, true);
                }
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
        if(source.getEntity() instanceof ServerPlayer serverPlayer && !SaveUtil.isNoPlotMode()){
            bossInfo.addPlayer(serverPlayer);//亮血条！
            talk(serverPlayer, Component.translatable(entityType.getDescriptionId() + ".fuck_chat" + r.nextInt(whatCanISay)));
        }
        return super.hurt(source, v);
    }

    /**
     * 濒死则进入对话
     */
    @Override
    public void die(DamageSource source) {
        SaveUtil.biome1.isElderDie = true;
        SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_KILL_ELDER);
        SaveUtil.TASK_SET.add(SaveUtil.Biome1ProgressData.TASK_BACK_TO_BOSS);
        if(source.getEntity() instanceof Player player){
            ItemUtil.addItem(player,BookManager.BIOME1_ELDER_DIARY_3.get().getItem(),1);
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

    @Override
    public String getResourceName() {
        return "pastoral_plain_villager_elder";
    }
}
