package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.entity.custom.DOTEPiglin;
import com.gaboj1.tcr.entity.custom.DOTEZombie;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DOTEPiglinRenderer extends HumanoidMobRenderer<DOTEPiglin, HumanoidModel<DOTEPiglin>> {
    public DOTEPiglinRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    /**
     * 原版猪灵贴图
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DOTEPiglin entity) {
        return new ResourceLocation("textures/entity/piglin/piglin.png");
    }
}
