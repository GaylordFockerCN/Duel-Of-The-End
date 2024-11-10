package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.entity.TCREntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class MagicProjectile extends LlamaSpit {
    private float damage = 0;
    public MagicProjectile(EntityType<? extends MagicProjectile> p_37248_, Level level) {
        super(p_37248_, level);
        level.playSound(null , getX(), getY(), getZ(), TCRSounds.YGGDRASIL_SHOOT_BALL.get(), SoundSource.BLOCKS,1,1);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        if(damage == 0){
            super.onHitEntity(pResult);
        } else {
            Entity entity = this.getOwner();
            if (entity instanceof LivingEntity) {
                pResult.getEntity().hurt(this.damageSources().indirectMagic(this, getOwner()), damage);
            }
        }
    }

    public MagicProjectile(Level level, LivingEntity owner) {
        this(TCREntities.MAGIC_PROJECTILE.get(), level);
        this.setOwner(owner);
        setPos(owner.getEyePosition().add(owner.getViewVector(1.0F).normalize().scale(3)));//从眼前发射
    }

    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getXa();
        double d1 = packet.getYa();
        double d2 = packet.getZa();

        for(int i = 0; i < 7; ++i) {
            double d3 = 0.4 + 0.1 * (double)i;
            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), d0 * d3, d1, d2 * d3);
        }

        this.setDeltaMovement(d0, d1, d2);
    }

}
