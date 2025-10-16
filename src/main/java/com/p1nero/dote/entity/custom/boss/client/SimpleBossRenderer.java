package com.p1nero.dote.entity.custom.boss.client;

import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SimpleBossRenderer extends HumanoidMobRenderer<DOTEBoss, HumanoidModel<DOTEBoss>> {
    private final ResourceLocation texture;

    public SimpleBossRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.texture = texture;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DOTEBoss boss) {
        return texture;
    }
}
