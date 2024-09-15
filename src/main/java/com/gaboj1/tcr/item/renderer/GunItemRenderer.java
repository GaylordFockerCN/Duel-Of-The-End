package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.weapon.GunCommon;
import com.gaboj1.tcr.item.model.GunItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GunItemRenderer extends GeoItemRenderer<GunCommon> {
    public GunItemRenderer() {
        super(new GunItemModel());
    }

}
