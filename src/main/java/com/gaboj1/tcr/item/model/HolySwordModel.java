package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.boss_loot.FlySword;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class HolySwordModel extends DefaultedItemGeoModel<FlySword> {
    public HolySwordModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "holy_sword"));
    }

    @Override
    public ResourceLocation getTextureResource(FlySword animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/item/tree_spirit_wand_old.png");
    }
}
