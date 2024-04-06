package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.entity.custom.dreamspirit.JellyCat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JellyCatRenderer extends GeoEntityRenderer<JellyCat> {
    public JellyCatRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JellyCatModel());
    }

    @Override
    public void render(JellyCat entity, float entityYaw, float partialTick, PoseStack poseStack,MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
