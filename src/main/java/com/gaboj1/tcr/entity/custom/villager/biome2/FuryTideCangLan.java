package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/**
 * 苍澜：放水球
 */
public class FuryTideCangLan extends Master {
    public FuryTideCangLan(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 10.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速
                .build();
    }

    @Override
    public String getResourceName() {
        return "cang_lan";
    }

    @Override
    public void playAttackAnim() {
        triggerAnim("Summon","summon");
    }

}
