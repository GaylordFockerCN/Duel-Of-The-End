package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.entity.custom.StarChaser;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StarChaserRenderer extends HumanoidMobRenderer<StarChaser, HumanoidModel<StarChaser>> {
    public StarChaserRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull StarChaser entity) {
        return new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/star_chaser" + entity.getSkinId() + ".png");
    }
}
