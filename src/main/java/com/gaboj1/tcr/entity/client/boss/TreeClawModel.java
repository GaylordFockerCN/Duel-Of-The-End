package com.gaboj1.tcr.entity.client.boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.TreeClawEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TreeClawModel extends GeoModel<TreeClawEntity>{
    @Override
    public ResourceLocation getModelResource(TreeClawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/tree_claw.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TreeClawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tree_claw.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TreeClawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/tree_claw.animation.json");
    }
}
