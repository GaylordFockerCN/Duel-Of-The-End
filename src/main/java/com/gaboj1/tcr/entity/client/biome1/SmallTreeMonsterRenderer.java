package com.gaboj1.tcr.entity.client.biome1;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.SmallTreeMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallTreeMonsterRenderer extends GeoEntityRenderer<SmallTreeMonsterEntity> {
    public SmallTreeMonsterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"small_tree_monster"), true));
    }

    @Override
    public void render(@NotNull SmallTreeMonsterEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2f, 2f, 2f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
