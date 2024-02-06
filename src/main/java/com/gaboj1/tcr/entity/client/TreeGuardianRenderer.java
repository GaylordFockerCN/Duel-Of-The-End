package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.TreeGuardianEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TreeGuardianRenderer extends GeoEntityRenderer<TreeGuardianEntity> {
    public TreeGuardianRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TreeGuardianModel());
    }

    @Override
    public ResourceLocation getTextureLocation(TreeGuardianEntity animatable) {
        //贴图
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tree_guardian.png");
    }

    @Override
    public void render(TreeGuardianEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
//        if(entity.isBaby()) {
        poseStack.scale(3.5f, 3.5f, 3.5f);//设置缩放大小
//        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
