package com.gaboj1.tcr.entity.client.boss.yggrasil;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class YggdrasilRenderer extends GeoEntityRenderer<YggdrasilEntity> {
    public YggdrasilRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new YggdrasilModel());
    }

    @Override
    public void render(@NotNull YggdrasilEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        if(!entity.isFighting()){
            poseStack.scale(0.7F, 0.7F, 0.7F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
