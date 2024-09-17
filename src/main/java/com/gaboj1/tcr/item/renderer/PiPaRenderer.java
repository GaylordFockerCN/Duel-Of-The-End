package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.weapon.PiPa;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class PiPaRenderer extends GeoItemRenderer<PiPa> {
    public PiPaRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pi_pa")));
//        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}