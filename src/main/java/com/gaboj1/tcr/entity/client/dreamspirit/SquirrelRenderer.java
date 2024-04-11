package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.entity.custom.dreamspirit.Squirrel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SquirrelRenderer extends GeoEntityRenderer<Squirrel> {
    public SquirrelRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SquirrelModel());
    }
}
