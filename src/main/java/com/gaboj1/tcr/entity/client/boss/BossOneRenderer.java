package com.gaboj1.tcr.entity.client.boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.client.tree_monster.MiddleTreeMonsterModel;
import com.gaboj1.tcr.entity.custom.boss_one.BossOneEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.MiddleTreeMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BossOneRenderer extends GeoEntityRenderer<BossOneEntity> {
    public BossOneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BossOneModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BossOneEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/boss_one.png");
    }

    @Override
    public void render(BossOneEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2f, 2f, 2f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
