package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.MiaoYin;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class TCRVillagerModel extends DefaultedEntityGeoModel<TCRVillager> {

    // We use the alternate super-constructor here to tell the model it should handle head-turning for us
    //从GeckoLib的BatModel学的
    public TCRVillagerModel(){
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager"), true);
    }

    @Override
    public ResourceLocation getModelResource(TCRVillager animatable) {
        if(animatable instanceof MiaoYin){
            return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/entity/yueshi.geo.json");
        }
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/entity/pastoral_plain_villager"+(animatable.isFemale()?"_slim":"")+".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TCRVillager animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/villager/"+animatable.getResourceName()+".png");
    }

}
