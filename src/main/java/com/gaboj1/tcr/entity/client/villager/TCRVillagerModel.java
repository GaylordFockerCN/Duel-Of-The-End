package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TCRVillagerModel extends GeoModel<TCRVillager> {
    @Override
    public ResourceLocation getModelResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/pastoral_plain_villager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/pastoral_plain_villager.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/pastoral_plain_villager.animation.json");
    }
}
