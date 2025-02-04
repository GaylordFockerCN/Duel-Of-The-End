package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.efpatch.GoldenFlamePatch;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.effect.DOTEEffects;
import com.p1nero.dote.entity.IWanderableEntity;
import com.p1nero.dote.entity.ai.ef.api.TimeStampedEvent;
import com.p1nero.dote.entity.ai.goal.CustomWanderGoal;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.util.EntityUtil;
import com.p1nero.dote.util.ItemUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
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
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Random;

public class GoldenFlame extends DOTEBoss implements IWanderableEntity {
    protected static final EntityDataAccessor<Integer> CHARGING_TIMER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> FLAME_CIRCLE_LIFETIME = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> CURRENT_FLAME_CIRCLE_TIMER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ANTI_FORM_TIMER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> IS_BLUE = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHOULD_RENDER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.BOOLEAN);
    private int antiFormCooldown = 0;//FIXME 反神形态无法受伤？？
    private static final int MAX_ANTI_FORM_COOLDOWN = 2000;
    private static final int MAX_ANTI_FORM_TIMER = 1000;
    private static final int MAX_FLAME_CIRCLE_TIME = 200;
    private boolean healthLock = true;

    public GoldenFlame(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SOLAR.get().getDefaultInstance());
//        setItemInHand(InteractionHand.MAIN_HAND, DOTEItems.ROT_GREATSWORD.get().getDefaultInstance());
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
        getEntityData().define(FLAME_CIRCLE_LIFETIME, 0);
        getEntityData().define(CURRENT_FLAME_CIRCLE_TIMER, 0);
        getEntityData().define(ANTI_FORM_TIMER, 0);
        getEntityData().define(IS_BLUE, false);
        getEntityData().define(SHOULD_RENDER, true);
    }

    public void setIsBlue(boolean isBlue) {
        getEntityData().set(IS_BLUE, isBlue);
    }

    public boolean isBlue() {
        return getEntityData().get(IS_BLUE);
    }

    public void setShouldRender(boolean shouldRender) {
        getEntityData().set(SHOULD_RENDER, shouldRender);
    }

    public boolean shouldRender() {
        return getEntityData().get(SHOULD_RENDER);
    }

    public void startCharging() {
        getEntityData().set(CHARGING_TIMER, 135);
    }

    public void resetCharging() {
        getEntityData().set(CHARGING_TIMER, 0);
    }

    public boolean isCharging() {
        return getEntityData().get(CHARGING_TIMER) > 0;
    }

    public int getChargingTimer() {
        return getEntityData().get(CHARGING_TIMER);
    }

    public int getFlameCircleLifetime(){
        return getEntityData().get(FLAME_CIRCLE_LIFETIME);
    }

    public int getCurrentFlameCircleTimer(){
        return getEntityData().get(CURRENT_FLAME_CIRCLE_TIMER);
    }

    public void setCurrentFlameCircleTimer(int time){
        getEntityData().set(CURRENT_FLAME_CIRCLE_TIMER, time);
    }

    public void setFlameCircleLifeTimeAndStart(int time){
        getEntityData().set(FLAME_CIRCLE_LIFETIME, time);
        getEntityData().set(CURRENT_FLAME_CIRCLE_TIMER, 1);
    }

    public void startAntiForm() {
        getEntityData().set(ANTI_FORM_TIMER, MAX_ANTI_FORM_TIMER);
        antiFormCooldown = 1;
        healthLock = false;
    }

    public int getAntiFormTimer() {
        return getEntityData().get(ANTI_FORM_TIMER);
    }

    public int getAntiFormCooldown() {
        return antiFormCooldown;
    }

    @Nullable
    public GoldenFlamePatch getPatch() {
        return EpicFightCapabilities.getEntityPatch(this, GoldenFlamePatch.class);
    }

    @Override
    public void tick() {
        super.tick();

        if(hasEffect(MobEffects.WITHER)){
            removeEffect(MobEffects.WITHER);
        }

        if(hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
            removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        }

        //反神形态计时器，持续40秒用拳，时间到了再播动画变身回去
        if (getAntiFormTimer() > 0) {
            setInvulnerable(false);//保险，不知为何invulnerable为False
            if(!level().isClientSide){
                getEntityData().set(ANTI_FORM_TIMER, Math.max(0, getAntiFormTimer() - 1));
            }
            if (getAntiFormTimer() == MAX_ANTI_FORM_TIMER - 10) {
                setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                resetCharging();
            }
            if (getAntiFormTimer() <= 1) {
                if (!level().isClientSide) {
                    getEntityData().set(ANTI_FORM_TIMER, 0);
                    setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SOLAR_OBSCURIDAD.get().getDefaultInstance());
                    GoldenFlamePatch patch = EpicFightCapabilities.getEntityPatch(this, GoldenFlamePatch.class);
                    patch.setAttackSpeed(0.8f);
                    patch.playAnimationSynchronized(WOMAnimations.ANTITHEUS_LAPSE, 0.3F);
                    this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 31, 99));
                    patch.clearEvents();
                    patch.addEvent(new TimeStampedEvent(1.7F, (livingEntityPatch) -> {
                        for (int i = 0; i < 5; i++) {
                            EntityType.LIGHTNING_BOLT.spawn(((ServerLevel) level()), livingEntityPatch.getOriginal().getOnPos(), MobSpawnType.MOB_SUMMONED);
                        }
                    }));
                }
                antiFormCooldown = MAX_ANTI_FORM_COOLDOWN;
            }
        } else {
            if (isBlue()) {
                if (getMainHandItem().is(WOMItems.SOLAR.get()) || getMainHandItem().isEmpty()) {
                    setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SOLAR_OBSCURIDAD.get().getDefaultInstance());
                }
            } else {
                if (getMainHandItem().is(WOMItems.SOLAR_OBSCURIDAD.get())) {
                    setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SOLAR.get().getDefaultInstance());
                }
            }
        }

        if (antiFormCooldown > 0) {
            antiFormCooldown--;
        }

        //蓄力播音效
        if (getChargingTimer() > 0 && !level().isClientSide) {
            getEntityData().set(CHARGING_TIMER, Math.max(0, getChargingTimer() - 1));
            if (getTarget() != null) {
                getLookControl().setLookAt(getTarget(), 30, 30);
            }

            if (getChargingTimer() == 130) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.6F);
            }

            if (getChargingTimer() == 100) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.65F);
            }

            if (getChargingTimer() == 70) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.7F);
            }

            if (getChargingTimer() == 40) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 0.5F);
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.5F, 0.5F);
            }
        }

        //copy from SolarPassive
        if(getCurrentFlameCircleTimer() > 0){
            setCurrentFlameCircleTimer(getCurrentFlameCircleTimer() + 1);
            if(getCurrentFlameCircleTimer() >= getFlameCircleLifetime()){
                setCurrentFlameCircleTimer(0);
            }
            int numberOf = 2;
            double r = 0.7;
            double t = 0.01;
            float power = 1.0F + (float) Math.max(MAX_FLAME_CIRCLE_TIME, getCurrentFlameCircleTimer()) / 200.0F * 7.0F;
            if(shouldRender()){
                if(!level().isClientSide){
                    for (Entity entity : EntityUtil.getNearByEntities(this, (int) (r * power))) {
                        entity.setSecondsOnFire(5);
                        if(entity instanceof LivingEntity livingEntity){
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
                            livingEntity.addEffect(new MobEffectInstance(DOTEEffects.BURNT.get(), 100, 1));
                        }
                    }
                } else {
                    for (int i = 0; i < numberOf; ++i) {
                        double theta = 6.283185 * (new Random()).nextDouble();
                        double phi = ((new Random()).nextDouble() - 0.5) * Math.PI * t / r;
                        double x = r * Math.cos(phi) * Math.cos(theta);
                        double y = r * Math.cos(phi) * Math.sin(theta);
                        double z = r * Math.sin(phi);
                        Vec3f direction = new Vec3f((float) x, (float) y, (float) z);
                        OpenMatrix4f rotation = (new OpenMatrix4f()).rotate(-((float) Math.toRadians(this.yBodyRotO)), new Vec3f(0.0F, 1.0F, 0.0F));
                        rotation.rotate((float) Math.toRadians(90.0), new Vec3f(1.0F, 0.0F, 0.0F));
                        OpenMatrix4f.transform3v(rotation, direction, direction);
                        level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) direction.x, this.getY() + 0.1, this.getZ() + (double) direction.z, 0.0, 0.0099, 0.0);
                        level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) (direction.x * (1.0F + (new Random()).nextFloat() * power)), this.getY() + 0.1, this.getZ() + (double) (direction.z * (1.0F + (new Random()).nextFloat() * power)), 0.0, (double) (0.1F + (new Random()).nextFloat() * 0.2F), 0.0);

                        for (int j = 0; j < 3; ++j) {
                            theta = 6.283185 * (new Random()).nextDouble();
                            phi = ((new Random()).nextDouble() - 0.5) * Math.PI * t / r;
                            x = r * Math.cos(phi) * Math.cos(theta);
                            y = r * Math.cos(phi) * Math.sin(theta);
                            z = r * Math.sin(phi);
                            direction = new Vec3f((float) x, (float) y, (float) z);
                            rotation = (new OpenMatrix4f()).rotate(-((float) Math.toRadians(this.yBodyRotO)), new Vec3f(0.0F, 1.0F, 0.0F));
                            rotation.rotate((float) Math.toRadians(90.0), new Vec3f(1.0F, 0.0F, 0.0F));
                            OpenMatrix4f.transform3v(rotation, direction, direction);
                            level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) (direction.x * power), this.getY() + 0.1, this.getZ() + (double) (direction.z * power), 0.0, 0.0099, 0.0);
                        }
                    }
                }
            }

        }

    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1500.0f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(Attributes.ARMOR, 10.0f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 4)
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
    public boolean hurt(@NotNull DamageSource source, float damageValue) {
        if (!shouldRender()) {
            return false;
        }
        if(getHealthRatio() <= 0.2 && healthLock){
            damageValue = Math.min(damageValue, getMaxHealth() * 0.01F);
        }
        return super.hurt(source, damageValue);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS, 1, 1);
        if (source.getEntity() instanceof ServerPlayer player) {
            DialogueComponentBuilder builder = new DialogueComponentBuilder(this);
            switch (DOTEArchiveManager.getWorldLevel()) {
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
            if (DOTEArchiveManager.BIOME_PROGRESS_DATA.isSenbaiFought()) {
                DOTEAdvancementData.getAdvancement("golden_flame", player);
            }
            if (!DOTEArchiveManager.BIOME_PROGRESS_DATA.isBoss2fought()) {
                ItemUtil.addItem(player, DOTEItems.ADVENTURESPAR.get(), 25 * (DOTEArchiveManager.getWorldLevel() + 1));
            }
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.GOLDEN_FLAME_BGM.get();
    }

}
