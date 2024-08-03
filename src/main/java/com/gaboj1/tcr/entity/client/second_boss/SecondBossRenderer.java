package com.gaboj1.tcr.entity.client.second_boss;

import com.gaboj1.tcr.entity.client.sword_controller.SwordControllerModel;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.entity.custom.sword_controller.SwordControllerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SecondBossRenderer extends GeoEntityRenderer<SecondBossEntity> {
    public SecondBossRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SecondBossModel());
    }

    public void render(@NotNull SecondBossEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
