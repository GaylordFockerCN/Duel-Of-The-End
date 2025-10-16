package com.p1nero.dote.entity.custom.boss.sand_captain.client;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.custom.boss.sand_captain.SandCaptainCoffin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SandCaptainCoffinRenderer extends GeoEntityRenderer<SandCaptainCoffin> {
    public SandCaptainCoffinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "sand_captain_coffin")));
    }
}
