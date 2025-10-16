package com.p1nero.dote.entity.custom.boss.reaper.client;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.custom.boss.reaper.ReaperEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ReaperRenderer extends HumanoidMobRenderer<ReaperEntity, HumanoidModel<ReaperEntity>> {
    public ReaperRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ReaperEntity entity) {
        return new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/reaper.png");
    }
}
