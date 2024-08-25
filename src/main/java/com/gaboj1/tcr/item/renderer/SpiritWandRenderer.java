package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.SpiritWand;
import com.gaboj1.tcr.item.model.SpiritWandModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SpiritWandRenderer extends GeoItemRenderer<SpiritWand> {

    public SpiritWandRenderer() {
        super(new SpiritWandModel());
//        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
