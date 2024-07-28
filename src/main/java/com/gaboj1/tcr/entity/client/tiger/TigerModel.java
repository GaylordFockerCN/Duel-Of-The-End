package com.gaboj1.tcr.entity.client.tiger;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.tiger.TigerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TigerModel extends GeoModel<TigerEntity> {
    @Override
    public ResourceLocation getModelResource(TigerEntity tigerEntity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/tiger.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TigerEntity tigerEntity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tiger.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TigerEntity tigerEntity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/tiger.animation.json");
    }
}
