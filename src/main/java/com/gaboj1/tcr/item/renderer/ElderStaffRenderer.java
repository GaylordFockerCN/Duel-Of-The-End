package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.ElderStaff;
import com.gaboj1.tcr.item.model.ElderStaffModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ElderStaffRenderer extends GeoItemRenderer<ElderStaff> {

    public ElderStaffRenderer() {
        super(new ElderStaffModel());
    }
}
