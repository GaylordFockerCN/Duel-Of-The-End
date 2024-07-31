package com.gaboj1.tcr.entity.client.big_hammer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class BigHammerModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/big_hammer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/big_hammer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/big_hammer.animation.json");
    }
}
