package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.weapon.GunCommon;
import com.gaboj1.tcr.item.custom.weapon.GunPlus;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

import net.minecraft.resources.ResourceLocation;

public class GunItemModel extends DefaultedItemGeoModel<GunCommon> {

    public GunItemModel(){
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "gun"));
    }

    @Override
    public ResourceLocation getTextureResource(GunCommon gun) {
        if(gun instanceof GunPlus){
            return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "item/gun_2.png");
        }
        return super.getTextureResource(gun);
    }
}
