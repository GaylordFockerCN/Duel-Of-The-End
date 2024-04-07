package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.dreamspirit.JellyCat;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JellyCatModel extends GeoModel<JellyCat> {
    @Override
    public ResourceLocation getModelResource(JellyCat animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/jelly_cat"+(animatable.getSkinID()<0?"_ball_hand":"")+".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JellyCat animatable) {
        String cat = "jelly_cat_"+Math.abs(animatable.getSkinID())+"_"+animatable.getFaceID().ordinal()+".png";
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/"+cat);
    }

    @Override
    public ResourceLocation getAnimationResource(JellyCat animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/jelly_cat"+(animatable.getSkinID()<0?"_ball_hand":"")+".animation.json");
    }
}
