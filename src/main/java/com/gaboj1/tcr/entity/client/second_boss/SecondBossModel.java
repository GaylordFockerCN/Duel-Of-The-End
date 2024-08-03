package com.gaboj1.tcr.entity.client.second_boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SecondBossModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/second_boss.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/second_boss.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/second_boss.animation.json");
    }
}
