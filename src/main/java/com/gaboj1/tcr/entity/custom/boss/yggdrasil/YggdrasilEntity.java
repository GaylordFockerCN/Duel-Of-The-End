package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.block.entity.spawner.EnforcedHomePoint;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.TCRModSounds;
import com.gaboj1.tcr.gui.screen.TreeNode;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.server.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;


public class YggdrasilEntity extends PathfinderMob implements GeoEntity, EnforcedHomePoint, NpcDialogue {
    @Nullable
    protected Player conversingPlayer;
    EntityType<?> entityType = TCRModEntities.YGGDRASIL.get();
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(YggdrasilEntity.class, EntityDataSerializers.COMPONENT);
    private static final EntityDataAccessor<Boolean> IS_FIGHTING = SynchedEntityData.defineId(YggdrasilEntity.class, EntityDataSerializers.BOOLEAN);
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);

    private boolean canBeHurt;
    private boolean isFighting;
    private int hurtTimer;

    public YggdrasilEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        getEntityData().define(IS_FIGHTING, false);
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

    public ServerBossEvent getBossBar() {
        return this.bossInfo;
    }
    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.getBossBar().setName(this.getDisplayName());
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide()) {
            this.getBossBar().setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.getBossBar().addPlayer(player);
    }


    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.getBossBar().removePlayer(player);
    }

    /**
     * 不能真的死，剩下一口气还要对话
     */
    @Override
    public void die(@NotNull DamageSource source) {
        getEntityData().set(IS_FIGHTING, false);
        if(!level().isClientSide){
            this.setHealth(1);
            ServerPlayer player;
            if (source.getEntity() instanceof ServerPlayer serverPlayer) {
                player = serverPlayer;
            } else {
                player = (ServerPlayer) level().players().iterator().next();
            }
            if (this.getConversingPlayer() == null && player != null) {
                sendDialoguePacket(player);
                this.setConversingPlayer(player);
            }
            canBeHurt = false;
        }

    }

    /**
     * 真的死要用这个hhh
     */
    public void realDie(DamageSource damageSource){
        this.setHealth(0);
        super.die(damageSource);
        SaveUtil.biome1.isBossDie = true;
        SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskKillBoss);
        SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskBackToElder);
    }


    @Override
    public boolean hurt(DamageSource damageSource, float v) {
        if(damageSource.isCreativePlayer()){
            super.hurt(damageSource, v);
        }
        if(!canBeHurt){
            if(damageSource.getEntity() instanceof ServerPlayer player){
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.boss1invincible"),true);
            }
            return false;
        }

        //能不能打也得看进度，选了就不能背刺了
        if(!SaveUtil.biome1.canAttackBoss()){
            return false;
        }

        return super.hurt(damageSource, v);
    }

    /**
     * 时间过了就恢复无敌状态
     */
    @Override
    public void tick() {
        super.tick();

        if(!level().isClientSide){

        }

        if(conversingPlayer != null){
            canBeHurt = false;
        }
        if(canBeHurt){
            hurtTimer--;
        }
        if(hurtTimer<0){
            canBeHurt = false;
        }
    }

    /**
     * 特定机制下（比如树爪被破坏）才能被打
     */
    public void setCanBeHurt() {
        this.canBeHurt = true;
        hurtTimer = 200;
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 6.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.35f)//移速
                .build();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //非攻击状态动画
        controllers.add(new AnimationController<>(this, "controller",
                0, this::predicate));

        //攻击状态动画
        controllers.add(new AnimationController<>(this, "attackController",
                0, this::attackPredicate));

    }

    @Override
    public @Nullable GlobalPos getRestrictionPoint() {
        return null;
    }

    @Override
    public void setRestrictionPoint(@Nullable GlobalPos pos) {

    }

    @Override
    public int getHomeRadius() {
        return 0;
    }

    public void sendDialoguePacket(ServerPlayer serverPlayer){
        if(getEntityData().get(IS_FIGHTING)){
            return;
        }
        CompoundTag serverData = new CompoundTag();
        serverData.putBoolean("canGetBossReward", SaveUtil.biome1.canGetBossReward());
        serverData.putBoolean("isBossTalked", SaveUtil.biome1.isBossTalked);
        serverData.putBoolean("isBossFought", SaveUtil.biome1.isBossFought);
        serverData.putBoolean("isBossDie", SaveUtil.biome1.isBossDie);
        serverData.putBoolean("killElderTaskGet", SaveUtil.biome1.killElderTaskGet());
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), serverData), serverPlayer);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openDialogueScreen(CompoundTag serverData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);

        if(serverData.getBoolean("canGetBossReward")) {
            //满足领奖条件
            builder.start(BUILDER.buildDialogueAnswer(entityType, 9))
                    .addChoice(BUILDER.buildDialogueOption(entityType, 7), BUILDER.buildDialogueAnswer(entityType, 10))
                    .thenExecute((byte) 3)//中途返回值进行处理但不结束对话
                    .addChoice(BUILDER.buildDialogueOption(entityType, 8), BUILDER.buildDialogueAnswer(entityType, 11))
                    .addChoice(BUILDER.buildDialogueOption(entityType, 9), BUILDER.buildDialogueAnswer(entityType, 12))
                    .addChoice(BUILDER.buildDialogueOption(entityType, -1), BUILDER.buildDialogueAnswer(entityType, 13));
        } else if(serverData.getBoolean("killElderTaskGet")){
            //未满足领奖条件但是任务已经领了
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,9))
                            .addLeaf(BUILDER.buildDialogueOption(entityType,6),(byte) 114514));

        } else if(!serverData.getBoolean("isBossTalked")){
            //靠近就触发战斗，初次对话
            builder.start(BUILDER.buildDialogueAnswer(entityType, 0))
                    .addChoice(BUILDER.buildDialogueOption(entityType,-1),BUILDER.buildDialogueAnswer(entityType,1))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)0);

        } else if(!serverData.getBoolean("isBossFought")){
            //战斗结束后的对话
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueAnswer(entityType,2))
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,1))
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,4),BUILDER.buildDialogueOption(entityType,2))
                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,5),BUILDER.buildDialogueOption(entityType,-1))
                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,6),BUILDER.buildDialogueOption(entityType,-1))
                                                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,7),BUILDER.buildDialogueOption(entityType,3))
                                                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,8),BUILDER.buildDialogueOption(entityType,4))
                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,5),(byte) 1)//处决
                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,6),(byte) 2)
                                                                    )//领任务
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        } else {
            //还有别的情况吗？有就当还没打过处理吧然后返回值随便搞一个表示不处理
            builder.start(BUILDER.buildDialogueAnswer(entityType,0))
                    .addChoice(BUILDER.buildDialogueOption(entityType,-1),BUILDER.buildDialogueAnswer(entityType,1))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)114514);
            getEntityData().set(IS_FIGHTING, true);
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            //初次对话结束，就是变成开始打了
            case 0:
                SaveUtil.biome1.isBossTalked = true;
                getEntityData().set(IS_FIGHTING, true);
                break;
            //选择处决
            case 1:
                SaveUtil.biome1.isBossFought = true;//注意要处决或者接任务后再调这个，注意考虑对话中断的情况
                realDie(player.damageSources().playerAttack(player));
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskKillBoss);
                SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskBackToElder);
                break;
            //选择接任务
            case 2:
                SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskKillElder);
                SaveUtil.biome1.isBossFought = true;//注意要处决或者接任务后再调这个，注意考虑对话中断的情况
                break;
            //任务成功
            case 3:
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskBackToBoss);
                SaveUtil.biome1.choice = SaveUtil.BiomeData.BOSS;
                //TODO: 颁奖了
                return;//NOTE：颁奖后面还有对话，不能setConversingPlayer为Null
        }
        this.setConversingPlayer(null);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide()) {
                    this.lookAt(player, 180.0F, 180.0F);
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (this.getConversingPlayer() == null) {
                            sendDialoguePacket(serverPlayer);
                            this.setConversingPlayer(serverPlayer);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
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


    @Nullable
    @Override
    public void chat(Component component) {
        if(conversingPlayer != null) {
            conversingPlayer.sendSystemMessage(BUILDER.buildDialogue(this, component));
        }
    }

    public static class conversationTriggerGoal extends Goal{
        private final YggdrasilEntity yggdrasil;
        ServerPlayer player;

        public conversationTriggerGoal(YggdrasilEntity yggdrasil) {
            this.yggdrasil = yggdrasil;
        }
        @Override
        public boolean canUse() {
            player = null;
            if (yggdrasil.getTarget() instanceof ServerPlayer) {
                player = (ServerPlayer) yggdrasil.getTarget();
            }
            if (player == null) {
                return false;
            }
            double distance = yggdrasil.distanceTo(player);
            return distance < 20 && !SaveUtil.biome1.isBossTalked;
        }

        @Override
        public void start() {
                if (yggdrasil.getConversingPlayer() == null) {
                    yggdrasil.setConversingPlayer(player);
                    yggdrasil.sendDialoguePacket(player);
                }

        }
    }

    public static class spawnTreeClawAtPointPositionGoal extends Goal {
    private final YggdrasilEntity yggdrasil;
    private int shootInterval;
    public final int shootIntervalMax = 200;
    public static final int attackRange = 10;

    public spawnTreeClawAtPointPositionGoal(YggdrasilEntity yggdrasil) {
        this.yggdrasil = yggdrasil;
//        shootInterval = shootIntervalMax;//开局就发射一个树爪，方便调试
    }

    @Override
    public boolean canUse() {
        return --this.shootInterval <= 0 && yggdrasil.getConversingPlayer() == null;
    }


    @Override
    public void start() {
        List<Player> players = this.yggdrasil.level().getNearbyPlayers(TargetingConditions.DEFAULT, yggdrasil, getPlayerAABB(yggdrasil.getOnPos(),attackRange));
        for(Player target : players){
            TreeClawEntity treeClaw = new TreeClawEntity(this.yggdrasil.level(), this.yggdrasil, target);
            treeClaw.setPos(target.getX(),target.getY(),target.getZ());
            yggdrasil.level().addFreshEntity(treeClaw);//树爪继承自Mob，和平模式无法召唤！！
            treeClaw.setDeltaMovement(target.getDeltaMovement().scale(0.1));//追一下
            treeClaw.catchPlayer();
            yggdrasil.level().playSound(null,treeClaw.getOnPos(), SoundEvents.PLAYER_HURT, SoundSource.BLOCKS,1.0f,1.0f);
        }

        this.shootInterval = shootIntervalMax;
    }

    /**
     * 返回一个范围
     * @param pos 中心位置
     * @param offset 半径
     * @return 以pos为中心offset的两倍为边长的一个正方体
     */
    private AABB getPlayerAABB(BlockPos pos, int offset){
        return new AABB(pos.offset(offset,offset,offset),pos.offset(-offset,-offset,-offset));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("wander", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(isDeadOrDying()){//播放死亡动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("death", Animation.LoopType.LOOP));
            return PlayState.STOP;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.STOP;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return TCRModSounds.YGGDRASIL_AMBIENT_SOUND.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return TCRModSounds.YGGDRASIL_CRY.get();
    }

    /**
     * TODO 补死亡音效
     */
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying();
    }

    private PlayState attackPredicate(AnimationState state) {
        if(state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    protected void registerGoals() {//设置生物行为
        this.goalSelector.addGoal(0,new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1,new conversationTriggerGoal(this));
        this.goalSelector.addGoal(2, new spawnTreeClawAtPointPositionGoal(this));
//       this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));



        /*
         * 下面设置攻击目标：（按需修改）
         * 首先寻找攻击源
         * 如果是玩家，且树怪被玩家激怒，则优先攻击玩家
         * 如果是村民，不处于被激怒状态则也被攻击，优先级低于玩家（按需修改）
         * 如果是Creeper，则与村民逻辑相同，但优先级低于村民（按需修改）
         * */
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this,AbstractVillager.class,true));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
