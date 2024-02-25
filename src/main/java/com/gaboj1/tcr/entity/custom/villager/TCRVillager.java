package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.TCRConfig;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Random;

public class TCRVillager extends TamableAnimal implements GeoEntity {
    public TCRVillager(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    boolean canTalk = true;

    Random r = new Random();
    int whatCanISay = 0;
//    @Override
//    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
//        Brain<Villager> brain = this.brainProvider().makeBrain(pDynamic);
//        this.registerBrainGoals(brain);
//        return brain;
//    }
//    @Override
//    public void refreshBrain(ServerLevel pServerLevel) {
//        Brain<Villager> brain = this.getBrain();
//        brain.stopAll(pServerLevel, this);
//        this.brain = brain.copyWithoutBehaviors();
//        this.registerBrainGoals(this.getBrain());
//    }

    private void registerBrainGoals(Brain<Villager> pVillagerBrain) {
//        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        if (this.isBaby()) {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_BABY);
            pVillagerBrain.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
        }

        //删除工作目标，其他和原版一致

//        pVillagerBrain.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivityWithConditions(Activity.MEET, VillagerGoalPackages.getMeetPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
//        pVillagerBrain.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
//        pVillagerBrain.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
        pVillagerBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pVillagerBrain.setDefaultActivity(Activity.IDLE);
        pVillagerBrain.setActiveActivityIfPossible(Activity.IDLE);
        pVillagerBrain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive()/* && !this.isTrading()*/ && !this.isSleeping() && !pPlayer.isSecondaryUseActive()) {
            if (this.isBaby()) {
                this.setUnhappy();
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                boolean flag = true;//this.getOffers().isEmpty();
                if (pHand == InteractionHand.MAIN_HAND) {
                    if (flag && !this.level().isClientSide) {
                        this.setUnhappy();
                    }

                    pPlayer.awardStat(Stats.TALKED_TO_VILLAGER);
                }

                if (flag && canTalk) {
                    if(TCRConfig.IS_WHITE.get()){//新增对话，其他和原版一样
                        talk(pPlayer);
                    }else {
                        talkFuck(pPlayer);
                    }

                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                } else {
                    if (!this.level().isClientSide/* && !this.offers.isEmpty()*/) {
//                        this.startTrading(pPlayer);
                    }

                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }
            }
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }
    public void talk(Player player){

    }

    public void talk(Player player, Component component){
        player.sendSystemMessage(Component.literal("[").append(this.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)).append("]: ").append(component));
    }

    public void talkFuck(Player player){

    }

    public String getResourceName(){
        return "villager";
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
        TCRConfig.IS_WHITE.set(false);//设为黑方
    }

    //TODO 补全音效
//    protected SoundEvent getAmbientSound() {
//        if (this.isAngry()) {
//            return SoundEvents.WOLF_GROWL;
//        } else if (this.random.nextInt(3) != 0) {
//            return SoundEvents.WOLF_AMBIENT;
//        } else {
//            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
//        }
//    }
//
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        if(pDamageSource.getEntity() instanceof Player player)
            talkFuck(player);
        return SoundEvents.VILLAGER_HURT;
    }
//
//    protected SoundEvent getDeathSound() {
//        return SoundEvents.WOLF_DEATH;
//    }

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
