package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.ai.goal.BossRecoverGoal;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.entity.ai.goal.RangeMeleeAttackGoal;
import com.gaboj1.tcr.entity.custom.boss.TCRBoss;
import com.gaboj1.tcr.entity.custom.biome1.SpriteEntity;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.EntityUtil;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.RegistryObject;
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
import java.util.UUID;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;


public class YggdrasilEntity extends TCRBoss implements GeoEntity{
    EntityType<?> entityType = TCREntities.YGGDRASIL.get();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(YggdrasilEntity.class, EntityDataSerializers.INT);

    private static final UUID MODIFY_HEALTH = UUID.fromString("c9d011ac-f90f-11ed-a05b-1919bb114514");
    private boolean canBeHurt;
    private int hurtTimer;
    private int shootTimer = 0;
    @Nullable
    private Entity shootTarget;
    private int recoverTimer = 0;
    private int treeClawTimer = 0;
    private int flowerTimer = 0;
    private boolean flowerType;
    private int recoverAnimTimer = 0;
    public final int maxRecoverTimer = 200;
    private final List<Integer> mobs = new ArrayList<>();
    private static final List<RegistryObject<Block>> FLOWER_BLOCKS = new ArrayList<>();

    public YggdrasilEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_, BossEvent.BossBarColor.BLUE);
        FLOWER_BLOCKS.add(TCRBlocks.LAZY_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.MELANCHOLIC_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.WITHERED_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.LAZY_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.WITHERED_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.THIRST_BLOOD_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.POTTED_MELANCHOLIC_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.BLUE_MUSHROOM);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1422)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.30f)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0,new BossRecoverGoal(this, 100));
        this.goalSelector.addGoal(1,new ConversationTriggerGoal(this));
        this.goalSelector.addGoal(2,new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(3, new RangeMeleeAttackGoal(this, 1.0, true, 22, 80){
            @Override
            public boolean canUse() {
                return YggdrasilEntity.this.getEntityData().get(IS_FIGHTING) && shootTimer <= 0 && flowerTimer <=0 && treeClawTimer <=0 && super.canUse();
            }
        });
        this.goalSelector.addGoal(4,new RecoverGoal(this));
        this.goalSelector.addGoal(5, new SpawnTreeClawGoal(this));
        this.goalSelector.addGoal(6, new SpawnFlowersGoal(this));
//        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));

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
            //二次挑战则直接死
            triggerAnim("Death","death");
            superDie(source);
            return;
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
        SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_KILL_BOSS);
        SaveUtil.TASK_SET.add(SaveUtil.Biome1ProgressData.TASK_BACK_TO_ELDER);
    }


    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float v) {
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
            if(recoverAnimTimer > 0){
                recoverAnimTimer--;
                setHealth(getHealth() + 2);
                for(Entity entity : EntityUtil.getNearByEntities(this, 30)){
                    if(entity instanceof Player player){
                        player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.boss1tip2"),true);
                    }
                }
            }

            //恢复的倒计时，这个timer由goal触发
            if(recoverTimer <= 0){
                boolean canRecover = false;
                for(int entityID : mobs){
                    Entity entity = level().getEntity(entityID);
                    if(entity instanceof SpriteEntity spriteEntity && spriteEntity.getHealth() >= spriteEntity.getMaxHealth() / 3){
                        canRecover = true;
                        break;
                    }
                }
                if(canRecover){
                    recover();
                }
            } else if(recoverTimer <= maxRecoverTimer){
                recoverTimer--;
            }

            //树爪的倒计时，这个timer由goal触发
            if(treeClawTimer > 0){
                treeClawTimer--;
                if(treeClawTimer == 1){
                    List<Player> players = this.level().getNearbyPlayers(TargetingConditions.DEFAULT, this, getPlayerAABB(getOnPos(), 20));
//                    List<Player> players2 = this.level().getNearbyPlayers(TargetingConditions.forNonCombat(), this, getPlayerAABB(getOnPos(), 20));//创造测试用
//                    players.addAll(players2);
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
                if(shootTimer > 50){
                    if(getTarget() != null){
                        this.getLookControl().setLookAt(getTarget());
                    }
                }
                double x, y, z;
                if(shootTimer == 40 && shootTarget != null){
                    MagicProjectile projectile = new MagicProjectile(level(), this);
                    x = shootTarget.getX() - this.getX();
                    y = shootTarget.getY(0.3333) - projectile.getY();
                    z = shootTarget.getZ() - this.getZ();
                    double $$5 = Math.sqrt(x * x + z * z) * 0.2;
                    projectile.setDamage(((float) Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).getValue()) * (SaveUtil.getWorldLevel() + 1) * 0.7F);
                    projectile.shoot(x, y + $$5, z, 1.5F, 10.0F);
                    level().addFreshEntity(projectile);
                }
                if(shootTimer == 36 || shootTimer == 51){
                    MagicProjectile projectile = new MagicProjectile(level(), this);
                    if(random.nextBoolean() && shootTarget != null){
                        x = shootTarget.getX() - this.getX();
                        y = shootTarget.getY(0.3333) - projectile.getY();
                        z = shootTarget.getZ() - this.getZ();
                        double h = Math.sqrt(x * x + z * z) * 0.2;
                        y += h;
                    } else {
                        x = getViewVector(1.0F).x;
                        y = getViewVector(1.0F).y;
                        z = getViewVector(1.0F).z;
                    }
                    projectile.setDamage(((float) Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).getValue() * (SaveUtil.getWorldLevel() + 1) * 0.7F));
                    projectile.shoot(x, y, z, 1.5F, 10.0F);
                    level().addFreshEntity(projectile);
                }
            }

            //花海技能
            if(flowerTimer > 0){
                flowerTimer--;
                if(flowerTimer < 60){
                    int dis = 60 - flowerTimer;
                    //十字花
                    if(flowerType){
                        for(int i = 0; i < dis / 2; i++){
                            int offsetX = dis * (random.nextBoolean() ? 1 : -1) + random.nextInt(3);
                            int offsetZ = dis * (random.nextBoolean() ? 1 : -1) + random.nextInt(3);
                            BlockPos pos = getOnPos().offset(offsetX, 1, offsetZ);
                            if(level().getBlockState(pos).isAir() && !level().getBlockState(pos.below()).isAir()){
                                level().explode(this, this.damageSources().explosion(this, this), null, pos.getCenter(), 2, false, Level.ExplosionInteraction.NONE);
                                level().setBlock(pos, FLOWER_BLOCKS.get(random.nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                            }
                        }
                    //直线花
                    } else {
                        Vec3 target = getViewVector(1.0F).normalize().scale(dis);
                        BlockPos pos = getOnPos().offset((int) target.x, 1,(int) target.z);
                        if(level().getBlockState(pos).isAir() && !level().getBlockState(pos.below()).isAir()){
                            level().explode(this, this.damageSources().explosion(this, this), null, pos.getCenter(), 3, false, Level.ExplosionInteraction.NONE);
                            level().setBlock(pos, FLOWER_BLOCKS.get(random.nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                            level().setBlock(pos.east().east(), FLOWER_BLOCKS.get(random.nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                            level().setBlock(pos.west().west(), FLOWER_BLOCKS.get(random.nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                            level().setBlock(pos.north().north(), FLOWER_BLOCKS.get(random.nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                            level().setBlock(pos.south().south(), FLOWER_BLOCKS.get(random.nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                        }
                    }

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
        return TCRSounds.BIOME1BOSS_FIGHT.get();
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
        return TCRSounds.YGGDRASIL_AMBIENT_SOUND.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return TCRSounds.YGGDRASIL_CRY.get();
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

        if(getEntityData().get(IS_SHADER)){
            builder.start(BUILDER.buildDialogueAnswer(entityType,0))
                    .addChoice(BUILDER.buildDialogueOption(entityType,-1),BUILDER.buildDialogueAnswer(entityType,1))
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)-1);
            getEntityData().set(IS_FIGHTING, true);
        } else if(serverData.getBoolean("canGetBossReward")) {
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
                    .addFinalChoice(BUILDER.buildDialogueOption(entityType,0),(byte)-1);
            getEntityData().set(IS_FIGHTING, true);
        }
        Minecraft.getInstance().setScreen(builder.build());
    }

    /**
     * 根据手持的枯萎之触的物品数量来削弱树魔的属性
     * FIXME 失效了？？
     */
    private void modifyAttribute(int count){
        AttributeModifier healthModify = new AttributeModifier(MODIFY_HEALTH, "healthModify", - 0.15 * count, AttributeModifier.Operation.MULTIPLY_BASE);
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(healthModify);
        setHealth(getMaxHealth());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_FIND_ELDER1);
        SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_BACK_TO_BOSS);
        switch (interactionID){
            //初次对话结束，就是变成开始打了
            case -1:
                SaveUtil.biome1.isBossTalked = true;
                getEntityData().set(STATE, 1);
                getEntityData().set(IS_FIGHTING, true);
                int total = 0;
                for(ItemStack itemStack : player.getInventory().items){
                    if(itemStack.is(TCRItems.WITHERING_TOUCH.get())){
                        total += itemStack.getCount();
                    }
                }
                if(total > 0){
                    modifyAttribute(Math.min(total, 3));
                    this.hurt(damageSources().magic(), 1.14514F);//为了播个音效
                    chat(BUILDER.buildDialogueAnswer(entityType, -1, false));
                }
                break;
            //选择处决
            case 1:
                chat(BUILDER.buildDialogueAnswer(entityType, 14, false));
                SaveUtil.biome1.isBossFought = true;//注意要处决或者接任务后再调这个，注意考虑对话中断的情况
                realDie(player.damageSources().playerAttack(player));
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_KILL_BOSS);
                SaveUtil.TASK_SET.add(SaveUtil.Biome1ProgressData.TASK_BACK_TO_ELDER);
                break;
            //选择接任务
            case 2:
                getEntityData().set(IS_FIGHTING, false);
                setTarget(null);
                chat(BUILDER.buildDialogueAnswer(entityType, 15, false));
                SaveUtil.TASK_SET.remove(SaveUtil.Biome1ProgressData.TASK_KILL_BOSS);
                SaveUtil.TASK_SET.add(SaveUtil.Biome1ProgressData.TASK_KILL_ELDER);
                SaveUtil.biome1.isBossFought = true;//注意要处决或者接任务后再调这个，注意考虑对话中断的情况
                break;
            //任务成功
            case 3:
                SaveUtil.biome1.finish(SaveUtil.BiomeProgressData.BOSS, ((ServerLevel) level()));
                if(!DataManager.boss1LootGot.get(player)){
                    ItemStack wand = TCRItems.TREE_SPIRIT_WAND.get().getDefaultInstance();
                    wand.getOrCreateTag().putBoolean("fromBoss", true);
                    ItemUtil.addItem(player,wand.getItem(),1);
                    ItemUtil.addItem(player,TCRItems.DENSE_FOREST_CERTIFICATE.get(),1);
                    DataManager.boss1LootGot.put(player, true);
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
                return !yggdrasil.isFighting() && yggdrasil.distanceTo(player) < 10 && !SaveUtil.biome1.isBossTalked;
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
    public static class SpawnTreeClawGoal extends Goal {
        private final YggdrasilEntity yggdrasil;
        private int shootInterval;
        public final int shootIntervalMax = 200;

        public SpawnTreeClawGoal(YggdrasilEntity yggdrasil) {
            this.yggdrasil = yggdrasil;
            shootInterval = shootIntervalMax;
        }

        @Override
        public boolean canUse() {
            return --this.shootInterval <= 0 && yggdrasil.getEntityData().get(IS_FIGHTING) && SaveUtil.biome1.canAttackBoss() && yggdrasil.shootTimer <= 0 && yggdrasil.flowerTimer <= 0;
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
     * 定时召唤树爪，击破树爪才能攻击boss
     */
    public static class SpawnFlowersGoal extends Goal {
        private final YggdrasilEntity yggdrasil;
        private int shootInterval;
        public final int shootIntervalMax = 300;

        public SpawnFlowersGoal(YggdrasilEntity yggdrasil) {
            this.yggdrasil = yggdrasil;
            shootInterval = 0;
        }

        @Override
        public boolean canUse() {
            if(shootInterval > 0){
                shootInterval--;
            }
            return this.shootInterval <= 0 && yggdrasil.getEntityData().get(IS_FIGHTING) && SaveUtil.biome1.canAttackBoss() && yggdrasil.getHealth() < yggdrasil.getMaxHealth() / 2 && yggdrasil.shootTimer <= 0 && yggdrasil.treeClawTimer <= 0;
        }

        @Override
        public void start() {
            yggdrasil.triggerAnim("Summon", "flower");
            for(Entity entity : EntityUtil.getNearByEntities(yggdrasil, 30)){
                if(entity instanceof Player player){
                    player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.boss1tip3"),true);
                }
            }
            yggdrasil.flowerTimer = 76;
            yggdrasil.turningLock(76);
            yggdrasil.movementLock(76);
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
            yggdrasil.getEntityData().set(STATE, 2);
            yggdrasil.triggerAnim("Summon", "summon");
            List<Player> players = this.yggdrasil.level().getNearbyPlayers(TargetingConditions.DEFAULT, yggdrasil, getPlayerAABB(yggdrasil.getOnPos(), 10));
//            List<Player> players2 = this.yggdrasil.level().getNearbyPlayers(TargetingConditions.forNonCombat(), yggdrasil, getPlayerAABB(yggdrasil.getOnPos(), 10));//创造测试用
//            players.addAll(players2);
            for(Player player : players){
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.boss1tip2"),true);
                if(yggdrasil.mobs.size() <= 10){
                    SpriteEntity mob = TCREntities.SPRITE.get().spawn(((ServerLevel) yggdrasil.level()), player.getOnPos().above(3), MobSpawnType.NATURAL);
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
        recoverAnimTimer = 100;
        movementLock(100);
        turningLock(100);
    }

    /**
     * 播放动画并发射
     */
    public boolean doHurtTarget(@NotNull Entity target){
        getLookControl().setLookAt(target);
        triggerAnim("Attack","attack");
        shootTimer = 60;
        if(getRandom().nextBoolean() && getHealth() < getMaxHealth() / 2){
            shootTarget = target;
        } else {
            shootTarget = null;
        }
        turningLock(60);
        movementLock(60);
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //非攻击状态动画
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));

        //自定义触发
        controllers.add(new AnimationController<>(this, "Recover", 5, state -> PlayState.CONTINUE)
                .triggerableAnim("recover", RawAnimation.begin().then("recover", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "Summon", 5, state -> PlayState.CONTINUE)
                .triggerableAnim("summon", RawAnimation.begin().then("attack1", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("flower", RawAnimation.begin().then("attack3", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "Attack", 5, state -> PlayState.CONTINUE)
                .triggerableAnim("attack", RawAnimation.begin().then("attack2", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "Death", 5, state -> PlayState.CONTINUE)
                .triggerableAnim("death", RawAnimation.begin().then("death", Animation.LoopType.PLAY_ONCE)));

    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("wander", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
