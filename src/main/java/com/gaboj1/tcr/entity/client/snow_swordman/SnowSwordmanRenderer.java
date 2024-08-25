package com.gaboj1.tcr.entity.client.snow_swordman;
import com.gaboj1.tcr.entity.custom.biome2.SnowSwordmanEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class SnowSwordmanRenderer extends GeoEntityRenderer<SnowSwordmanEntity> {
    public SnowSwordmanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SnowSwordmanModel());
    }

    public void render(@NotNull SnowSwordmanEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
