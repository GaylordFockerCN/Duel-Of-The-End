package com.p1nero.dote.entity.custom.boss.ms_abyss;

import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MsAbyssEntity extends DOTEBoss {

    public MsAbyssEntity(EntityType<? extends DOTEBoss> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes().build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.registerCommonGoals();
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return null;
    }

}
