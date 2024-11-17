package com.gaboj1.tcr.entity.custom;

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

public class DOTEPiglin extends DOTEMonster {
    public static final List<Item> WEAPONS = new ArrayList<>();
    public DOTEPiglin(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        WEAPONS.add(Items.GOLDEN_AXE);
        WEAPONS.add(EpicFightItems.IRON_GREATSWORD.get());
        WEAPONS.add(EpicFightItems.IRON_LONGSWORD.get());
        WEAPONS.add(EpicFightItems.IRON_SPEAR.get());
        WEAPONS.add(EpicFightItems.IRON_TACHI.get());
        setItemInHand(InteractionHand.MAIN_HAND, WEAPONS.get(getRandom().nextInt(WEAPONS.size())).getDefaultInstance());
    }

    @Override
    public int getMaxNeutralizeCount() {
        return 4;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIGLIN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_21239_) {
        return SoundEvents.PIGLIN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIGLIN_AMBIENT;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0f)
                .add(Attributes.ATTACK_DAMAGE, 20.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 3)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 80)
                .add(EpicFightAttributes.WEIGHT.get(), 3)
                .build();
    }

}
