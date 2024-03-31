package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import com.gaboj1.tcr.item.model.TreeSpiritWandModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class TreeSpiritWandRenderer extends GeoItemRenderer<TreeSpiritWand> {

    public TreeSpiritWandRenderer() {
        super(new TreeSpiritWandModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    //官方给的解法。。等更新新版后就不需要了
    @Override
    public void preRender(PoseStack poseStack, TreeSpiritWand animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.itemRenderTranslations = new Matrix4f(poseStack.last().pose());;
        scaleModelForRender(this.scaleWidth, this.scaleHeight, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
        if(!isReRender){
            poseStack.translate(0.5f, 0.51f, 0.5f);
        }
    }
}
