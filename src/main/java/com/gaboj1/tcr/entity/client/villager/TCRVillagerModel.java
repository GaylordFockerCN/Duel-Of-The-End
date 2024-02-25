package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TCRVillagerModel extends GeoModel<TCRVillager> {
    @Override
    public ResourceLocation getModelResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/"+animatable.getResourceName()+".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/"+animatable.getResourceName()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, animatable.getResourceName()+".animation.json");
    }
}
