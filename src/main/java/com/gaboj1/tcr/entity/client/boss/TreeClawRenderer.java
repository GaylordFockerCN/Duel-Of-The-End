package com.gaboj1.tcr.entity.client.boss;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.TreeClawEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TreeClawRenderer extends GeoEntityRenderer<TreeClawEntity> {
    public TreeClawRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TreeClawModel());
    }

    @Override
    public ResourceLocation getTextureLocation(TreeClawEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tree_claw.png");
    }

    @Override
    public void render(TreeClawEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2f, 2f, 2f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
