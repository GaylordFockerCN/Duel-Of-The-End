package com.p1nero.dote.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class SenbaiPatchedRenderer extends PHumanoidRenderer {
    public SenbaiPatchedRenderer(MeshProvider mesh, EntityRendererProvider.Context context, EntityType entityType) {
        super(mesh, context, entityType);
    }

    @Override
    public void render(LivingEntity entity, LivingEntityPatch entitypatch, LivingEntityRenderer renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        poseStack.scale(1.1F, 1.1F, 1.1F);
        poseStack.translate(0, 0.1F, 0);
        super.render(entity, entitypatch, renderer, buffer, poseStack, packedLight, partialTicks);
    }
}
