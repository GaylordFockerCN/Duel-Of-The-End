package com.gaboj1.tcr.entity.client.biome1;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.HorribleTreeMonsterEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HorribleTreeMonsterRenderer extends GeoEntityRenderer<HorribleTreeMonsterEntity> {
    public HorribleTreeMonsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "horrible_tree_monster")));
    }
}
