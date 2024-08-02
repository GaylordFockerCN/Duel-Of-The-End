package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * 不受彼此伤害
 */
public class Master extends TCRVillager {
    public Master(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 142857);
    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightningBolt) {}

    /**
     * 免疫除了来自玩家和第二boss以外的伤害
     */
    @Override
    public boolean hurt(DamageSource source, float v) {
        if(source.getEntity() instanceof Player || source.getEntity() instanceof YggdrasilEntity){
            return super.hurt(source, v);
        }
        return false;
    }

    @Override
    public boolean isAngry() {
        //TODO 改为用SaveUtil判断
        return true;
    }
}
