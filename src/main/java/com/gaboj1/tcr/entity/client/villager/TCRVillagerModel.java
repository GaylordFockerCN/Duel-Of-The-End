package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

import java.util.Random;

public class TCRVillagerModel extends DefaultedEntityGeoModel<TCRVillager> {

    // We use the alternate super-constructor here to tell the model it should handle head-turning for us
    //从GeckoLib的BatModel学的
    public TCRVillagerModel(){
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager"), true);
    }

//    @Override
//    public ResourceLocation getModelResource(TCRVillager animatable) {
////        System.out.println("model res"+animatable.getResourceName());
//        //应该是villager.geo.json的不过无所谓了
//        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/pastoral_plain_villager.geo.json");
////        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/"+animatable.getResourceName()+".geo.json");
//    }

    @Override
    public ResourceLocation getTextureResource(TCRVillager animatable) {
//        System.out.println(animatable.getResourceName());
//        System.out.println("tex res"+animatable.getResourceName());
//        int i = new Random().nextInt(2);
//        System.out.println(i);
//        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/villager/pastoral_plain_villager"+i+".png");
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/villager/"+animatable.getResourceName()+".png");
    }

//    @Override
//    public ResourceLocation getAnimationResource(TCRVillager animatable) {
////        System.out.println("ani res"+animatable.getResourceName());
//        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/pastoral_plain_villager.animation.json");
////        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/"+animatable.getResourceName()+".animation.json");
//    }
}
