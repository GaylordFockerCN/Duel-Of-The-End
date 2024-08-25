package com.gaboj1.tcr.entity.client.boxer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome2.BoxerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BoxerModel extends DefaultedEntityGeoModel<BoxerEntity> {
    public BoxerModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "boxer"));
    }

}
