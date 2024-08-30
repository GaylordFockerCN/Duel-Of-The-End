package com.gaboj1.tcr.entity.client.boss.yggrasil;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.TreeClawEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TreeClawRenderer extends GeoEntityRenderer<TreeClawEntity> {
    public TreeClawRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_claw")));
    }

    @Override
    public void render(@NotNull TreeClawEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(4f, 4f, 4f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
