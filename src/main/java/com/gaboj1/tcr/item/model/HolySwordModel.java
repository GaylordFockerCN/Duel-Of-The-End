package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.boss_loot.HolySword;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HolySwordModel extends GeoModel<HolySword> {
    @Override
    public ResourceLocation getModelResource(HolySword animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/item/sword.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(HolySword animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/item/sword.animation.json");
    }

    @Override
    public ResourceLocation getTextureResource(HolySword animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/item/tree_spirit_wand.png");
    }
}
