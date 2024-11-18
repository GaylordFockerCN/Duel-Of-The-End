package com.p1nero.dote.entity.client;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.custom.DOTEZombie;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DOTEZombieRenderer extends HumanoidMobRenderer<DOTEZombie, HumanoidModel<DOTEZombie>> {
    public DOTEZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DOTEZombie entity) {
        int skinId = entity.getSkinId();
        return switch (skinId){
            case 0 -> new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/dark_advance.png");
            case 1 -> new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/ashes.png");
            case 2 -> new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/slaughter_general.png");
            default -> new ResourceLocation("textures/entity/zombie/zombie.png");
        };
    }
}
