package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.DesertEagleItem;
import com.gaboj1.tcr.item.model.DesertEagleItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DesertEagleItemRenderer extends GeoItemRenderer<DesertEagleItem> {
    public DesertEagleItemRenderer() {
        super(new DesertEagleItemModel("textures/item/texturecrc.png"));
    }

}
