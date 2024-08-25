package com.gaboj1.tcr.entity.client.tree_monster;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.SmallTreeMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallTreeMonsterRenderer extends GeoEntityRenderer<SmallTreeMonsterEntity> {
    public SmallTreeMonsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SmallTreeMonsterModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SmallTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/small_tree_monster.png");
    }

    @Override
    public void render(SmallTreeMonsterEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2f, 2f, 2f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
