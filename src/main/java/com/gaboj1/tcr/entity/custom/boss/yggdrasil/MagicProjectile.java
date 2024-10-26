package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.entity.TCREntities;
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
    public MagicProjectile(EntityType<? extends MagicProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
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
        this.setPos(owner.getX() - (double)(owner.getBbWidth() + 1.0F) * 0.5 * (double) Mth.sin(owner.yBodyRot * 0.0175F), owner.getEyeY() - 0.5, owner.getZ() + (double)(owner.getBbWidth() + 1.0F) * 0.5 * (double)Mth.cos(owner.yBodyRot * 0.0175F));
    }

}
