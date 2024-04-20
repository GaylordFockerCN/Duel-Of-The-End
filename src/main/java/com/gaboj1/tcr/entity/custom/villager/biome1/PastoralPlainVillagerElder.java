package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.init.TCRModItems;
import com.gaboj1.tcr.item.custom.Book;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.packet.server.NPCDialoguePacketWithSkinID;
import com.gaboj1.tcr.util.DataManager;
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
                            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacketWithSkinID(this.getId(),serverPlayer.getPersistentData().copy(),this.skinID), serverPlayer);
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
    public void openDialogueScreen(CompoundTag serverPlayerData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);

        //长老濒死
        if(DataManager.elder1Defeated.getBool(serverPlayerData)){

        }

        //击杀完boss1后回来见长老
        if(DataManager.boss1Defeated.getBool(serverPlayerData) && DataManager.isWhite.getBool(serverPlayerData) /*&& DataManager.isWhite.isLocked()*/){
            builder.start(4)
                    .addChoice(3,5)
                    .addChoice(4,6)
                    .addChoice(5,8)
                    .addChoice(2,9)
                    .addFinalChoice(6,(byte)1);
        //初次与长老对话
        } else {
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
            String position = "("+biome1Center.getX()+","+biome1Center.getZ()+")";
            builder.start(0)
                    .addChoice(2,-1)
                    .addChoice(1,-2)
                    .addChoice(2,-3)
                    .addChoice(2,-4)
                    .addChoice(BUILDER.buildDialogueOption(entityType,0),BUILDER.buildDialogueAnswer(entityType,1,position))//告诉玩家密林方位
                    .addChoice(7,2)
                    .addChoice(2,3)
                    .addFinalChoice(-2,(byte)-1);
        }

        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID) {
            case -1:
                player.addItem(TCRModItems.ELDER_CAKE.get().getDefaultInstance());
                player.addItem(Items.DIAMOND.getDefaultInstance().copyWithCount(5));
                this.chat(BUILDER.buildDialogueAnswer(entityType,7));
                break;
            case 0: //对话中断的代码！
//                this.chat(Component.translatable("刚刚说到哪儿来着？"));
                break;
            case 1: //白方 击败boss
                this.chat(BUILDER.buildDialogueAnswer(entityType,10));//再会，勇者！
                player.addItem(Items.DIAMOND.getDefaultInstance().copyWithCount(5));
                //TODO 获得进度
                break;
            case 2: //黑方 未击败boss
                this.chat(BUILDER.buildDialogueAnswer(entityType,11));
                break;
            case 3: //黑方 击败boss
                this.chat(BUILDER.buildDialogueAnswer(entityType,12));
                break;
        }
        this.setConversingPlayer(null);
    }

    public void chat(Component component){
        this.talk(conversingPlayer,component);
    }

    /**
     *长老被打也是会生气滴！
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void talkFuck(Player player){
        talk(player, Component.translatable(entityType.getDescriptionId()+".fuck_chat"+(r.nextInt(whatCanISay))));
    }

    @OnlyIn(Dist.CLIENT)
    public void talkFuck(Player player, int i){
        talk(player, Component.translatable(entityType.getDescriptionId()+".fuck_chat"+i));
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
        if(source.getEntity() instanceof ServerPlayer serverPlayer){
            bossInfo.addPlayer(serverPlayer);//亮血条！
        }
        return super.hurt(source, v);
    }

    /**
     * 濒死则进入对话
     * @param source
     */
    @Override
    public void die(DamageSource source) {
        setHealth(1);
        setInvulnerable(true);//无敌
        if(source.getEntity() instanceof ServerPlayer serverPlayer) {
            if (this.getConversingPlayer() == null) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacketWithSkinID(this.getId(), serverPlayer.getPersistentData().copy(), this.skinID), serverPlayer);
                this.setConversingPlayer(serverPlayer);
            }
        }
    }

    /**
     * 真的死亡
     * @param pCause
     */
    public void realDie(DamageSource pCause){
        super.die(pCause);
        //如果玩家为黑方（接受boss任务）则获取村长日记真相
        if(pCause.getEntity() instanceof ServerPlayer player){
            DataManager.elder1Defeated.putBool(player,true);
            DataManager.elder1Defeated.lock(player);
            if(!DataManager.isWhite.getBool(player) && DataManager.isWhite.isLocked(player)){
                player.addItem(Book.getBook("biome1_elder_diary3",2));
            }else {
                talkFuck(player,1);//你为何选择这样的道路？
            }
        }
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
