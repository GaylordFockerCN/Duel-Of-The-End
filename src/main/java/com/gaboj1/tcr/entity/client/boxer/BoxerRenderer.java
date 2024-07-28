package com.gaboj1.tcr.entity.client.boxer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.client.sprite.SpriteModel;
import com.gaboj1.tcr.entity.custom.boxer.BoxerEntity;
import com.gaboj1.tcr.entity.custom.sprite.SpriteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoxerRenderer extends GeoEntityRenderer<BoxerEntity> {
    public BoxerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BoxerModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BoxerEntity animatable) {
            return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/boxer.png");
        }

    public void render(@NotNull  BoxerEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
