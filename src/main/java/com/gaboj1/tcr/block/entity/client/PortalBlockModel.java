package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class PortalBlockModel extends DefaultedBlockGeoModel<PortalBlockEntity> {
    public PortalBlockModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "portal_block"));
    }

}