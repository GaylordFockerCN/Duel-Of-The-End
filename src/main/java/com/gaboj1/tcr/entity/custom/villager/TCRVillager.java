package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.entity.ManySkinEntity;
import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerTasks;
import com.gaboj1.tcr.entity.custom.tree_monsters.MiddleTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.SmallTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.TreeGuardianEntity;
import com.gaboj1.tcr.init.TCRModSounds;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.server.EntityChangeSkinIDPacket;
import com.gaboj1.tcr.util.DataManager;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;

public class TCRVillager extends Villager implements GeoEntity, ManySkinEntity {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected EntityType<? extends TCRVillager> entityType;
    boolean canTalk = true;

    protected Random r = new Random();
    protected int whatCanISay = 2;//真的不是玩牢大的梗（
    private boolean isAngry;
    private int angryTick = 10;
    public boolean isAngry() {
        return isAngry;
    }

    //区别于getID
    @Override
    public int getSkinID() {
        return skinID;
    }
    @Override
    public void setSkinID(int newSkinID) {
        skinID = newSkinID;
    }

    //用于随机生成不同的皮肤和声音
    protected int skinID = 0;

    //共有多少种村民，会根据村民数量来随机一个id，从[0,TYPES]中取。负数代表女性
    public static final int MAX_TYPES = 5;//男性数量
    public static final int MAX_FEMALE_TYPES = 4;//女性数量
    public TCRVillager(EntityType<? extends TCRVillager> pEntityType, Level pLevel, int skinID) {
        super(pEntityType, pLevel);
        this.entityType = pEntityType;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);//抛弃大脑后最后的尊严...
//        if(!this.getPersistentData().getBoolean("hasSkinID")){
            this.skinID = skinID;
//        }
        if(!pLevel.isClientSide){
            new Thread(()->{
                try {
                    Thread.sleep(200);//等两端实体数据互通完才能进行同步操作
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new EntityChangeSkinIDPacket(this.getId(), skinID));
            }).start();
        }

    }

    public boolean isFemale(){
        return skinID < 0;
    }

    /**
     *
     * @return 是否是尊者（所有村庄的头领）
     */
    public boolean isElder(){
        return skinID == -114514;
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("TCRVillagerSkinID", skinID);
        this.getPersistentData().putBoolean("hasSkinID", true);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        skinID = tag.getInt("TCRVillagerSkinID");
//        PacketRelay.sendDelay(500,()->PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new EntityChangeSkinIDPacket(this.getId(), skinID)));
        new Thread(()->{
            try {
                Thread.sleep(200);//等两端实体数据互通完才能进行同步操作（更新了EntityChangeSkinIDPacket逻辑后sleep可有可无）
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new EntityChangeSkinIDPacket(this.getId(), skinID));
        }).start();
        super.load(tag);
    }

    //实现控制生气
    @Override
    public void tick() {
//        if(!isClientSide()){
            if(isAngry){
                if(angryTick < 0){
                    isAngry = false;
                    if(!isClientSide()){//不限制好像不会在服务端生效？
                        setTarget(null);
                    }
                    setTarget(null);
                }else {
                    angryTick--;
                    this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);//似乎只在客户端生效
                }
            }else {
                angryTick = 100;
            }
//        }

        super.tick();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {

        LocalPlayer player = Minecraft.getInstance().player;
        //是坏人就唉声叹气一下
        if(DataManager.isWhite.isLocked() && player!=null && DataManager.isWhite.getBool(player)){
            SoundEvent sound = TCRModSounds.FEMALE_SIGH.get();
            int i = random.nextInt(2);
            sound = switch (i) {
                case 0 -> TCRModSounds.FEMALE_SIGH.get();
                case 1 -> TCRModSounds.FEMALE_HENG.get();
                default -> sound;
            };
            SoundEvent sound2 = TCRModSounds.MALE_SIGH.get();
            int j = random.nextInt(2);
            sound2 = switch (j) {
                case 0 -> TCRModSounds.MALE_HENG.get();
                case 1 -> TCRModSounds.MALE_SIGH.get();
                default -> sound2;
            };
            return skinID < 0 ? sound : sound2;
        }

        SoundEvent sound = TCRModSounds.FEMALE_VILLAGER_OHAYO.get();
        int i = random.nextInt(6);
        sound = switch (i) {
            case 0 -> TCRModSounds.FEMALE_VILLAGER_HI.get();
            case 1 -> TCRModSounds.FEMALE_VILLAGER_HELLO.get();
            case 2 -> TCRModSounds.FEMALE_VILLAGER_HENG.get();
            case 3 -> TCRModSounds.FEMALE_VILLAGER_HI_THERE.get();
            case 4 -> TCRModSounds.FEMALE_VILLAGER_EI.get();
            default -> sound;
        };
        SoundEvent sound2 = TCRModSounds.MALE_HELLO.get();
        int j = random.nextInt(3);
        sound2 = switch (j) {
            case 0 -> TCRModSounds.MALE_EYO.get();
            case 1 -> TCRModSounds.MALE_HI.get();
            default -> sound2;
        };
        return skinID < 0 ? sound : sound2;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return skinID < 0 ? TCRModSounds.FEMALE_VILLAGER_HURT.get() : TCRModSounds.MALE_GET_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return skinID < 0 ?  TCRModSounds.FEMALE_VILLAGER_DEATH.get() : TCRModSounds.MALE_DEATH.get();
    }

    /**
     * copy from 原版
    * */
    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> pDynamic) {
        Brain<Villager> brain = this.brainProvider().makeBrain(pDynamic);
        this.registerBrainGoals(brain);
        return brain;
    }
    /**
     * copy from 原版
     * */
    @Override
    public void refreshBrain(@NotNull ServerLevel pServerLevel) {
        Brain<Villager> brain = this.getBrain();
        brain.stopAll(pServerLevel, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }


    /**
     * copy from 原版
     * */
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        if (this.isBaby()) {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_BABY);
            pVillagerBrain.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
        }else {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_DEFAULT);
        }

        //删除工作目标，增加自卫目标，其他和原版一致
        pVillagerBrain.addActivity(Activity.CORE, TCRVillagerTasks.getGuardCorePackage());

        pVillagerBrain.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivityWithConditions(Activity.MEET, VillagerGoalPackages.getMeetPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
        pVillagerBrain.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivity(Activity.FIGHT, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));//FIGHT不管用..
        pVillagerBrain.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
        pVillagerBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pVillagerBrain.setDefaultActivity(Activity.IDLE);
        pVillagerBrain.setActiveActivityIfPossible(Activity.IDLE);
        pVillagerBrain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
    }

    //如果继承自村民则Goal无效。。。。把村民降级成普通生物处理又太弱智。。最后被迫去学Brain怎么写
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                e->isAngry||(e instanceof ServerPlayer player && !DataManager.isWhite.getBool(player))));//被激怒或者是坏人就攻击（别惹村民！）
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SmallTreeMonsterEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MiddleTreeMonsterEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, TreeGuardianEntity.class, false));
//        this.goalSelector.addGoal(5, new PanicGoal(this, 0.5D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        super.registerGoals();
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 2.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 2.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速 0.5不匹配动画awa
                .build();
    }

    /**
     * @see Entity#getTypeName()
     * 重写村民的getTypeName防止因为职业而读取不到对话信息
    */
    @Override
    protected Component getTypeName() {
        return this.getType().getDescription();
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if ( this.isAlive() && !this.isTrading() && !this.isSleeping() && !pPlayer.isSecondaryUseActive()) {
            if (this.isBaby()) {
                this.setUnhappy();
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                boolean flag = true;

                if (flag && canTalk && pPlayer instanceof ServerPlayer player && pHand == InteractionHand.MAIN_HAND) {
                    if(DataManager.isWhite.getBool(player)){//新增对话，其他和原版一样
                        talk(pPlayer);
                    }else {
                        talkFuck(pPlayer);
                    }

                } else {
                    if (!this.level().isClientSide && !this.offers.isEmpty()) {
//                        this.startTrading(pPlayer);
                    }

                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }
    public void talk(Player player){}

    public void talkFuck(Player player){}

    public void talk(Player player, Component component){
        if(player != null)
            player.sendSystemMessage(Component.literal("[").append(this.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append("]: ").append(component));
    }

    //用于Geckolib模型区分贴图
    public String getResourceName() {
        return "pastoral_plain_villager"+ skinID;
    }

    private void setUnhappy() {
//        this.setUnhappyCounter(40);
        if (!this.level().isClientSide()) {
            this.playSound(SoundEvents.VILLAGER_NO, this.getSoundVolume(), this.getVoicePitch());//TODO 替换音效
        }
    }

//    //不，你不想要铁傀儡
//    public boolean wantsToSpawnGolem(long pGameTime) {
//        return false;
//    }


//    TODO:替换成守卫
//    @Override
//    public void spawnGolemIfNeeded(ServerLevel p_35398_, long p_35399_, int p_35400_) {
//        if (this.wantsToSpawnGolem(p_35399_)) {
//            AABB aabb = this.getBoundingBox().inflate(10.0D, 10.0D, 10.0D);
//            List<Villager> list = p_35398_.getEntitiesOfClass(Villager.class, aabb);
//            List<Villager> list1 = list.stream().filter((p_186293_) -> {
//                return p_186293_.wantsToSpawnGolem(p_35399_);
//            }).limit(5L).collect(Collectors.toList());
//            if (list1.size() >= p_35400_) {
//                if (SpawnUtil.trySpawnMob(EntityType.IRON_GOLEM, MobSpawnType.MOB_SUMMONED, p_35398_, this.blockPosition(), 10, 8, 6, SpawnUtil.Strategy.LEGACY_IRON_GOLEM).isPresent()) {
//                    list.forEach(GolemSensor::golemDetected);
//                }
//            }
//        }
//    }

    @Override
    public boolean hurt(DamageSource source, float v) {

        Entity entity = source.getEntity();
        if(entity instanceof Player player) {
            if(this.isClientSide()){
                talkFuck(player);
            }else {
                //如果已经锁定好人方则无法伤害村民
                if(DataManager.isWhite.isLocked() && DataManager.isWhite.getBool(player)){
                    player.displayClientMessage(Component.literal("info.the_casket_of_reveries.alreadyAddWhite"),true);
                    return false;
                }
            }
            isAngry = true;//不限制服务端是为了客户端要渲染生气
        }

        //设置反击目标
        if(entity instanceof LivingEntity livingEntity){
            setTarget(livingEntity);
        }

        return super.hurt(source, v);
    }

    @Override
    public void die(DamageSource pCause) {
        super.die(pCause);
        if(pCause.getEntity() instanceof Player player) {
            if(!DataManager.isWhite.isLocked()){
                DataManager.isWhite.putBool(player, false);
                DataManager.isWhite.lock(player);
            }
        }
//        this.getServer().getSingleplayerProfile().getProperties();
    }

    public void playAttackAnim(){
        triggerAnim("Attack","attack");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                0, this::predicate));
        controllers.add(new AnimationController<>(this, "Attack", 0, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("fight")));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
//        if(isAngry) {
//            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.attack", Animation.LoopType.LOOP));
//        } else
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.move", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(entityType.getDescriptionId()+skinID);
    }

}
