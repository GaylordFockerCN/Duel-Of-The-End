package com.p1nero.dote.entity.custom.boss.senbai;

import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SenbaiDevil extends DOTEBoss {

    public SenbaiDevil(EntityType<? extends DOTEBoss> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 573.03f)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.ARMOR, 15.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.registerCommonGoals();
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.SENBAI_BGM.get();
    }

}
