package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.YggdrasilSpawnerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class YggdrasilBlockRenderer extends GeoBlockRenderer<YggdrasilSpawnerBlockEntity> {
    public YggdrasilBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new YggdrasilBlockModel());
    }

    //没法继承render
    @Override
    public void defaultRender(PoseStack poseStack, YggdrasilSpawnerBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.translate(-2, 0, -2);
        poseStack.scale(5.0f,5.0f,5.0f);
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
    }
}
