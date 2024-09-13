package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.spawner.TigerTrialSpawnerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TigerSpawnerRenderer extends GeoBlockRenderer<TigerTrialSpawnerBlockEntity> {
    public TigerSpawnerRenderer() {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tiger_trial_spawner_block_entity")));
    }

    @Override
    public void defaultRender(PoseStack poseStack, TigerTrialSpawnerBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.translate(-2, 0, -2);
        poseStack.scale(5.0f,5.0f,5.0f);
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
    }

}
