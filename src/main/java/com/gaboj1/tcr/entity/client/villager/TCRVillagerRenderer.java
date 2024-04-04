package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TCRVillagerRenderer extends GeoEntityRenderer<TCRVillager> {

    public TCRVillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TCRVillagerModel());
    }

}