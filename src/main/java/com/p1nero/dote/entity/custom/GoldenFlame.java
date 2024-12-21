package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.efpatch.GoldenFlamePatch;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.entity.IWanderableEntity;
import com.p1nero.dote.entity.ai.goal.CustomWanderGoal;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.util.ItemUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class GoldenFlame extends DOTEBoss implements IWanderableEntity {
    protected static final EntityDataAccessor<Integer> CHARGING_TIMER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ANTI_FORM_TIMER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> INACTION_TIME = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> IS_BLUE = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHOULD_RENDER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.BOOLEAN);
    private int antiFormCooldown = 0;//FIXME 反神形态无法受伤？？
    private static final int MAX_ANTI_FORM_COOLDOWN = 2400;
    private static final int MAX_ANTI_FORM_TIMER = 1200;
    private int strafingTime;
    private float strafingForward;
    private float strafingClockwise;
    public GoldenFlame(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
//        setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SOLAR.get().getDefaultInstance());
        setItemInHand(InteractionHand.MAIN_HAND, DOTEItems.ROT_GREATSWORD.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.HEAD, DOTEItems.GOLDEN_DRAGON_HELMET.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.CHEST, DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.LEGS, DOTEItems.GOLDEN_DRAGON_LEGGINGS.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.FEET, DOTEItems.GOLDEN_DRAGON_BOOTS.get().getDefaultInstance());
    }

    @Override
    public float getHomeRadius() {
        return 37;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(CHARGING_TIMER, 0);
        getEntityData().define(ANTI_FORM_TIMER, 0);
        getEntityData().define(INACTION_TIME, 0);
        getEntityData().define(IS_BLUE, false);
        getEntityData().define(SHOULD_RENDER, true);
    }

    public void setIsBlue(boolean isBlue){
        getEntityData().set(IS_BLUE, isBlue);
    }

    public boolean isBlue(){
        return getEntityData().get(IS_BLUE);
    }

    public void setShouldRender(boolean shouldRender){
        getEntityData().set(SHOULD_RENDER, shouldRender);
    }

    public boolean shouldRender() {
        return getEntityData().get(SHOULD_RENDER);
    }

    public void startCharging(){
        getEntityData().set(CHARGING_TIMER, 135);
    }

    public void resetCharging(){
        getEntityData().set(CHARGING_TIMER, 0);
    }

    public boolean isCharging(){
        return getEntityData().get(CHARGING_TIMER) > 0;
    }

    public int getChargingTimer(){
        return getEntityData().get(CHARGING_TIMER);
    }

    public void startAntiForm(){
        getEntityData().set(ANTI_FORM_TIMER, MAX_ANTI_FORM_TIMER);
        antiFormCooldown = 1;
    }

    public int getAntiFormTimer(){
        return getEntityData().get(ANTI_FORM_TIMER);
    }

    public int getAntiFormCooldown(){
        return antiFormCooldown;
    }

    public void setInactionTime(int inactionTime){
        getEntityData().set(INACTION_TIME, inactionTime);
    }

    public int getInactionTime(){
        return getEntityData().get(INACTION_TIME);
    }

    @Override
    public void tick() {
        super.tick();

        if(getInactionTime() > 0){
            setInactionTime(getInactionTime() -1);
        }

        //反神形态计时器，持续40秒用拳，时间到了再播动画变身回去
        if(getAntiFormTimer() > 0){
            getEntityData().set(ANTI_FORM_TIMER, Math.max(0, getAntiFormTimer() - 1));
            if(getAntiFormTimer() == MAX_ANTI_FORM_TIMER - 10){
                setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                resetCharging();
            }
            if(getAntiFormTimer() == 1){
                setItemInHand(InteractionHand.MAIN_HAND, DOTEItems.ROT_GREATSWORD.get().getDefaultInstance());
                if(!level().isClientSide){
                    GoldenFlamePatch patch = EpicFightCapabilities.getEntityPatch(this, GoldenFlamePatch.class);
                    patch.playAnimationSynchronized(WOMAnimations.ANTITHEUS_LAPSE, 0.3F);
                }
                antiFormCooldown = MAX_ANTI_FORM_COOLDOWN;
            }
        }

        if(antiFormCooldown > 0){
            antiFormCooldown--;
        }

        //wander控制
        if(getStrafingTime() > 0){
            this.setStrafingTime(this.getStrafingTime() - 1);
            this.getNavigation().stop();
            if(getTarget() != null){
                this.lookAt(getTarget(), 30.0F, 30.0F);
            }
            this.getMoveControl().strafe(this.getStrafingForward() > 0.0F ? 0.0F : this.getStrafingForward(), this.getStrafingClockwise());
        }

        //蓄力播音效
        if(getChargingTimer() > 0 && !level().isClientSide){
            getEntityData().set(CHARGING_TIMER, Math.max(0, getChargingTimer() - 1));
            if(getTarget() != null){
                getLookControl().setLookAt(getTarget(), 30, 30);
            }
        }

        if(getChargingTimer() == 115){
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.6F);
        }

        if(getChargingTimer() == 95){
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.65F);
        }

        if(getChargingTimer() == 65){
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.7F);
        }

        if(getChargingTimer() == 35){
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.5F);
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.5F, 0.5F);
        }

    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 752.0f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 3)
                .add(EpicFightAttributes.WEIGHT.get(), 3)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(1, new CustomWanderGoal<>(this, 1.0, true, 64));//无效
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        if(!shouldRender()){
            return false;
        }
        return super.hurt(source, p_21017_);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        level().playSound(null , getX(), getY(), getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS,1,1);
        if(source.getEntity() instanceof ServerPlayer player){
            DialogueComponentBuilder builder = new DialogueComponentBuilder(this);
            switch (DOTEArchiveManager.getWorldLevel()){
                case 0:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(0)), false);
                    break;
                case 1:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(1)), false);
                    break;
                default:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(2)), false);
            }
            DOTEArchiveManager.BIOME_PROGRESS_DATA.setGoldenFlameFought(true);
            if(DOTEArchiveManager.BIOME_PROGRESS_DATA.isSenbaiFought()){
                DOTEAdvancementData.getAdvancement("golden_flame", player);
            }
            if(!DOTEArchiveManager.BIOME_PROGRESS_DATA.isBoss2fought()){
                ItemUtil.addItem(player, DOTEItems.ADVENTURESPAR.get(), 25 * (DOTEArchiveManager.getWorldLevel() + 1));
            }
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.GOLDEN_FLAME_BGM.get();
    }

    @Override
    public int getStrafingTime() {
        return strafingTime;
    }

    @Override
    public void setStrafingTime(int strafingTime) {
        this.strafingTime = strafingTime;
        setInactionTime(strafingTime);
    }

    @Override
    public float getStrafingForward() {
        return strafingForward;
    }

    @Override
    public void setStrafingForward(float strafingForward) {
        this.strafingForward = strafingForward;
    }

    @Override
    public float getStrafingClockwise() {
        return strafingClockwise;
    }

    @Override
    public void setStrafingClockwise(float strafingClockwise) {
        this.strafingClockwise = strafingClockwise;
    }

}
