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
import yesman.epicfight.world.item.EpicFightItems;

/**
 * 炼狱炎魔
 */
public class ThePyroclasOfPurgatory extends DOTEBoss {

    public ThePyroclasOfPurgatory(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, EpicFightItems.UCHIGATANA.get().getDefaultInstance());
    }

    @Override
    public boolean shouldRenderBossBar() {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 274.0f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .build();
    }

    @Override
    public int getMaxNeutralizeCount() {
        return 12;
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
        return DOTESounds.SENBAI_BGM.get();
    }

}
