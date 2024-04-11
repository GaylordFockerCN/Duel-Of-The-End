package com.gaboj1.tcr.entity.client.dreamspirit;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.dreamspirit.Squirrel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SquirrelModel extends DefaultedEntityGeoModel<Squirrel> {

    public SquirrelModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "squirrel"), true);
    }

}
