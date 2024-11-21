package com.p1nero.dote.entity.custom;

import com.p1nero.dote.capability.DOTECapabilityProvider;
import com.p1nero.dote.client.DOTESounds;
import net.minecraft.sounds.SoundEvent;
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
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.world.item.EpicFightItems;

/**
 * 圣辉裁决者
 */
public class TheArbiterOfRadiance extends DOTEBoss {

    public TheArbiterOfRadiance(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, EpicFightItems.IRON_TACHI.get().getDefaultInstance());
    }

    @Override
    public boolean shouldRenderBossBar() {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 114.514f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .build();
    }

    @Override
    public float getAttackSpeed() {
        return 0.65F;
    }

    /**
     * 播放音效，解锁玩家进入维度的权限
     */
    @Override
    public void die(@NotNull DamageSource source) {
        if(source.getEntity() instanceof Player player){
            player.getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(dotePlayer -> dotePlayer.setCanEnterPBiome(true));
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return getHealth() <= getMaxHealth() / 2 ? DOTESounds.TARBITER2.get() : DOTESounds.TARBITER1.get();
    }

}
