package com.gaboj1.tcr.entity.client.snow_swordman;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SnowSwordmanModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/snow_swordman.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/snow_swordman.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/snow_swordman.animation.json");
    }
}
