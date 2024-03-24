package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PortalBlockRenderer extends GeoBlockRenderer<PortalBlockEntity> {
    public PortalBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new PortalBlockModel());
    }
}
