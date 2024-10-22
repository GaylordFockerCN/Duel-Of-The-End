package com.gaboj1.tcr.entity.client.biome3;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome3.DesertFigure;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DesertFigureRenderer extends GeoEntityRenderer<DesertFigure> {
    public DesertFigureRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "desert_figure")));
    }
}
