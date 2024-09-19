package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

/**
 * 冰刺实体，由冰虎射出
 */
@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class IceThornEntity extends AbstractArrow implements ItemSupplier {
    public IceThornEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(TCREntities.ICE_THORN_ENTITY.get(), world);
    }

    public IceThornEntity(EntityType<? extends IceThornEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return new ItemStack(TCRItems.ICE_THORN.get());
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(TCRItems.ICE_THORN.get());
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity entity) {
        super.doPostHurtEffects(entity);
        MobEffectInstance mobEffectInstance = new MobEffectInstance(TCREffects.FROZEN.get(), this.tickCount, 0);
        entity.addEffect(mobEffectInstance, this.getEffectSource());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 200) {
            this.discard();
        }
    }
}