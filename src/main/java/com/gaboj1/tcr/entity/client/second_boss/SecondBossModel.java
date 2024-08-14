package com.gaboj1.tcr.entity.client.second_boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SecondBossModel extends GeoModel<SecondBossEntity> {
    @Override
    public ResourceLocation getModelResource(SecondBossEntity geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/second_boss.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SecondBossEntity geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/second_boss.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SecondBossEntity geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/second_boss.animation.json");
    }
}
