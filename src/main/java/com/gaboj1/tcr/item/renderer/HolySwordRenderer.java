package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.item.custom.boss_loot.FlySword;
import com.gaboj1.tcr.item.model.HolySwordModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class HolySwordRenderer extends GeoItemRenderer<FlySword> {
    public HolySwordRenderer() {
        super(new HolySwordModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, FlySword animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
//        poseStack.translate();//移动到合适位置
    }


}
