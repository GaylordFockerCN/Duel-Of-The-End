package com.gaboj1.tcr.entity.client.boss.second_boss;

import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SecondBossRenderer extends GeoEntityRenderer<SecondBossEntity> {
    public SecondBossRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SecondBossModel());
    }

    @Override
    public void render(@NotNull SecondBossEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(0.5F, 0.5F, 0.5F);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
