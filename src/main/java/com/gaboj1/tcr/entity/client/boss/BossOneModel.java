package com.gaboj1.tcr.entity.client.boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss_one.BossOneEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class BossOneModel extends GeoModel<BossOneEntity> {


    @Override
    public ResourceLocation getModelResource(BossOneEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/first_boss.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BossOneEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/boss_one.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BossOneEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/first_boss.animation.json");
    }
}
