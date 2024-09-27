package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.dreamspirit.WindFeatherFalconEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WindFeatherFalconRenderer extends GeoEntityRenderer<WindFeatherFalconEntity> {
    public WindFeatherFalconRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "wind_feather_falcon")));
    }
}
