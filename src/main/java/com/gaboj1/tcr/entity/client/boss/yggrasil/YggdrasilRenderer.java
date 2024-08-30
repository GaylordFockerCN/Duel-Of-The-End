package com.gaboj1.tcr.entity.client.boss.yggrasil;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class YggdrasilRenderer extends GeoEntityRenderer<YggdrasilEntity> {
    public YggdrasilRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new YggdrasilModel());
    }

}
