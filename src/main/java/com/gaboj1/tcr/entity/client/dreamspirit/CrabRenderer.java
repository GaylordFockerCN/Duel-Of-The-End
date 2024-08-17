package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.entity.custom.dreamspirit.CrabCrabYou;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrabRenderer extends GeoEntityRenderer<CrabCrabYou> {
    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel());
    }

}
