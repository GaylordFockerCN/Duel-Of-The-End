package com.gaboj1.tcr.entity.client.boss.second_boss;

import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SecondBossRenderer extends GeoEntityRenderer<SecondBossEntity> {
    public SecondBossRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SecondBossModel());
    }
}
