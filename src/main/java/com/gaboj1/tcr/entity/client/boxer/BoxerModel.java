package com.gaboj1.tcr.entity.client.boxer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class BoxerModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/boxer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/boxer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/boxer.animation.json");
    }
}
