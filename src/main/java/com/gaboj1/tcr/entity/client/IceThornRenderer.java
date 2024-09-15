package com.gaboj1.tcr.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class IceThornRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
    private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public IceThornRenderer(EntityRendererProvider.Context p_174416_, float p_174417_, boolean p_174418_) {
        super(p_174416_);
        this.itemRenderer = p_174416_.getItemRenderer();
        this.scale = p_174417_;
        this.fullBright = p_174418_;
    }

    public IceThornRenderer(EntityRendererProvider.Context p_174414_) {
        this(p_174414_, 1.0F, false);
    }

    protected int getBlockLightLevel(T p_116092_, BlockPos p_116093_) {
        return this.fullBright ? 15 : super.getBlockLightLevel(p_116092_, p_116093_);
    }

    public void render(T item, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_) {
        if (item.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(item) < 12.25)) {
            p_116088_.pushPose();
            p_116088_.scale(this.scale, this.scale, this.scale);
//            p_116088_.mulPose(this.entityRenderDispatcher.cameraOrientation());
//            p_116088_.mulPose(Axis.YP.rotationDegrees(180.0F));

            p_116088_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116087_, item.yRotO, item.getYRot()) - 90.0F));
            p_116088_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116087_, item.xRotO, item.getXRot())));
            this.itemRenderer.renderStatic(item.getItem(), ItemDisplayContext.GROUND, p_116090_, OverlayTexture.NO_OVERLAY, p_116088_, p_116089_, item.level(), item.getId());
            p_116088_.popPose();
            super.render(item, p_116086_, p_116087_, p_116088_, p_116089_, p_116090_);
        }
    }

    public ResourceLocation getTextureLocation(Entity p_116083_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}