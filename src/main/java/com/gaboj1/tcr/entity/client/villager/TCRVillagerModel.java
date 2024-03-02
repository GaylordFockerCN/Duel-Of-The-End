package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

//TODO 换成animatable.getResourceName()
public class TCRVillagerModel extends GeoModel<TCRVillager> {
    @Override
    public ResourceLocation getModelResource(TCRVillager animatable) {
        System.out.println("model res"+animatable.getResourceName());
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/pastoral_plain_villager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TCRVillager animatable) {
//        System.out.println(animatable.getResourceName());
        System.out.println("tex res"+animatable.getResourceName());
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/pastoral_plain_villager.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TCRVillager animatable) {
        System.out.println("ani res"+animatable.getResourceName());
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/pastoral_plain_villager.animation.json");
    }
}
