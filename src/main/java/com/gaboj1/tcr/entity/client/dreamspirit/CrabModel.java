package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.dreamspirit.CrabCrabYou;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CrabModel extends DefaultedEntityGeoModel<CrabCrabYou> {
    public CrabModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "crab"));
    }

    @Override
    public ResourceLocation getTextureResource(CrabCrabYou crab) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/crab/crab" + crab.getSkinID() + ".png");
    }
}
