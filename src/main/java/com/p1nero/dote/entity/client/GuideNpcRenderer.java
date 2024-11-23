package com.p1nero.dote.entity.client;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.custom.npc.GuideNpc;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GuideNpcRenderer extends HumanoidMobRenderer<GuideNpc, HumanoidModel<GuideNpc>> {
    public GuideNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GuideNpc entity) {
        return new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/ms_abyss.png");
    }
}
