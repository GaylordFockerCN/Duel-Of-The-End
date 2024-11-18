package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.entity.custom.GoldenFlame;
import com.gaboj1.tcr.entity.custom.SenbaiDevil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GoldenFlameRenderer extends HumanoidMobRenderer<GoldenFlame, HumanoidModel<GoldenFlame>> {
    public GoldenFlameRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GoldenFlame entity) {
        return new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/kindom.png");
    }
}
