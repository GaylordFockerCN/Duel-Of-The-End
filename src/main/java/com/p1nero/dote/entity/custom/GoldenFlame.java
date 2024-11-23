package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.item.DOTEItems;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class GoldenFlame extends DOTEBoss {
    protected static final EntityDataAccessor<Integer> CHARGING_TIMER = SynchedEntityData.defineId(GoldenFlame.class, EntityDataSerializers.INT);
    public GoldenFlame(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, DOTEItems.BALMUNG.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.HEAD, DOTEItems.GOLDEN_DRAGON_HELMET.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.CHEST, DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.LEGS, DOTEItems.GOLDEN_DRAGON_LEGGINGS.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.FEET, DOTEItems.GOLDEN_DRAGON_BOOTS.get().getDefaultInstance());
    }

    @Override
    public float getHomeRadius() {
        return 39;
    }

    @Override
    public float getAttackSpeed() {
        return 0.35F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(CHARGING_TIMER, 0);
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

    @Override
    public void tick() {
        super.tick();
        if(getChargingTimer() > 0){
            getEntityData().set(CHARGING_TIMER, Math.max(0, getChargingTimer() - 1));
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
//                .add(Attributes.MAX_HEALTH, 10.0f)//测试用
                .add(Attributes.MAX_HEALTH, 752.0f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
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
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
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
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.GOLDEN_FLAME_BGM.get();
    }

}
