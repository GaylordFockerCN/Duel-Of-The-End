package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.entity.ManySkinEntity;
import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerTasks;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.AddVillagerParticlePacket;
import com.gaboj1.tcr.util.SaveUtil;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;

public abstract class TCRVillager extends Villager implements GeoEntity, ManySkinEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected EntityType<? extends TCRVillager> entityType;
    boolean canTalk = true;

    protected Random r = new Random();
    protected int whatCanISay = 6;//真的不是玩牢大的梗（
    private boolean isAngry;
    protected int angryTick = 10;
    protected int maxAngryTick = 10;

    /**
     * 用于区别村民的外貌和声音，负数代表女性
     * 这玩意儿发现得太晚，之前自己写了个skinID，发包什么的都写好了，现在全删了T T
     */
    private static final EntityDataAccessor<Integer> DATA_SKIN_ID = SynchedEntityData.defineId(TCRVillager.class, EntityDataSerializers.INT);

    //共有多少种村民，会根据村民数量来随机一个id，从[0,TYPES]中取。负数代表女性
    public static final int RANDOM_SKIN = Integer.MAX_VALUE;
    public TCRVillager(EntityType<? extends TCRVillager> pEntityType, Level pLevel, int skinID) {
        super(pEntityType, pLevel);
        this.entityType = pEntityType;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);//抛弃大脑后最后的尊严...
        if(skinID == RANDOM_SKIN){
            if(random.nextBoolean()){
                skinID = random.nextInt(getMaleTypeCnt())+1;
            }else {
                skinID = -(random.nextInt(getFemaleTypeCnt())+1);
            }
        }
        this.getEntityData().define(DATA_SKIN_ID, skinID);
    }

    /**
     * 不关心skinID是多少
     */
    public TCRVillager(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        this(pEntityType, pLevel, 142857);
    }

    public int getMaleTypeCnt(){
        return 0;
    }

    public int getFemaleTypeCnt(){
        return 0;
    }

    public boolean isAngry() {
        return isAngry;
    }

    /**
     * 方便代码让它攻击某人
     */
    @Override
    public void setTarget(@Nullable LivingEntity entity) {
        super.setTarget(entity);
        isAngry = true;
    }

    /**
     * 区别于getID
     */
    @Override
    public int getSkinID() {
        return this.getEntityData().get(DATA_SKIN_ID);
    }
    @Override
    public void setSkinID(int newSkinID) {
        this.getEntityData().set(DATA_SKIN_ID, newSkinID);
    }

    public boolean isFemale(){
        return getSkinID() < 0;
    }

    /**
     *
     * @return 是否是尊者（所有村庄的头领）
     */
    public boolean isElder(){
        return getSkinID() == -114514;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TCRVillagerSkinID", this.getEntityData().get(DATA_SKIN_ID));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(DATA_SKIN_ID,tag.getInt("TCRVillagerSkinID"));
    }

    /**
     * 实现控制生气
     * 因为如果分别判断服务端和客户端的话，setTarget容易出错，所以发个包来实现客户端粒子效果。
     */
    @Override
    public void tick() {
        if(!isClientSide()){
            if(isAngry()){
                if(angryTick < 0){
                    isAngry = false;
                    setTarget(null);
                }else {
                    angryTick--;

                    //不要太频繁产生粒子特效
                    if(angryTick%5 == 0){
                        PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new AddVillagerParticlePacket(this.getId()));
                    }
                }
            }else {
                angryTick = maxAngryTick;
            }
        }

        super.tick();
    }

    public void addParticlesAroundSelf(SimpleParticleType particleTypes){
        super.addParticlesAroundSelf(particleTypes);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        // 1/5概率叫，降低叫的频率
        if(getRandom().nextInt(5) != 1){
            return null;
        }
        //是坏人就唉声叹气一下
        if(SaveUtil.biome1.choice == 1){
            SoundEvent sound = TCRSounds.FEMALE_SIGH.get();
            int i = random.nextInt(2);
            sound = switch (i) {
                case 0 -> TCRSounds.FEMALE_SIGH.get();
                case 1 -> TCRSounds.FEMALE_HENG.get();
                default -> sound;
            };
            SoundEvent sound2 = TCRSounds.MALE_SIGH.get();
            int j = random.nextInt(2);
            sound2 = switch (j) {
                case 0 -> TCRSounds.MALE_HENG.get();
                case 1 -> TCRSounds.MALE_SIGH.get();
                default -> sound2;
            };
            return isFemale() ? sound : sound2;
        }

        SoundEvent sound = TCRSounds.FEMALE_VILLAGER_OHAYO.get();
        int i = random.nextInt(6);
        sound = switch (i) {
            case 0 -> TCRSounds.FEMALE_VILLAGER_HI.get();
            case 1 -> TCRSounds.FEMALE_VILLAGER_HELLO.get();
            case 2 -> TCRSounds.FEMALE_VILLAGER_HENG.get();
            case 3 -> TCRSounds.FEMALE_VILLAGER_HI_THERE.get();
            case 4 -> TCRSounds.FEMALE_VILLAGER_EI.get();
            default -> sound;
        };
        SoundEvent sound2 = TCRSounds.MALE_HELLO.get();
        int j = random.nextInt(3);
        sound2 = switch (j) {
            case 0 -> TCRSounds.MALE_EYO.get();
            case 1 -> TCRSounds.MALE_HI.get();
            default -> sound2;
        };
        return isFemale() ? sound : sound2;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return isFemale() ? TCRSounds.FEMALE_VILLAGER_HURT.get() : TCRSounds.MALE_GET_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return isFemale() ?  TCRSounds.FEMALE_VILLAGER_DEATH.get() : TCRSounds.MALE_DEATH.get();
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
     * copy from 原版,增加自卫目标 {@link com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask}
     * 村民带大脑的生物，普通goal无效，只能通过注册大脑goal来影响村民行为。
     * */
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        if (this.isBaby()) {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_BABY);
            pVillagerBrain.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
        }else {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_DEFAULT);
        }

        //删除工作目标，增加自卫目标和对话目标，其他和原版一致
        pVillagerBrain.addActivity(Activity.CORE, TCRVillagerTasks.getTCRVillagerCorePackage(this));

        pVillagerBrain.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivityWithConditions(Activity.MEET, VillagerGoalPackages.getMeetPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
        pVillagerBrain.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
        pVillagerBrain.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
        pVillagerBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pVillagerBrain.setDefaultActivity(Activity.IDLE);
        pVillagerBrain.setActiveActivityIfPossible(Activity.IDLE);
        pVillagerBrain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)//最大血量
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
    protected @NotNull Component getTypeName() {
        return this.getType().getDescription();
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player pPlayer, @NotNull InteractionHand pHand) {

        if ( this.isAlive() && !this.isTrading() && !this.isSleeping() && !pPlayer.isSecondaryUseActive()) {
            if (canTalk && pPlayer instanceof ServerPlayer && pHand == InteractionHand.MAIN_HAND) {
                talk(pPlayer, false);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    /**
     *
     * @param player 对话的玩家
     * @param isFWord 是否说藏话
     */
    public void talk(Player player, boolean isFWord){}

    /**
     * 判断是否是友好的，如果选择boss阵营则为false，需要每个群系独自判断。。。
     */
    public boolean isFriendly(){
        return true;
    }

    public void talk(Player player, Component component){
        if(player != null)
            player.displayClientMessage(Component.literal("[").append(this.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append("]: ").append(component),false);
    }

    //用于Geckolib模型区分贴图
    public String getResourceName() {
        return "";
    }

    /**
     * 不，你不想要铁傀儡，你已经会反击了
     */
    public boolean wantsToSpawnGolem(long pGameTime) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float v) {

        Entity entity = source.getEntity();
        if(entity instanceof Player player) {
            if(this.isClientSide()){
                talk(player, true);
            }
        }

        //设置反击目标
        if(entity instanceof LivingEntity livingEntity){
            setTarget(livingEntity);
        }
        isAngry = true;

        return super.hurt(source, v);
    }

    /**
     * 取消变女巫
     */
    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightningBolt) {

        this.setRemainingFireTicks(this.getRemainingFireTicks() + 1);
        if (this.getRemainingFireTicks() == 0) {
            this.setSecondsOnFire(8);
        }

        this.hurt(this.damageSources().lightningBolt(), lightningBolt.getDamage());

    }

    public void playAttackAnim(){
        triggerAnim("Attack","attack");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller",
                10, this::predicate));
        controllers.add(new AnimationController<>(this, "Attack", 10, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("fight")));
    }

    protected <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
//        if(isAngry) {
//            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.attack", Animation.LoopType.LOOP));
//        } else
        if(tAnimationState.isMoving() && !(this instanceof IStationaryVillager)) {
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
        return Component.translatable(entityType.getDescriptionId());
    }

}
