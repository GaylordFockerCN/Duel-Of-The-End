package com.gaboj1.tcr.entity.client.boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.Yggdrasil.YggdrasilEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class YggdrasilModel extends GeoModel<YggdrasilEntity> {


    @Override
    public ResourceLocation getModelResource(YggdrasilEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/yggdrasil.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YggdrasilEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/yggdrasil.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YggdrasilEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/yggdrasil.animation.json");
    }
}
