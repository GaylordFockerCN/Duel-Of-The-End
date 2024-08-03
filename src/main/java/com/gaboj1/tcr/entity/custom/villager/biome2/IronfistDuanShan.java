package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/**
 * 断山，皮糙肉厚移速慢攻击距离近
 */
public class IronfistDuanShan extends Master {
    public IronfistDuanShan(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 15.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 0.5f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.35f)//移速
                .build();
    }

    @Override
    public String getResourceName() {
        return "duan_shan";
    }

}
