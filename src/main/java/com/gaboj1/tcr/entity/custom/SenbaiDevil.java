package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.capability.DOTECapabilityProvider;
import com.gaboj1.tcr.client.DOTESounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.item.EpicFightItems;

public class SenbaiDevil extends DOTEBoss {

    public SenbaiDevil(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, EpicFightItems.UCHIGATANA.get().getDefaultInstance());
    }

    @Override
    public float getAttackSpeed() {
        return 0.9F;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 274.0f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(EpicFightAttributes.IMPACT.get(), 1.1f)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 3)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 80)
                .add(EpicFightAttributes.WEIGHT.get(), 3)
                .build();
    }

    @Override
    public int getMaxNeutralizeCount() {
        return 10;
    }

    /**
     * 播放音效，解锁玩家进入维度的权限
     */
    @Override
    public void die(@NotNull DamageSource source) {
        level().playSound(null , getX(), getY(), getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS,1,1);
        if(source.getEntity() instanceof Player player){
            player.getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(dotePlayer -> dotePlayer.setCanEnterPBiome(true));
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.SENBAI_BGM.get();
    }

}
