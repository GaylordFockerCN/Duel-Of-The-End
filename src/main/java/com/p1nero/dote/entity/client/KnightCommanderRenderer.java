package com.p1nero.dote.entity.client;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.custom.npc.KnightCommander;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class KnightCommanderRenderer extends HumanoidMobRenderer<KnightCommander, HumanoidModel<KnightCommander>> {
    public KnightCommanderRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull KnightCommander entity) {
        return new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/sage.png");
    }
}
