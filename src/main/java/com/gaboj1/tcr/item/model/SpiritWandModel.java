package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.weapon.SpiritWand;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class SpiritWandModel extends DefaultedItemGeoModel<SpiritWand> {

    public SpiritWandModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "spirit_wand"));
    }

    @Override
    public ResourceLocation getTextureResource(SpiritWand spiritWand) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/jingling.png");
    }
}
