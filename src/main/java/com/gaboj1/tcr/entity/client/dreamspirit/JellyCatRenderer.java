package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.entity.custom.dreamspirit.JellyCat;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JellyCatRenderer extends GeoEntityRenderer<JellyCat> {
    public JellyCatRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JellyCatModel());
    }

}
