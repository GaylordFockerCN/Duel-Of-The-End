package com.gaboj1.tcr.entity.client.boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.Yggdrasil.tree_clawEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class tree_clawModel extends GeoModel<tree_clawEntity>{
    @Override
    public ResourceLocation getModelResource(tree_clawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/tree_claw.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(tree_clawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tree_claw.png");
    }

    @Override
    public ResourceLocation getAnimationResource(tree_clawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/tree_claw.animation.json");
    }
}
