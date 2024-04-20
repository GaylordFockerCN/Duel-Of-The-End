package com.gaboj1.tcr.entity.client.tree_monster;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.client.tree_monster.TreeGuardianModel;
import com.gaboj1.tcr.entity.custom.tree_monsters.TreeGuardianEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TreeGuardianRenderer extends GeoEntityRenderer<TreeGuardianEntity> {
    public TreeGuardianRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TreeGuardianModel());
    }

    @Override
    public void render(TreeGuardianEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(1f, 1f, 1f);//设置缩放大小
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
