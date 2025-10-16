package com.p1nero.dote.entity.custom.boss.goldenflame;

import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.entity.api.IWanderableEntity;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GoldenFlame extends DOTEBoss implements IWanderableEntity {

    public GoldenFlame(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemSlot(EquipmentSlot.HEAD, DOTEItems.GOLDEN_DRAGON_HELMET.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.CHEST, DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.LEGS, DOTEItems.GOLDEN_DRAGON_LEGGINGS.get().getDefaultInstance());
        setItemSlot(EquipmentSlot.FEET, DOTEItems.GOLDEN_DRAGON_BOOTS.get().getDefaultInstance());
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1500.0f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(Attributes.ARMOR, 10.0f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.GOLDEN_FLAME_BGM.get();
    }

}
