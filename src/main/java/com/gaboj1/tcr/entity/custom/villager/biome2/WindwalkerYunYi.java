package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class WindwalkerYunYi extends Master{
    public WindwalkerYunYi(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 2.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 10.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.7f)//移速
                .build();
    }

    @Override
    protected <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("walk2", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public String getResourceName() {
        return "yun_yi";
    }

}
