package com.gaboj1.tcr.entity.client.tree_monster;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.client.tree_monster.MiddleTreeMonsterModel;
import com.gaboj1.tcr.entity.custom.tree_monsters.MiddleTreeMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MiddleTreeMonsterRenderer extends GeoEntityRenderer<MiddleTreeMonsterEntity> {
    public MiddleTreeMonsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MiddleTreeMonsterModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MiddleTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/middle_tree_monster.png");
    }

    @Override
    public void render(MiddleTreeMonsterEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
            poseStack.scale(1f, 1f, 1f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
