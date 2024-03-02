package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TCRVillagerRenderer extends GeoEntityRenderer<TCRVillager> {

    public TCRVillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TCRVillagerModel());
    }

    @Override
    public ResourceLocation getTextureLocation(TCRVillager animatable) {
        System.out.println("tex res loc"+animatable.getResourceName());
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/pastoral_plain_villager.png");
    }

}