package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.ElderStaff;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ElderStaffRenderer extends GeoItemRenderer<ElderStaff> {

    public ElderStaffRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "elder_staff")));
    }
}
