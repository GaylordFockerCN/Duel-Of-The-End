package com.gaboj1.tcr.entity.client.biome3;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome3.SuaLiong;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SuaLiongRenderer extends GeoEntityRenderer<SuaLiong> {
    public SuaLiongRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "sualiong")));
    }
}
