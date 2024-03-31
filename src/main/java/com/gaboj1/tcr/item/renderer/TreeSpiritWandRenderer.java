package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import com.gaboj1.tcr.item.model.TreeSpiritWandModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class TreeSpiritWandRenderer extends GeoItemRenderer<TreeSpiritWand> {

    public TreeSpiritWandRenderer() {
        super(new TreeSpiritWandModel());
//        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
