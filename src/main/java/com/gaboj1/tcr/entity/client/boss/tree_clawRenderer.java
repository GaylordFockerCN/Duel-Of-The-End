package com.gaboj1.tcr.entity.client.boss;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.Yggdrasil.tree_clawEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class tree_clawRenderer extends GeoEntityRenderer<tree_clawEntity> {
    public tree_clawRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new tree_clawModel());
    }

    @Override
    public ResourceLocation getTextureLocation(tree_clawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tree_claw.png");
    }

    @Override
    public void render(tree_clawEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2f, 2f, 2f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
