package com.gaboj1.tcr.entity.custom.boss.second_boss;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.boss.TCRBoss;
import com.gaboj1.tcr.entity.custom.villager.biome2.*;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.Set;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class SecondBossEntity extends TCRBoss implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected EntityType<?> entityType = TCRModEntities.SECOND_BOSS.get();
    private final Set<Integer> mastersId = new HashSet<>();
    public SecondBossEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_, BossEvent.BossBarColor.WHITE);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SaveUtil.getMobMultiplier(200))
                .add(Attributes.ATTACK_DAMAGE, SaveUtil.getMobMultiplier(3))
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514)//不动如山！
                .build();
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
                    .addFinalChoice(0, (byte)-1);
        } else if(senderData.getBoolean("isElderDie")){
            //击败联军后
            builder.start(4)
                    .addChoice(5, 5)
                    .addChoice(0, 6)
                    .thenExecute((byte) 4)
                    .addChoice(6, 7)
                    .addFinalChoice(0, (byte)3);
        } else {
            return;
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case -1: // 按esc也是返回0！！！
                //初次对话，召唤六大门派
                if(!mastersId.isEmpty()){
                    return;
                }
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();
                double r = 2;
                FuryTideCangLan cangLan = TCRModEntities.CANG_LAN.get().create(level());
                assert cangLan != null;
                cangLan.setPos(x + r, y, z);
                BlazingFlameYanXin yanXin = TCRModEntities.YAN_XIN.get().create(level());
                yanXin.setPos(x + 0.5 * r, y, z + 0.866 * r);
                IronfistDuanShan duanShan = TCRModEntities.DUAN_SHAN.get().create(level());
                duanShan.setPos(x - 0.5 * r, y, z + 0.866 * r);
                SerpentWhispererCuiHua cuiHua = TCRModEntities.CUI_HUA.get().create(level());
                cuiHua.setPos(x - r, y, z);
                ThunderclapZhenYu zhenYu = TCRModEntities.ZHEN_YU.get().create(level());
                zhenYu.setPos(x - 0.5 * r, y, z - 0.866 * r);
                WindwalkerYunYi yunYi = TCRModEntities.YUN_YI.get().create(level());
                yunYi.setPos(x + 0.5 * r, y, z - 0.866 * r);
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
                for(int id : mastersId){
                    if(level().getEntity(id) instanceof Master master){//没加入level前无法获取id。。。
                        master.getLookControl().setLookAt(this);
                        master.setWaiting(true);
                        master.setBossId(this.getId());
                        master.setHealth(1);//TODO 测试用，记得删
//                        level().explode(master, this.damageSources().explosion(this, this), null, master.position(), 3F, false, Level.ExplosionInteraction.NONE);
                    }
                }
                CompoundTag data = new CompoundTag();
                data.putBoolean("fromBoss", true);
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(cangLan.getId(), data), ((ServerPlayer) player));//让苍澜接话
                break;
            case 1:
                //选择boss阵容
                for(int id : mastersId){
                    if(level().getEntity(id) instanceof Master master){
                        master.startFighting(player);
                    }
                }
                SaveUtil.biome2.isBossTalked = true;
                SaveUtil.biome2.choice = SaveUtil.BiomeData.BOSS;
                break;
            case 2:
                //选择联军阵容
                getEntityData().set(IS_FIGHTING, true);
                setTarget(player);
                for(int id : mastersId){
                    if(level().getEntity(id) instanceof Master master){
                        master.startFighting(this);
                    }
                }
                SaveUtil.biome2.isBossTalked = true;
                SaveUtil.biome2.choice = SaveUtil.BiomeData.VILLAGER;
                break;
            case 3:
                //结束boss对话
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 7), false);
                level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3F, false, Level.ExplosionInteraction.NONE);
                this.discard();
                break;
            case 4:
                //boss送礼
                return;
        }
        setConversingPlayer(null);
    }

    @Override
    public void tick() {
        super.tick();

        //在对话的时候要等
        if(conversingPlayer != null){
            setTarget(null);
            this.getNavigation().stop();
            this.getLookControl().setLookAt(conversingPlayer);
        }

        //苍澜插嘴的时候也要等
        for(int i : mastersId){
            if(level().getEntity(i) instanceof Master master && master.isWaiting()){
                this.getNavigation().stop();
                this.getLookControl().setLookAt(master);
            }
        }

    }

    /**
     * 掌门死了后要移除，并且判断是否杀死了全部宗师
     * 注意要先判断周围有没有玩家再发包，以免找不到玩家而存档又改掉了
     */
    public void removeMaster(Master master){
        mastersId.remove(master.getId());
        if(mastersId.isEmpty()){
            if(getTarget() instanceof ServerPlayer serverPlayer){
                SaveUtil.biome2.isElderDie = true;
                sendDialoguePacket(serverPlayer);
            } else if(level().getNearestPlayer(this, 48) instanceof ServerPlayer serverPlayer){
                SaveUtil.biome2.isElderDie = true;
                sendDialoguePacket(serverPlayer);
            }
        }
    }

    @Override
    public SoundEvent getFightMusic() {
        return null;
    }

    @Override
    public boolean hurt(@NotNull DamageSource entity, float v) {
        if(SaveUtil.biome2.choice == SaveUtil.BiomeData.BOSS && entity.getEntity() instanceof Player){
            return false;
        }
        return super.hurt(entity, v);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        SaveUtil.biome2.isBossDie = true;
        for(int id : mastersId){
            if(level().getEntity(id) instanceof Master master){
                master.discard();
            }
        }
        FuryTideCangLan cangLan = TCRModEntities.CANG_LAN.get().create(level());
        assert cangLan != null;
        if(getTarget() != null){
            Vec3 vec3 = getTarget().position().add(this.position()).scale(0.5);
            cangLan.setPos(vec3.x, vec3.y, vec3.z);//夹中间
        } else {
            cangLan.setPos(this.position());
        }
        cangLan.setSummonedByBoss(true);
        cangLan.setWaiting(true);
        level().addFreshEntity(cangLan);
        cangLan.sendDialoguePacket(((ServerPlayer) level().getNearestPlayer(this, 48)));
        super.die(source);
    }

    @Override
    public void chat(Component component) {

    }

}
