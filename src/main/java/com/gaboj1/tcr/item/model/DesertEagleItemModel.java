package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.DesertEagleItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

import net.minecraft.resources.ResourceLocation;

public class DesertEagleItemModel extends DefaultedItemGeoModel<DesertEagleItem> {

    public DesertEagleItemModel(){
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "deserteagle"));
    }

}
