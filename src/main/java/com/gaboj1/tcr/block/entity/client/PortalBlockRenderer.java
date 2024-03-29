package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PortalBlockRenderer extends GeoBlockRenderer<PortalBlockEntity> {
    public PortalBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new PortalBlockModel());
    }

    //没法继承render
    @Override
    public void defaultRender(PoseStack poseStack, PortalBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.scale(2.0f,2.0f,2.0f);
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
    }
}
