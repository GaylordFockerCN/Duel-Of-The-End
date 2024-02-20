package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.DesertEagleItem;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

public class DesertEagleItemModel extends GeoModel<DesertEagleItem> {

    private String textureResourceLocation = "";
    public DesertEagleItemModel(String textureResourceLocation){
        this.textureResourceLocation = textureResourceLocation;
    }

    @Override
    public ResourceLocation getAnimationResource(DesertEagleItem animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/deserteagle.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(DesertEagleItem animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/deserteagle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DesertEagleItem animatable) {
        //System.out.println("location"+textureResourceLocation);
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, textureResourceLocation);
    }
}
