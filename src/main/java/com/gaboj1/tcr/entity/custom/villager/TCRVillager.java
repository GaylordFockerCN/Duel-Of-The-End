package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.entity.ManySkinEntity;
import com.gaboj1.tcr.init.TCRModSounds;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.server.EntityChangeSkinIDPacket;
import com.gaboj1.tcr.util.DataManager;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;

public class TCRVillager extends Villager implements GeoEntity, ManySkinEntity {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    boolean canTalk = true;

    protected Random r = new Random();
    protected int whatCanISay = 2;//真的不是玩牢大的梗（

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
    public static final int MAX_FEMALE_TYPES = 3;//女性数量
    public TCRVillager(EntityType<? extends Villager> pEntityType, Level pLevel, int skinID) {
        super(pEntityType, pLevel);
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

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("TCRVillagerSkinID", skinID);
        this.getPersistentData().putBoolean("hasSkinID", true);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        skinID = tag.getInt("TCRVillagerSkinID");
        new Thread(()->{
            try {
                Thread.sleep(200);//等两端实体数据互通完才能进行同步操作
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new EntityChangeSkinIDPacket(this.getId(), skinID));
        }).start();
        super.load(tag);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
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
        int j = random.nextInt(5);
        sound2 = switch (j) {
            case 0 -> TCRModSounds.MALE_EYO.get();
            case 1 -> TCRModSounds.MALE_HI.get();
            case 2 -> TCRModSounds.MALE_HENG.get();
            case 3 -> TCRModSounds.MALE_SIGH.get();
            default -> sound2;
        };
        return skinID < 0 ? sound : sound2;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        if(pDamageSource.getEntity() instanceof Player player && this.isClientSide()) {
            talkFuck(player);
        }
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
        }

        //删除工作目标，其他和原版一致

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
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
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

    //不，你不想要铁傀儡
    public boolean wantsToSpawnGolem(long pGameTime) {
        return false;
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
