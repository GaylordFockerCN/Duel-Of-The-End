package com.gaboj1.tcr.entity.client.sword_controller;

import com.gaboj1.tcr.entity.custom.biome2.SwordControllerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SwordControllerRenderer extends GeoEntityRenderer<SwordControllerEntity>{
    public SwordControllerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SwordControllerModel());
    }

    public void render(@NotNull SwordControllerEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
