package com.gaboj1.tcr.entity.client.biome1;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.MiddleTreeMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MiddleTreeMonsterRenderer extends GeoEntityRenderer<MiddleTreeMonsterEntity> {
    public MiddleTreeMonsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "middle_tree_monster")));
    }
}
