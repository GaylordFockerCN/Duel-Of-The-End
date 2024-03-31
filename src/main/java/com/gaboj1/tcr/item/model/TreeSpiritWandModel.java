package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TreeSpiritWandModel extends GeoModel<TreeSpiritWand> {

    @Override
    public ResourceLocation getAnimationResource(TreeSpiritWand animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/tree_spirit_wand.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TreeSpiritWand animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/tree_spirit_wand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TreeSpiritWand animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/item/tree_spirit_wand.png");
    }
}
