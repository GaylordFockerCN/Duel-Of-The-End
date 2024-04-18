package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.init.TCRModEntities;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

public class PastoralPlainVillagerElder extends TCRVillager implements NpcDialogue {

    @Nullable
    private Player conversingPlayer;

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
     * 因为不知为何Goal失效，所以只能在tick中实现这个操作
     */
    @Override
    public void tick() {
        super.tick();
        if(this.conversingPlayer!=null){
            this.navigation.stop();
        }
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
        var entityType = TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get();
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(DataManager.boss1Defeated.getBool(serverPlayerData)){
            builder.start(BUILDER.buildDialogueDialog(entityType,4))
                    .addChoice(BUILDER.buildDialogueChoice(entityType,3),BUILDER.buildDialogueDialog(entityType,5))
                    .addChoice(BUILDER.buildDialogueChoice(entityType,4),BUILDER.buildDialogueDialog(entityType,6))
                    .addFinalChoice(BUILDER.buildDialogueChoice(entityType,5),(byte)0);
        }else {
            BiomeMap biomeMap = BiomeMap.getInstance();
            BlockPos biome1Center = biomeMap.getBlockPos(biomeMap.getCenter1(),0);
            String position = "("+biome1Center.getX()+","+biome1Center.getZ()+")";
            builder.start(BUILDER.buildDialogueDialog(entityType,0))
                    .addChoice(BUILDER.buildDialogueChoice(entityType,0),BUILDER.buildDialogueDialog(entityType,1,position))//告诉玩家密林方位
                    .addChoice(BUILDER.buildDialogueChoice(entityType,1),BUILDER.buildDialogueDialog(entityType,2))
                    .addChoice(BUILDER.buildDialogueChoice(entityType,1),BUILDER.buildDialogueDialog(entityType,3))
                    .addFinalChoice(BUILDER.buildDialogueChoice(entityType,2),(byte)0);
        }

        Minecraft.getInstance().setScreen(builder.build());
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

    //长老被打也是会生气滴！
    @Override
    @OnlyIn(Dist.CLIENT)
    public void talkFuck(Player player){
        talk(player, Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".fuck_chat"+(r.nextInt(whatCanISay))));
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
    public void die(DamageSource pCause) {
        //如果玩家为黑方（接受boss任务）则获取村长日记真相
        if(pCause.getEntity() instanceof Player player && !DataManager.isWhite.getBool(player)){
            player.addItem(Book.getBook("biome1_elder_diary3",2));
        }
        super.die(pCause);
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }
}
