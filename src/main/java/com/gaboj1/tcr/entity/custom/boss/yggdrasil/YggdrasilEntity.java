package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ShadowableEntity;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.entity.custom.boss.TCRBoss;
import com.gaboj1.tcr.entity.custom.biome1.SpriteEntity;
import com.gaboj1.tcr.entity.custom.biome1.TreeGuardianEntity;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.client.TCRModSounds;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;


public class YggdrasilEntity extends TCRBoss implements GeoEntity{
    EntityType<?> entityType = TCRModEntities.YGGDRASIL.get();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(YggdrasilEntity.class, EntityDataSerializers.INT);

    private boolean canBeHurt;
    private int hurtTimer;
    private int shootTimer = 0;
    private Entity shootTarget;
    private int recoverTimer = 0;
    private int treeClawTimer = 0;
    public final int maxRecoverTimer = 200;
    private final List<Integer> mobs = new ArrayList<>();

    public YggdrasilEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_, BossEvent.BossBarColor.BLUE);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SaveUtil.getMobMultiplier(200))
                .add(Attributes.ATTACK_DAMAGE, SaveUtil.getMobMultiplier(3))
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0,new ConversationTriggerGoal(this));
        this.goalSelector.addGoal(1,new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(2, new RangeMeleeAttackGoal(this, 1.0, true, 72, 40){
            @Override
            public boolean canUse() {
                return YggdrasilEntity.this.getEntityData().get(IS_FIGHTING) && super.canUse();
            }
        });
        this.goalSelector.addGoal(3,new RecoverGoal(this));
        this.goalSelector.addGoal(4, new SpawnTreeClawAtPointPositionGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this,AbstractVillager.class,true));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("state", this.getEntityData().get(STATE));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(STATE, tag.getInt("state"));
        if(tag.getInt("state") != 0){
            SaveUtil.biome1.isBossTalked = true;//多余的保险措施？
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(STATE, 0);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if(getEntityData().get(IS_SHADER)){
           return Component.translatable(entityType.getDescriptionId()+"_shader");
        }
        return super.getDisplayName();
    }

    /**
     * 抄自父类的die，取消死亡姿势的播放
     */
    public void superDie(@NotNull DamageSource pDamageSource){
        if (!ForgeHooks.onLivingDeath(this, pDamageSource)) {
            if (!this.isRemoved() && !this.dead) {
                Entity entity = pDamageSource.getEntity();
                LivingEntity livingentity = this.getKillCredit();
                if (this.deathScore >= 0 && livingentity != null) {
                    livingentity.awardKillScore(this, this.deathScore, pDamageSource);
                }

                if (this.isSleeping()) {
                    this.stopSleeping();
                }

                if (!this.level().isClientSide && this.hasCustomName()) {
                    TheCasketOfReveriesMod.LOGGER.info("Named entity {} died: {}", this, this.getCombatTracker().getDeathMessage().getString());
                }

                this.dead = true;
                this.getCombatTracker().recheckStatus();
                Level level = this.level();
                if (level instanceof ServerLevel serverlevel) {
                    if (entity == null || entity.killedEntity(serverlevel, this)) {
                        this.gameEvent(GameEvent.ENTITY_DIE);
                        this.dropAllDeathLoot(pDamageSource);
                        this.createWitherRose(livingentity);
                    }

                    this.level().broadcastEntityEvent(this, (byte)3);
                }

            }

        }
    }

    /**
     * 不能真的死，剩下一口气还要对话
     * 如果boss已经死过了而再次死说明是历战，要直接死
     */
    @Override
    public void die(@NotNull DamageSource source) {
        if(getEntityData().get(IS_SHADER)){
            //二次挑战则直接死 TODO 不知道为什么无法杀死
            superDie(source);
        }
        getEntityData().set(IS_FIGHTING, false);
        setTarget(null);
        if(!level().isClientSide){
            if(SaveUtil.biome1.isBossDie){
                triggerAnim("Death","death");
                superDie(source);
                return;
            }
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
     * 真的死要用这个hhh，在选项处决的时候调用
     */
    public void realDie(DamageSource damageSource){
        this.setHealth(0);
        triggerAnim("Death","death");
        superDie(damageSource);
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

        //能不能打也得看进度，选了就不能背刺了，还没进战斗也不能打。
        if(!getEntityData().get(IS_FIGHTING) || !SaveUtil.biome1.canAttackBoss()){
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

            //恢复的倒计时，这个timer由goal触发
            if(recoverTimer <= 0){
                boolean canRecover = false;
                for(int entityID : mobs){
                    Entity entity = level().getEntity(entityID);
                    if(entity instanceof TreeGuardianEntity treeGuardian && treeGuardian.getHealth()>=treeGuardian.getMaxHealth()/2){
                        canRecover = true;
                        break;
                    }else if(entity != null){
                        entity.discard();
                    }
                }
                if(canRecover){
                    recover();
                    recoverTimer = 114514;
                }
            } else if(recoverTimer <= maxRecoverTimer){
                recoverTimer--;
            }

            //树爪的倒计时，这个timer由goal触发
            if(treeClawTimer > 0){
                treeClawTimer--;
                if(treeClawTimer == 1){
                    List<Player> players = this.level().getNearbyPlayers(TargetingConditions.DEFAULT, this, getPlayerAABB(getOnPos(), 20));
                    List<Player> players2 = this.level().getNearbyPlayers(TargetingConditions.forNonCombat(), this, getPlayerAABB(getOnPos(), 20));//创造测试用
                    players.addAll(players2);
                    for (Player target : players) {
                        TreeClawEntity treeClaw = new TreeClawEntity(this.level(), this, target);
                        treeClaw.setPos(target.getX(), target.getY(), target.getZ());
                        level().addFreshEntity(treeClaw);//树爪继承自Mob，和平模式无法召唤！！
                        treeClaw.setDeltaMovement(target.getDeltaMovement().scale(0.1));//追一下
                        treeClaw.catchPlayer();
                        level().playSound(null, treeClaw.getOnPos(), SoundEvents.PLAYER_HURT, SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                }
            }

            //普攻的延迟发射，这个timer由goal触发
            if(shootTimer > 0){
                shootTimer--;
                if(shootTimer == 1){
                    MagicProjectile projectile = new MagicProjectile(level(), this);
                    double x = shootTarget.getX() - this.getX();
                    double y = shootTarget.getY(0.3333333333333333) - projectile.getY();
                    double z = shootTarget.getZ() - this.getZ();
                    double $$5 = Math.sqrt(x * x + z * z) * 0.20000000298023224;
                    projectile.setDamage(10);
                    projectile.shoot(x, y + $$5, z, 1.5F, 10.0F);
                    level().addFreshEntity(projectile);
                }
            }

        }

        //霸体时间判断
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

    public SoundEvent getFightMusic(){
        return TCRModSounds.BIOME1BOSS_FIGHT.get();
    }

    /**
     * 特定机制下（比如树爪被破坏）才能被打
     */
    public void setCanBeHurt() {
        this.canBeHurt = true;
        hurtTimer = 200;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return TCRModSounds.YGGDRASIL_AMBIENT_SOUND.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
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

        BiomeMap biomeMap = BiomeMap.getInstance();
        BlockPos biome2Center = biomeMap.getBlockPos(biomeMap.getCenter2(),0);
        BlockPos biome3Center = biomeMap.getBlockPos(biomeMap.getCenter3(),0);
        String position2 = "("+ biome2Center.getX()+", "+biome2Center.getZ()+")";
        String position3 = "("+ biome3Center.getX()+", "+biome3Center.getZ()+")";

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);

        if(serverData.getBoolean("canGetBossReward")) {
            //满足领奖条件
            builder.start(BUILDER.buildDialogueAnswer(entityType, 9))
                    .addChoice(BUILDER.buildDialogueOption(entityType, 7), BUILDER.buildDialogueAnswer(entityType, 10))
                    .thenExecute((byte) 3)//中途返回值进行处理但不结束对话
                    .addChoice(BUILDER.buildDialogueOption(entityType, 8), BUILDER.buildDialogueAnswer(entityType, 11))
                    .addChoice(BUILDER.buildDialogueOption(entityType, 9), BUILDER.buildDialogueAnswer(entityType, 12))
                    .addChoice(BUILDER.buildDialogueOption(entityType, -1), Component.literal("\n").append(Component.translatable(entityType + ".dialog13", position2, position3)))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,-1),(byte)4);
        } else if(serverData.getBoolean("killElderTaskGet")){
            //未满足领奖条件但是任务已经领了
            builder.start(9).addFinalChoice(6, (byte) 114514);
        } else if(!serverData.getBoolean("isBossTalked")){
            //靠近就触发战斗，初次对话
            builder.start(BUILDER.buildDialogueAnswer(entityType, 0))
                    .addChoice(BUILDER.buildDialogueOption(entityType,-1),BUILDER.buildDialogueAnswer(entityType,1))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)(-1));

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
                                                                            .addLeaf(BUILDER.buildDialogueOption(entityType,6),(byte) 2)//接任务
                                                                    )//领任务
                                                            )
                                                    )
                                            )
                                    )
                            )
            );
        } else {
            //还有别的情况吗？有就当还没打过处理吧，打的历战
            builder.start(BUILDER.buildDialogueAnswer(entityType,0))
                    .addChoice(BUILDER.buildDialogueOption(entityType,-1),BUILDER.buildDialogueAnswer(entityType,1))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)0);
            getEntityData().set(IS_FIGHTING, true);
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    /**
     * 根据手持的枯萎之触的物品数量来削弱树魔的属性
     */
    private void modifyAttribute(int count){
        AttributeModifier healthModifier = new AttributeModifier("health", -50, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeedModifier = new AttributeModifier("attackSpeed", -5, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamageModifier = new AttributeModifier("attackDamage", -2, AttributeModifier.Operation.ADDITION);
        switch (count){
            case 0:break;
            case 1:
                Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(healthModifier);break;
            case 2:
                Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_SPEED)).addPermanentModifier(attackSpeedModifier);break;
            default:
                Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addPermanentModifier(attackDamageModifier);
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            //初次对话结束，就是变成开始打了
            case -1:
                SaveUtil.biome1.isBossTalked = true;
                getEntityData().set(STATE, 1);
                getEntityData().set(IS_FIGHTING, true);
                for(ItemStack itemStack : player.getInventory().items){
                    if(itemStack.is(TCRModItems.WITHERING_TOUCH.get())){
                        modifyAttribute(itemStack.getCount());
                    }
                }
                break;
            //选择处决
            case 1:
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 14), false);
                SaveUtil.biome1.isBossFought = true;//注意要处决或者接任务后再调这个，注意考虑对话中断的情况
                realDie(player.damageSources().playerAttack(player));
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskKillBoss);
                SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskBackToElder);
                break;
            //选择接任务
            case 2:
                getEntityData().set(IS_FIGHTING, false);
                setTarget(null);
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 15), false);
                SaveUtil.TASK_SET.add(SaveUtil.Biome1Data.taskKillElder);
                SaveUtil.biome1.isBossFought = true;//注意要处决或者接任务后再调这个，注意考虑对话中断的情况
                break;
            //任务成功
            case 3:
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1Data.taskBackToBoss);
                SaveUtil.biome1.finish(SaveUtil.BiomeData.BOSS, ((ServerLevel) level()));
                if(!DataManager.boss1LootGot.getBool(player)){
                    player.addItem(TCRModItems.TREE_SPIRIT_WAND.get().getDefaultInstance());
                    player.addItem(TCRModItems.DENSE_FOREST_CERTIFICATE.get().getDefaultInstance());
                    DataManager.boss1LootGot.putBool(player, true);
                }
                return;//NOTE：颁奖后面还有对话，不能setConversingPlayer为Null
            //任务成功且对话结束
            case 4:
                player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 16), false);
                level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3F, false, Level.ExplosionInteraction.NONE);
                this.discard();
                break;
        }
        this.setConversingPlayer(null);
    }

    @Override
    public void chat(Component component) {
        if(conversingPlayer != null) {
            conversingPlayer.sendSystemMessage(BUILDER.buildDialogue(this, component));
        }
    }

    /**
     * 实现靠近就触发对话
     */
    public static class ConversationTriggerGoal extends Goal{
        private final YggdrasilEntity yggdrasil;
        ServerPlayer player;

        public ConversationTriggerGoal(YggdrasilEntity yggdrasil) {
            this.yggdrasil = yggdrasil;
        }
        @Override
        public boolean canUse() {
            if (yggdrasil.getTarget() instanceof ServerPlayer player) {
                this.player = player;
                return yggdrasil.distanceTo(player) < 10 && !SaveUtil.biome1.isBossTalked;
            }
            return false;
        }

        @Override
        public void start() {
            if (yggdrasil.getConversingPlayer() == null) {
                yggdrasil.setConversingPlayer(player);
                yggdrasil.sendDialoguePacket(player);
            }
        }

    }

    /**
     * 定时召唤树爪，击破树爪才能攻击boss
     */
    public static class SpawnTreeClawAtPointPositionGoal extends Goal {
        private final YggdrasilEntity yggdrasil;
        private int shootInterval;
        public final int shootIntervalMax = 100;

        public SpawnTreeClawAtPointPositionGoal(YggdrasilEntity yggdrasil) {
            this.yggdrasil = yggdrasil;
            shootInterval = (int) (55 + yggdrasil.getHealth()/2);//开局就发射一个树爪，方便调试，血越少越频繁
        }

        @Override
        public boolean canUse() {
            return --this.shootInterval <= 0 && yggdrasil.getEntityData().get(IS_FIGHTING) && SaveUtil.biome1.canAttackBoss();
        }

        @Override
        public void start() {
            yggdrasil.triggerAnim("Summon", "summon");
            yggdrasil.treeClawTimer = 20;


            this.shootInterval = shootIntervalMax;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    /**
     * 半血会触发回血，如果不限时击败小怪则boss会回血
     */
    public static class RecoverGoal extends Goal{

        private final YggdrasilEntity yggdrasil;
        private int summonInterval = 0;
        public RecoverGoal(YggdrasilEntity yggdrasil){
            this.yggdrasil = yggdrasil;
        }

        /**
         * 半血释放
         */
        @Override
        public boolean canUse() {
            return --this.summonInterval <= 0 && yggdrasil.getHealth() <= yggdrasil.getMaxHealth() / 2 && yggdrasil.getEntityData().get(IS_FIGHTING) && SaveUtil.biome1.canAttackBoss();
        }

        @Override
        public void start() {
            if(yggdrasil.level().isClientSide){
                return;
            }
            yggdrasil.triggerAnim("Summon", "summon");
            List<Player> players = this.yggdrasil.level().getNearbyPlayers(TargetingConditions.DEFAULT, yggdrasil, getPlayerAABB(yggdrasil.getOnPos(), 10));
            List<Player> players2 = this.yggdrasil.level().getNearbyPlayers(TargetingConditions.forNonCombat(), yggdrasil, getPlayerAABB(yggdrasil.getOnPos(), 10));//创造测试用
            players.addAll(players2);
            for(Player player : players){
                if(yggdrasil.mobs.size() <= 10){
                    SpriteEntity mob = TCRModEntities.SPRITE.get().spawn(((ServerLevel) yggdrasil.level()), player.getOnPos().above(3), MobSpawnType.NATURAL);
                    if(mob == null){
                        return;
                    }
                    mob.setTarget(player);
                    yggdrasil.level().addFreshEntity(mob);
                    yggdrasil.mobs.add(mob.getId());
                }
            }
            yggdrasil.recoverTimer = yggdrasil.maxRecoverTimer;
            this.summonInterval = yggdrasil.maxRecoverTimer * 2;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    /**
     * 播放动画并回血
     */
    public void recover(){
        triggerAnim("Recover","recover");
        getEntityData().set(STATE, 2);
        setHealth(getHealth()+getMaxHealth()/4);
    }

    /**
     * 播放动画并发射
     */
    public boolean doHurtTarget(@NotNull Entity target){
        triggerAnim("Attack","attack");
        shootTimer = 20;
        shootTarget = target;
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //非攻击状态动画
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));

        //自定义触发
        controllers.add(new AnimationController<>(this, "Recover", 10, state -> PlayState.STOP)
                .triggerableAnim("recover", RawAnimation.begin().then("recover", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "Summon", 10, state -> PlayState.STOP)
                .triggerableAnim("summon", RawAnimation.begin().then("attack1", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "Attack", 10, state -> PlayState.CONTINUE)
                .triggerableAnim("attack", RawAnimation.begin().then("attack2", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "Death", 10, state -> PlayState.STOP)
                .triggerableAnim("death", RawAnimation.begin().then("death", Animation.LoopType.PLAY_ONCE)));

    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("wander", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
