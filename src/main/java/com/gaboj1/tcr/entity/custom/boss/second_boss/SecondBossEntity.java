package com.gaboj1.tcr.entity.custom.boss.second_boss;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.boss.TCRBoss;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome2.*;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class SecondBossEntity extends TCRBoss implements GeoEntity, NpcDialogue {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected EntityType<?> entityType = TCRModEntities.SECOND_BOSS.get();
    private static final EntityDataAccessor<Boolean> IS_FIGHTING = SynchedEntityData.defineId(SecondBossEntity.class, EntityDataSerializers.BOOLEAN);
    private final List<Integer> mastersId = new ArrayList<>();
    public SecondBossEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SaveUtil.getMobMultiplier(200))
                .add(Attributes.ATTACK_DAMAGE, SaveUtil.getMobMultiplier(3))
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .build();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("is_fighting", this.getEntityData().get(IS_FIGHTING));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(IS_FIGHTING,tag.getBoolean("is_fighting"));
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(IS_FIGHTING, false);
        super.defineSynchedData();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.STOP;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void sendDialoguePacket(ServerPlayer serverPlayer){
        CompoundTag serverData = new CompoundTag();
        serverData.putBoolean("isBossTalked",SaveUtil.biome2.isBossTalked);
        serverData.putBoolean("isElderDie",SaveUtil.biome2.isElderDie);
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), serverData), serverPlayer);
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        if(this.getTarget() != null || this.getConversingPlayer() != null){
            return;
        }
        LinkListStreamDialogueScreenBuilder builder = new LinkListStreamDialogueScreenBuilder(this, entityType);
        if(senderData.getBoolean("fromCangLan")){
            //继续cangLan的对话
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType, 3))
                            .addLeaf(BUILDER.buildDialogueOption(entityType,3),(byte) 1)
                            .addLeaf(BUILDER.buildDialogueOption(entityType,4),(byte) 2));
        } else if(!senderData.getBoolean("isBossTalked")){
            //初次见面，返回 0
            builder.start(0)
                    .addChoice(1, 1)
                    .addChoice(2, 2)
                    .addFinalChoice(0, (byte)0);
        } else if(senderData.getBoolean("isElderDie")){
            //击败联军后
            builder.start(4)
                    .addChoice(6, 5)
                    .addChoice(0, 6)
                    .thenExecute((byte) 4)
                    .addChoice(7, 7)
                    .addFinalChoice(8, (byte)3);
        } else {
            return;
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case 0:
                //初次对话，召唤六大门派
                FuryTideCangLan cangLan = TCRModEntities.CANG_LAN.get().create(level());
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();
                double r = 2;
                assert cangLan != null;
                cangLan.setPos(x + r, y, z);//夹中间
                cangLan.setBossId(this.getId());
                BlazingFlameYanXin yanXin = TCRModEntities.YAN_XIN.get().create(level());
                yanXin.setPos(x + 0.5 * r, y, z + 0.866 * r);
                yanXin.setBossId(this.getId());
                IronfistDuanShan duanShan = TCRModEntities.DUAN_SHAN.get().create(level());
                duanShan.setPos(x - 0.5 * r, y, z + 0.866 * r);
                duanShan.setBossId(this.getId());
                SerpentWhispererCuiHua cuiHua = TCRModEntities.CUI_HUA.get().create(level());
                cuiHua.setPos(x - r, y, z);
                cuiHua.setBossId(this.getId());
                ThunderclapZhenYu zhenYu = TCRModEntities.ZHEN_YU.get().create(level());
                zhenYu.setPos(x - 0.5 * r, y, z - 0.866 * r);
                zhenYu.setBossId(this.getId());
                WindwalkerYunYi yunYi = TCRModEntities.YUN_YI.get().create(level());
                yunYi.setPos(x + 0.5 * r, y, z - 0.866 * r);
                yunYi.setBossId(this.getId());
                level().addFreshEntity(cangLan);
                level().addFreshEntity(yanXin);
                level().addFreshEntity(duanShan);
                level().addFreshEntity(cuiHua);
                level().addFreshEntity(zhenYu);
                level().addFreshEntity(yunYi);
                mastersId.add(cangLan.getId());
                mastersId.add(yanXin.getId());
                mastersId.add(duanShan.getId());
                mastersId.add(cuiHua.getId());
                mastersId.add(zhenYu.getId());
                mastersId.add(yunYi.getId());

                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), new CompoundTag()), ((ServerPlayer) player));//让苍澜接话
                break;
            case 1:
                //选择boss阵容
                for(int id : mastersId){
                    if(level().getEntity(id) instanceof Master master){
                        master.setTarget(player);
                    }
                }
                break;
            case 2:
                //选择联军阵容
                getEntityData().set(IS_FIGHTING, true);
                setTarget(player);
                break;
            case 3:
                break;
            case 4:
                //boss送礼
                return;
        }
        setConversingPlayer(null);
    }

    @Override
    public void setConversingPlayer(@Nullable Player player) {

    }

    @Nullable
    @Override
    public Player getConversingPlayer() {
        return null;
    }

    @Override
    public void chat(Component component) {

    }
}
