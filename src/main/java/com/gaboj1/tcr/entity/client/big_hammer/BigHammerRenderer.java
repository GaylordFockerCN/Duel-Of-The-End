package com.gaboj1.tcr.entity.client.big_hammer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.client.boxer.BoxerModel;
import com.gaboj1.tcr.entity.custom.big_hammer.BigHammerEntity;
import com.gaboj1.tcr.entity.custom.boxer.BoxerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BigHammerRenderer extends GeoEntityRenderer<BigHammerEntity> {
    public BigHammerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BigHammerModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BigHammerEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/big_hammer.png");

    }

    public void render(@NotNull BigHammerEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
