package com.gaboj1.tcr.entity.custom.boss.second_boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.ai.goal.BossRecoverGoal;
import com.gaboj1.tcr.entity.custom.boss.TCRBoss;
import com.gaboj1.tcr.entity.custom.villager.biome2.*;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.EntityUtil;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

/**
 * 隔一阵子生剑盾，有盾的时候在天上飞，破盾则落地
 * 在天上飞时：
 * 1：发射追踪剑
 * 2：发射一根剑，随后瞬移到身边横扫攻击
 * 在地上：
 * 1：蓄力砸地击飞
 * 2：两种快慢刀，快快慢，快慢慢，打百分比真伤，接满三次必死
 */
public class SecondBossEntity extends TCRBoss implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    //是否有剑盾
    protected static final EntityDataAccessor<Boolean> HAS_SWORD_SHIELD = SynchedEntityData.defineId(SecondBossEntity.class, EntityDataSerializers.BOOLEAN);
    private final Set<Integer> shieldId = new HashSet<>();
    private int shieldCooldownTimer;//护盾破后的冷却时间
    private int swordAttackTimer;
    private int swordConvergenceCooldownTimer;//播动画到发射的计时器
    private int totalSword = 0;
    protected Vec3 finalTargetPos = Vec3.ZERO, dir = Vec3.ZERO;
    protected boolean isShooting;//统一发射
    private int stellaSwordCooldownTimer;//播动画到发射的计时器
    private int stellaSwordTeleportTimer;//发射到瞬移的计时器
    private int stellaAttackTimer;//挥砍的计时器
    private int stellaSwordId;

    protected EntityType<?> entityType = TCREntities.SECOND_BOSS.get();
    private final Set<Integer> mastersId = new HashSet<>();
    public SecondBossEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_, BossEvent.BossBarColor.WHITE);
        setHealth(20);//TODO 测试用
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(HAS_SWORD_SHIELD, false);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1056)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514)//不动如山！
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0,new BossRecoverGoal(this, 72));
        this.goalSelector.addGoal(1, new SummonSwordShieldGoal(this));
        this.goalSelector.addGoal(2, new SummonSwordAttackGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, (livingEntity -> {
            return isFighting() ? livingEntity instanceof Player : livingEntity instanceof Master;
        })));
    }

    @Override
    public void tick() {
        super.tick();
        if(shieldCooldownTimer > 0){
            shieldCooldownTimer--;
        }

        if(stellaSwordCooldownTimer > 0){
            stellaSwordCooldownTimer--;
            if(stellaSwordCooldownTimer == 0){
                StellarSwordEntity sword = new StellarSwordEntity(level());
                stellaSwordId = sword.getId();
                sword.setOwner(this);
                sword.setNoGravity(true);
                sword.setBaseDamage(0.01);
                sword.setSilent(false);
                sword.pickup = AbstractArrow.Pickup.DISALLOWED;
                sword.setKnockback(0);//击退
                sword.setPierceLevel((byte) 5);//穿透
                sword.setPos(this.getEyePosition(1).add(0,1,0));
                if(getTarget() == null){
                    Vec3 view = this.getViewVector(1f);
                    sword.shoot(view.x, view.y, view.z, 3,0);
                } else {
                    Vec3 stellaDir = getTarget().position().subtract(this.position()).normalize();
                    sword.shoot(stellaDir.x, stellaDir.y, stellaDir.z, 3,0);
                }
                this.level().playSound(null, sword.getOnPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 1,1);
                this.level().addFreshEntity(sword);
                stellaSwordTeleportTimer = 60;
            }
        }

        //处理瞬移和挥砍
        if(stellaSwordTeleportTimer > 0){
            stellaSwordTeleportTimer--;
            if(stellaSwordTeleportTimer == 0){
                if(level().getEntity(stellaSwordId) instanceof StellarSwordEntity sword){
                    this.level().playSound(null, sword.getOnPos(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1,1);
                    setPos(sword.position());
                    this.level().playSound(null, sword.getOnPos(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1,1);
                    sword.discard();
//                    triggerAnim(); TODO播挥砍
                    stellaAttackTimer = 100;
                }
            }
        }

        //万剑归宗
        if(swordConvergenceCooldownTimer > 0){
            isShooting = false;
            swordConvergenceCooldownTimer--;
            //蓄力时增加剑数且不移动
            totalSword++;
            SwordConvergenceEntity.summonSwords(this, totalSword, 4 + totalSword / 20);
            getNavigation().stop();
            if(getTarget() != null){
                getLookControl().setLookAt(getTarget());
            }
            //发射
            if(swordConvergenceCooldownTimer == 0){
                totalSword = 0;
                if(this.getTarget() != null){
                    dir = getTarget().position().subtract(this.position()).normalize();
                    finalTargetPos = this.position().add(dir.scale(17));
                } else {
                    finalTargetPos = position().add(getViewVector(1.0f).normalize().scale(17));
                    dir = getViewVector(1.0f);
                }
                isShooting = true;
            }

        }

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
     * 受到琵琶攻击直接破盾
     */
    public void hurtByPiPa(Player player){
        if(getEntityData().get(HAS_SWORD_SHIELD)){
            player.displayClientMessage(BUILDER.buildDialogue(this, BUILDER.buildDialogueAnswer(entityType, 10, false)),false);
        }
        breakAllShield();
    }

    public void breakOneShield(){
        if(getShieldId().isEmpty()){
            return;
        }
        int id = getShieldId().iterator().next();
        if(level().getEntity(id) instanceof ScreenSwordEntityForBoss screenSwordEntityForBoss){
            screenSwordEntityForBoss.discard();
        }
        getShieldId().remove(id);//不管是不是都要移除
        if(getShieldId().isEmpty()){
            getEntityData().set(HAS_SWORD_SHIELD, false);
            setShieldCooldownTimer(600);
        }
    }

    public void breakAllShield(){
        while(!getShieldId().isEmpty()){
            breakOneShield();
        }
    }

    public Set<Integer> getShieldId() {
        return shieldId;
    }

    /**
     * 生盾冷却，即破盾后在地上逗留的时间
     */
    public void setShieldCooldownTimer(int shieldCooldownTimer) {
        this.shieldCooldownTimer = shieldCooldownTimer;
    }

    /**
     * 掌门死了后要移除，并且判断是否杀死了全部宗师
     * 注意要先判断周围有没有玩家再发包，以免找不到玩家而存档又改掉了
     */
    public void removeMaster(Master master){
        if(level().isClientSide){
            return;
        }
        mastersId.remove(master.getId());
        if(mastersId.isEmpty()){
            AtomicBoolean hasSend = new AtomicBoolean(false);
            //苍澜说遗言 生存才有效
            for(Player player : EntityUtil.getNearByPlayers(this, 32)){
                DialogueComponentBuilder.displayClientMessages(player, 2000, false, ()->{
                            SaveUtil.biome2.isElderDie = true;
                            if(!hasSend.get()){
                                if(player instanceof ServerPlayer serverPlayer){
                                    sendDialoguePacket(serverPlayer);
                                }
                                hasSend.set(true);
                            }
                        },
                        BUILDER.buildDialogue(TCREntities.CANG_LAN.get(), BUILDER.buildDialogueAnswer(TCREntities.CANG_LAN.get(), 12, false)),
                        BUILDER.buildDialogueAnswer(TCREntities.CANG_LAN.get(), 13, false));
            }
        }
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
    @OnlyIn(Dist.CLIENT)
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
            case -1:
                //初次对话，召唤六大门派
                if(!mastersId.isEmpty()){
                    return;
                }
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();
                double r = 2;
                FuryTideCangLan cangLan = TCREntities.CANG_LAN.get().create(level());
                assert cangLan != null;
                cangLan.setPos(x + r, y, z);
                BlazingFlameYanXin yanXin = TCREntities.YAN_XIN.get().create(level());
                yanXin.setPos(x + 0.5 * r, y, z + 0.866 * r);
                IronfistDuanShan duanShan = TCREntities.DUAN_SHAN.get().create(level());
                duanShan.setPos(x - 0.5 * r, y, z + 0.866 * r);
                SerpentWhispererCuiHua cuiHua = TCREntities.CUI_HUA.get().create(level());
                cuiHua.setPos(x - r, y, z);
                ThunderclapZhenYu zhenYu = TCREntities.ZHEN_YU.get().create(level());
                zhenYu.setPos(x - 0.5 * r, y, z - 0.866 * r);
                WindwalkerYunYi yunYi = TCREntities.YUN_YI.get().create(level());
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
                SaveUtil.biome2.choice = SaveUtil.BiomeProgressData.BOSS;
                break;
            case 2:
                //选择联军阵容
                setIsFighting(true);
                setTarget(player);
                for(int id : mastersId){
                    if(level().getEntity(id) instanceof Master master){
                        master.startFighting(this);
                    }
                }
                SaveUtil.biome2.isBossTalked = true;
                SaveUtil.biome2.choice = SaveUtil.BiomeProgressData.VILLAGER;
                break;
            case 3:
                //结束boss对话
                chat(BUILDER.buildDialogueAnswer(entityType, 8, false));
                level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3F, false, Level.ExplosionInteraction.NONE);
                ItemUtil.addItem(player, TCRItems.AZURE_SKY_CERTIFICATE.get(), 1);
                this.discard();
                SaveUtil.biome2.finish(SaveUtil.BiomeProgressData.BOSS, (ServerLevel) level());
                break;
            case 4:
                //boss送礼
                ItemUtil.addItem(player, TCRItems.NINE_TURN_REVIVAL_ELIXIR.get(), 9, true);
                ItemUtil.addItem(player, TCRItems.AQUA_GOLD_ELIXIR.get(), 9, true);
                return;
        }
        setConversingPlayer(null);
    }

    @Override
    public SoundEvent getFightMusic() {
        return null;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float v) {
        if(SaveUtil.biome2.choice == SaveUtil.BiomeProgressData.BOSS && source.getEntity() instanceof Player){
            return false;
        }
        //防止剑射到自己
        if(source.getEntity() == this){
            return false;
        }
        //用剑抵挡一次大于10的伤害
        if(getEntityData().get(HAS_SWORD_SHIELD)){
            if(v > 10){
                breakOneShield();
            } else {
                if(source.getEntity() instanceof ServerPlayer player){
                    player.displayClientMessage(TheCasketOfReveriesMod.getInfo("second_boss_fight_tip"), true);
                }
            }
            return false;
        }
        return super.hurt(source, v);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if(SaveUtil.biome2.choice == SaveUtil.BiomeProgressData.BOSS){
            setHealth(1);
            return;
        }
        SaveUtil.biome2.isBossDie = true;
        for(int id : mastersId){
            if(level().getEntity(id) instanceof Master master){
                master.discard();
            }
        }
        FuryTideCangLan cangLan = TCREntities.CANG_LAN.get().create(level());
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

    /**
     * 隔一段时间召唤个盾
     */
    private static class SummonSwordShieldGoal extends Goal{
        private final SecondBossEntity boss;
        private SummonSwordShieldGoal(SecondBossEntity boss){
            this.boss = boss;
        }
        @Override
        public boolean canUse() {
            return boss.isFighting() && !boss.getEntityData().get(HAS_SWORD_SHIELD) && boss.shieldCooldownTimer <= 0;
        }

        @Override
        public void start() {
            for(int i = 0; i < 4 ; i++){
                ScreenSwordEntityForBoss sword = new ScreenSwordEntityForBoss(boss.level(), boss, i);
                boss.level().addFreshEntity(sword);
                sword.setPos(boss.getPosition(0.5f).add(sword.getOffset()));
            }

        }

    }

    /**
     * 定期射剑，随机射三剑或者使用星斗归位剑
     */
    private static class SummonSwordAttackGoal extends Goal{
        private final SecondBossEntity boss;
        private SummonSwordAttackGoal(SecondBossEntity boss){
            this.boss = boss;
        }
        @Override
        public boolean canUse() {
            return boss.isFighting() && boss.getEntityData().get(HAS_SWORD_SHIELD) && boss.swordAttackTimer-- < 0;
        }

        @Override
        public void start() {
            boss.swordAttackTimer = 100;//技能间隔
            if(boss.random.nextBoolean()){
//                boss.triggerAnim(); TODO 播动画
                boss.swordConvergenceCooldownTimer = 60;
                boss.isShooting = false;
            } else {
//                boss.triggerAnim(); TODO 播动画
                boss.stellaSwordCooldownTimer = 20;
            }
        }
    }

}
