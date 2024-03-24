package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PortalBlockModel extends GeoModel<PortalBlockEntity> {
    @Override
    public ResourceLocation getModelResource(PortalBlockEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/portal_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PortalBlockEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/block/portal_block.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PortalBlockEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/portal_block.animation.json");
    }
}