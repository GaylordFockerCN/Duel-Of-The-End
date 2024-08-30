package com.gaboj1.tcr.entity.client.biome2;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.SpriteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpriteRenderer extends GeoEntityRenderer<SpriteEntity> {
    public SpriteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "jingling")));
    }
    @Override
    public void render(@NotNull SpriteEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(3f, 3f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
