package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.weapon.SpiritWand;
import com.gaboj1.tcr.item.model.SpiritWandModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SpiritWandRenderer extends GeoItemRenderer<SpiritWand> {

    public SpiritWandRenderer() {
        super(new SpiritWandModel());
//        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
