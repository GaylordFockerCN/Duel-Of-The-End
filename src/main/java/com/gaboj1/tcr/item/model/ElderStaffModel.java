package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.ElderStaff;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

public class ElderStaffModel extends GeoModel<ElderStaff> {

    @Override
    public ResourceLocation getAnimationResource(ElderStaff animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/elder_staff.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ElderStaff animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/elder_staff.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ElderStaff animatable) {
        //System.out.println("location"+textureResourceLocation);
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/item/elder_staff.png");
    }
}
