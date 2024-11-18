package com.gaboj1.tcr.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.ArrayList;
import java.util.List;

public class DOTEZombie extends DOTEMonster {
    public static final List<Item> WEAPONS = new ArrayList<>();
    protected static final EntityDataAccessor<Integer> SKIN_ID = SynchedEntityData.defineId(DOTEZombie.class, EntityDataSerializers.INT);
    private static final int MAX_SKIN_ID = 4;
    public DOTEZombie(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        WEAPONS.add(Items.IRON_AXE);
        WEAPONS.add(Items.IRON_SWORD);
        WEAPONS.add(EpicFightItems.STONE_GREATSWORD.get());
        WEAPONS.add(EpicFightItems.STONE_SPEAR.get());
        setItemInHand(InteractionHand.MAIN_HAND, WEAPONS.get(getRandom().nextInt(WEAPONS.size())).getDefaultInstance());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(SKIN_ID, getRandom().nextInt(MAX_SKIN_ID));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(SKIN_ID,tag.getInt("skin_id"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("skin_id", this.getEntityData().get(SKIN_ID));
    }

    public int getSkinId() {
        return getEntityData().get(SKIN_ID);
    }

    @Override
    public int getMaxNeutralizeCount() {
        return 3 + random.nextInt(2);
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 3)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 80)
                .add(EpicFightAttributes.WEIGHT.get(), 3)
                .build();
    }

}
